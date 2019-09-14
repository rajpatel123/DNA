package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Models.Enter_Mobile.EmailByFBResponse;
import com.dnamedical.Models.facebookloginnew.FacebookLoginResponse;
import com.dnamedical.Models.get_Mobile_number.MobileResponse;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.dnamedical.Models.FacebookLoginData;
import com.dnamedical.Models.facebook.FacebookResponse;
import com.dnamedical.Models.login.loginResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_email)
    EditText editEmail;

    @BindView(R.id.edit_Passwword)
    EditText editPassword;

    @BindView(R.id.loginButton)
    Button btnLogin;

    @BindView(R.id.login_checkbox)
    CheckBox loginCheck;
    /*

        @BindView(R.id.login_button)
        LoginButton loginBtn;
    */
   @BindView(R.id.txt_forget)
    TextView textViewForget;


    private Button customFacebook;
    CallbackManager callbackManager;

    String email_str, pass_str;
    boolean check = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        customFacebook = findViewById(R.id.custom_login);
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();
        loginwithFb();

        textViewForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(LoginActivity.this,ForgetPasswordSendEmailActiivty.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                finish();
            }
        });
        // loginFb();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Validation();
            }
        });
    }


    //Login Validation
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private void Validation() {


        email_str = editEmail.getText().toString();
        pass_str = editPassword.getText().toString();
        if (loginCheck.isChecked()) {
            check = true;
            DnaPrefs.putBoolean(this, Constants.LoginCheck, check);
        } else {
            check = false;
            DnaPrefs.putBoolean(this, Constants.LoginCheck, check);
        }
        if (TextUtils.isEmpty(email_str.trim()) || email_str.length() == 0) {
            Utils.displayToast(getApplicationContext(), "Please enter valid email");
            return;
        }
        if (TextUtils.isEmpty(pass_str.trim()) || pass_str.length() == 0) {
            Utils.displayToast(getApplicationContext(), "Please enter valid password");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_str).matches()) {
            Utils.displayToast(getApplicationContext(), "Please enter valid email");
            return;
        }
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), email_str);
        RequestBody pwd = RequestBody.create(MediaType.parse("text/plain"), pass_str);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.loginUser(email, pwd, new Callback<loginResponse>() {
                @Override
                public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response != null && response.body() != null) {
                        loginResponse loginResponse = response.body();
                        if (Integer.parseInt(loginResponse.getStatus()) == 1) {
                            Utils.displayToast(LoginActivity.this, loginResponse.getMessage());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            String id = loginResponse.getLoginDetails().get(0).getId();
                            String state = loginResponse.getLoginDetails().get(0).getState();
                            String college = loginResponse.getLoginDetails().get(0).getCollege();
                            String username = loginResponse.getLoginDetails().get(0).getName();

                            DnaPrefs.putString(getApplicationContext(), "Login_Id", id);
                            DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", false);
                            DnaPrefs.putString(getApplicationContext(), "STATE", state);
                            DnaPrefs.putString(getApplicationContext(), "COLLEGE", college);
                            DnaPrefs.putString(getApplicationContext(), Constants.MOBILE, loginResponse.getLoginDetails().get(0).getMobileNo());

                            DnaPrefs.putString(getApplicationContext(), "NAME", username);
                            DnaPrefs.putString(getApplicationContext(), "URL", "");
                            DnaPrefs.putString(getApplicationContext(), "EMAIL", email_str);

                            DnaPrefs.putBoolean(LoginActivity.this, Constants.LoginCheck, true);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Utils.displayToast(LoginActivity.this, "Invalid login detail");
                        }

                    } else {
                        Utils.displayToast(LoginActivity.this, "Invalid login detail");

                    }
                }


                @Override
                public void onFailure(Call<loginResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Utils.displayToast(LoginActivity.this, "Invalid login detail");

                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connection Failed", Toast.LENGTH_SHORT).show();
        }


      /*  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);*/
/*
        boolean check = true;
        String Email = editEmail.getText().toString().trim();
        String Password = editPassword.getText().toString().trim();

        if (Email.isEmpty()) {
            editEmail.setError(getString(R.string.empty_field));
            check = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            editEmail.setError(getString(R.string.invalid_email));
            check = false;

        }

        if (Password.isEmpty()) {
            editPassword.setError(getString(R.string.empty_field));
            check = false;
        }

        if (check == false) {

            Toast.makeText(this, getString(R.string.invalid_data), Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }*/
        //Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//          intent.putExtra(Constants.NAME,loginResponse.getName()!=null?loginResponse.getName():"");
