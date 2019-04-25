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
import android.widget.Toast;

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

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, FirstloginActivity.class));
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
                            String username = loginResponse.getLoginDetails().get(0).getUsername();

                            DnaPrefs.putString(getApplicationContext(), "Login_Id", id);
                            DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", false);
                            DnaPrefs.putString(getApplicationContext(), "STATE", state);
                            DnaPrefs.putString(getApplicationContext(), "COLLEGE", college);

                            DnaPrefs.putString(getApplicationContext(), "NAME", username);
                            DnaPrefs.putString(getApplicationContext(), "URL", "");
                            DnaPrefs.putString(getApplicationContext(), "EMAIL", email_str);


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

 /*       loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                *//*Profile profile = Profile.getCurrentProfile();
                String name = profile.getName();
                String link = profile.getLinkUri().toString();

                Intent intent = new Intent(FirstloginActivity.this, MainActivity.class);
                intent.putExtra("VideoModel", name);
                intent.putExtra("Link", link);
                startActivity(intent);


                Toast.makeText(FirstloginActivity.this, name + " " + link, Toast.LENGTH_SHORT).show();
*//*
                String Userid = loginResult.getAccessToken().getUserId();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {


                        if (object!=null) {
                            FacebookLoginData facebookLoginData = new Gson().fromJson(object.toString(), FacebookLoginData.class);
                            if (facebookLoginData!=null){
                                String name=facebookLoginData.getName();
                                String id=facebookLoginData.getId();



                                Intent intent = new Intent(FirstloginActivity.this,MainActivity.class);
                                    intent.putExtra("NAME",name);
                                    intent.putExtra("ID",id);

                                    startActivity(intent);


                            }
                        }


                    }

                });
                Bundle bundle = new Bundle();
                bundle.putString("fields", "id,name,email,picture,birthday,gender,age_range");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();
                // Toast.makeText(FirstloginActivity.this, name+" "+email+" "+gender, Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(FirstloginActivity.this, MainActivity.class));
            }
            @Override
            public void onCancel() {
                Toast.makeText(FirstloginActivity.this, "Login Cancel: " + getString(R.string.login_cancel), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(FirstloginActivity.this, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        JSONObject data = response.getJSONObject();
                        try {
                            String name = data.getString("name");
                            String email = data.getString("email");
                            String facebook_id = data.getString("id");
                            String pictureurl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                            DnaPrefs.putBoolean(LoginActivity.this, Constants.LoginCheck, true);

                            RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), email);
                            RequestBody Name = RequestBody.create(MediaType.parse("text/plain"), name);
                            RequestBody Facebook_id = RequestBody.create(MediaType.parse("text/plain"), facebook_id);
                            Utils.showProgressDialog(LoginActivity.this);
                            RestClient.facebookRegister(Name, Email, Facebook_id, new Callback<FacebookResponse>() {
                                @Override
                                public void onResponse(Call<FacebookResponse> call, Response<FacebookResponse> response) {
                                    Utils.dismissProgressDialog();
                                    if (response != null && response.body() != null) {
                                        FacebookResponse facebookResponse = response.body();
                                        if (Integer.parseInt(facebookResponse.getStatus()) == 1) {
                                            Utils.displayToast(LoginActivity.this, facebookResponse.getMessage());
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            int fb_id = facebookResponse.getFacebookDetails().get(0).getId();
                                            DnaPrefs.putInt(getApplicationContext(), "fB_ID", fb_id);
                                            DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", true);
                                            DnaPrefs.putString(getApplicationContext(), "NAME", name);
                                            DnaPrefs.putString(getApplicationContext(), "URL", pictureurl);
                                            DnaPrefs.putString(getApplicationContext(), "EMAIL", email);
                                            DnaPrefs.putString(getApplicationContext(), "FBID", facebook_id);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Utils.displayToast(LoginActivity.this, "Invalid login detail");
                                        }

                                    } else {
                                        Utils.displayToast(LoginActivity.this, "Invalid login detail");

                                    }
                                }

                                @Override
                                public void onFailure(Call<FacebookResponse> call, Throwable t) {
                                    Utils.dismissProgressDialog();
                                    Utils.displayToast(LoginActivity.this, "Invalid login detail");

                                }
                            });


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