//          intent.putExtra(Constants.EMAILID,loginResponse.getEmail()!=null?loginResponse.getEmail():"");
        //startActivity(intent);
        //  finish();
          /*  final LoginRequest loginRequest=new LoginRequest();
            loginRequest.setUserName(Email);
            loginRequest.setPassword(Password);
            if(Utils.isInternetConnected(this))
            {
                  Utils.showProgressDialog(this);
                RestClient.loginUser(loginRequest, new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
                    {


                        Utils.dismissProgressDialog();
                        if(response.code()==200 && response.body()!=null)
                        {
                            LoginResponse loginResponse=response.body();
                            DnaPrefs.putString(LoginActivity.this, Constants.ACCESS_TOKEN_EMAIL,loginResponse.getToken());
                           // DnaPrefs.putString(LoginActivity.this,Constants.NAME,loginResponse.getName());
                          //  DnaPrefs.putString(LoginActivity.this,Constants.EMAILID,loginResponse.getEmail());
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra(Constants.NAME,loginResponse.getName()!=null?loginResponse.getName():"");
                            intent.putExtra(Constants.EMAILID,loginResponse.getEmail()!=null?loginResponse.getEmail():"");
                            startActivity(intent);
                            finish();

                            }
                            else
                            {
                            Toast.makeText(LoginActivity.this, "Invalid Credential", Toast.LENGTH_SHORT).show();

                            }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {


                    }
                });
            }
            else
            {
                Toast.makeText(this,getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        }
    }*/


    }

    private void loginwithFb() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        JSONObject data = response.getJSONObject();
                        try {
                            String name = data.getString("name");
                            String email = data.optString("email");
                            String facebook_id = data.getString("id");
                            String pictureurl = data.getJSONObject("picture").getJSONObject("data").getString("url");

                            RequestBody facebookRequestBody = RequestBody.create(MediaType.parse("text/plain"), facebook_id);


                            RestClient.loginWithFacebook(facebookRequestBody, new Callback<FacebookLoginResponse>() {
                                @Override
                                public void onResponse(Call<FacebookLoginResponse> call, Response<FacebookLoginResponse> response) {

                                    FacebookLoginResponse facebookLoginResponse = response.body();
                                    if (facebookLoginResponse != null && facebookLoginResponse.getLoginDetails() != null) {
                                        if (TextUtils.isEmpty(facebookLoginResponse.getLoginDetails().get(0).getState()) || TextUtils.isEmpty(facebookLoginResponse.getLoginDetails().get(0).getEmailId()) || TextUtils.isEmpty(facebookLoginResponse.getLoginDetails().get(0).getMobileNo())) {
                                            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);

                                            intent.putExtra(Constants.LOGIN_ID, facebookLoginResponse.getLoginDetails().get(0).getId());
                                            intent.putExtra(Constants.MOBILE, facebookLoginResponse.getLoginDetails().get(0).getMobileNo());
                                            intent.putExtra(Constants.NAME, facebookLoginResponse.getLoginDetails().get(0).getName());
                                            intent.putExtra(Constants.EMAILID, facebookLoginResponse.getLoginDetails().get(0).getEmailId());
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            DnaPrefs.putBoolean(LoginActivity.this, Constants.LoginCheck, true);
                                            DnaPrefs.putString(getApplicationContext(), "Login_Id", facebookLoginResponse.getLoginDetails().get(0).getId());
                                            DnaPrefs.putString(getApplicationContext(), Constants.MOBILE, facebookLoginResponse.getLoginDetails().get(0).getMobileNo());
                                            DnaPrefs.putString(getApplicationContext(), "NAME", name);
                                            DnaPrefs.putString(getApplicationContext(), "URL", pictureurl);
                                            DnaPrefs.putString(getApplicationContext(), "EMAIL", facebookLoginResponse.getLoginDetails().get(0).getEmailId());
                                            DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", false);
                                            DnaPrefs.putString(getApplicationContext(), "STATE", facebookLoginResponse.getLoginDetails().get(0).getState());
                                            DnaPrefs.putString(getApplicationContext(), "COLLEGE", facebookLoginResponse.getLoginDetails().get(0).getCollege());
//                                            DnaPrefs.putString(getApplicationContext(), "FBID", facebook_id);

                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);

                                        intent.putExtra(Constants.LOGIN_ID, "");
                                        intent.putExtra(Constants.MOBILE, "");

                                        intent.putExtra(Constants.FB_ID, facebook_id);
                                        intent.putExtra(Constants.NAME, name);
                                        intent.putExtra(Constants.EMAILID, email);
                                        startActivity(intent);
                                    }

                                }

                                @Override
                                public void onFailure(Call<FacebookLoginResponse> call, Throwable t) {
                                    Log.d("Data", "Error in login");

                                }
                            });


//                            if (TextUtils.isEmpty(email)) {
//                                RequestBody Facebook_id = RequestBody.create(MediaType.parse("text/plain"), facebook_id);
//                                RestClient.getEmail(Facebook_id, new Callback<EmailByFBResponse>() {
//                                    @Override
//                                    public void onResponse(Call<EmailByFBResponse> call, Response<EmailByFBResponse> response) {
//                                        if (response != null && response.body() != null) {
//                                            if (TextUtils.isEmpty(response.body().getEmail())){
//                                                Intent intent = new Intent(FirstloginActivity.this, EnterMobileAndEmailActivity.class);
//                                                intent.putExtra("name", name);
//                                                intent.putExtra("fb_id", facebook_id);
//                                                intent.putExtra("pictureurl", pictureurl);
//                                                startActivity(intent);
//                                            }else{
//                                                gotoLoginWithFacebook(name,response.body().getEmail(),facebook_id,pictureurl);
//                                            }
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<EmailByFBResponse> call, Throwable t) {
//                                        Log.d(FirstloginActivity.class.getSimpleName(),"Unable to get Email form server");
//                                    }
//                                });
//
//
//
//
//                            }else{
//                              gotoLoginWithFacebook(name,email,facebook_id,pictureurl);
//                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                });
                Bundle bundle = new Bundle();
                bundle.putString("fields", "id,name,email,picture,birthday,gender,age_range");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();


            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login Cancel: " + getString(R.string.login_cancel), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        customFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
            }
        });
    }


    private void gotoLoginWithFacebook(String name, String email, String facebook_id, String pictureurl) {
        RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody Name = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody Facebook_id = RequestBody.create(MediaType.parse("text/plain"), facebook_id);
        Utils.showProgressDialog(LoginActivity.this);
        RestClient.facebookRegister(Name, Email, Facebook_id, new Callback<FacebookResponse>() {
            @Override
            public void onResponse(Call<FacebookResponse> call, Response<FacebookResponse> response) {
                Utils.dismissProgressDialog();
                FacebookResponse facebookResponse = response.body();
                if (facebookResponse.getFacebookDetails() != null && facebookResponse.getFacebookDetails().size() > 0) {
                    int ids = facebookResponse.getFacebookDetails().get(0).getId();
                    DnaPrefs.putString(getApplicationContext(), "Login_Id", ""+ids);
                    if (response != null && response.body() != null) {


                        if (Integer.parseInt(facebookResponse.getStatus()) == 1) {
                            Utils.displayToast(LoginActivity.this, facebookResponse.getMessage());

                            RestClient.getMobile(Email, new Callback<MobileResponse>() {
                                @Override
                                public void onResponse(Call<MobileResponse> call, Response<MobileResponse> response) {
                                    Utils.dismissProgressDialog();
                                    if (response != null && response.body() != null) {
                                        if (!TextUtils.isEmpty(response.body().getMobile())) {
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            int fb_id = facebookResponse.getFacebookDetails().get(0).getId();
                                            DnaPrefs.putBoolean(LoginActivity.this, Constants.LoginCheck, true);
                                            DnaPrefs.putInt(getApplicationContext(), "fB_ID", fb_id);
                                            DnaPrefs.putString(getApplicationContext(), Constants.MOBILE, response.body().getMobile());
                                            DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", true);
                                            DnaPrefs.putString(getApplicationContext(), "NAME", name);
                                            DnaPrefs.putString(getApplicationContext(), "URL", pictureurl);
                                            DnaPrefs.putString(getApplicationContext(), "EMAIL", email);
                                            DnaPrefs.putString(getApplicationContext(), "FBID", facebook_id);

                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(LoginActivity.this, Mobilenumber.class);
                                            int fb_id = facebookResponse.getFacebookDetails().get(0).getId();
                                            DnaPrefs.putInt(getApplicationContext(), "fB_ID", fb_id);
                                            DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", true);
                                            DnaPrefs.putString(getApplicationContext(), "NAME", name);
                                            DnaPrefs.putString(getApplicationContext(), "URL", pictureurl);
                                            DnaPrefs.putString(getApplicationContext(), "EMAIL", email);
                                            DnaPrefs.putString(getApplicationContext(), "FBID", facebook_id);
                                            startActivity(intent);
                                            finish();
                                        }

                                    } else {
                                        Toast.makeText(LoginActivity.this, "Some Thing Went Wrong", Toast.LENGTH_SHORT).show();
                                    }

                                }


                                @Override
                                public void onFailure(Call<MobileResponse> call, Throwable t) {
                                    Toast.makeText(LoginActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();

                                }
                            });

                        } else {
                            Utils.displayToast(LoginActivity.this, "Invalid login detail");

                        }
                    }


                } else {
                    Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<FacebookResponse> call, Throwable t) {
                Utils.dismissProgressDialog();
                Utils.displayToast(LoginActivity.this, "Invalid login detail");

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
