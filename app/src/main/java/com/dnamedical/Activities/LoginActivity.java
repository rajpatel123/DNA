package com.dnamedical.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import com.dnamedical.Models.facebookloginnew.FacebookLoginResponse;
import com.dnamedical.Models.login.loginResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
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
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordSendEmailActiivty.class);
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
        RequestBody deviceId = RequestBody.create(MediaType.parse("text/plain"), Utils.getDviceID(LoginActivity.this));

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.loginUser(email, pwd, deviceId, new Callback<loginResponse>() {
                @Override
                public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response != null && response.body() != null) {
                        loginResponse loginResponse = response.body();
                        if (Integer.parseInt(loginResponse.getStatus()) == 1) {
                            //Utils.displayToast(LoginActivity.this, loginResponse.getMessage());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            String id = loginResponse.getLoginDetails().get(0).getId();
                            String state = loginResponse.getLoginDetails().get(0).getState();
                            String college = loginResponse.getLoginDetails().get(0).getCollege();
                            String username = loginResponse.getLoginDetails().get(0).getName();


                            DnaPrefs.putString(getApplicationContext(), Constants.LOGIN_ID, id);
                            DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", false);
                            DnaPrefs.putString(getApplicationContext(), "STATE", state);
                            DnaPrefs.putString(getApplicationContext(), "COLLEGE", college);
                            DnaPrefs.putString(getApplicationContext(), Constants.MOBILE, loginResponse.getLoginDetails().get(0).getMobileNo());


                            if (!TextUtils.isEmpty(loginResponse.getLoginDetails().get(0).getInstitute_id())) {
                                DnaPrefs.putString(getApplicationContext(), Constants.INST_ID, loginResponse.getLoginDetails().get(0).getInstitute_id());
                                DnaPrefs.putString(getApplicationContext(), Constants.INST_NAME, loginResponse.getLoginDetails().get(0).getInstitute_name());
                                DnaPrefs.putString(getApplicationContext(), Constants.INST_IMAGE, loginResponse.getLoginDetails().get(0).getInstitute_logo());
                            } else {
                                DnaPrefs.putString(getApplicationContext(), Constants.INST_ID, "0");
                                DnaPrefs.putString(getApplicationContext(), Constants.INST_NAME, "");
                                DnaPrefs.putString(getApplicationContext(), Constants.INST_IMAGE, "");

                            }
                            DnaPrefs.putString(getApplicationContext(), "NAME", username);
                            DnaPrefs.putString(getApplicationContext(), "URL", "");
                            DnaPrefs.putString(getApplicationContext(), "EMAIL", email_str);

                            DnaPrefs.putBoolean(LoginActivity.this, Constants.LoginCheck, true);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else if (Integer.parseInt(loginResponse.getStatus()) == 2) {
                            showLoginfailedDialog(loginResponse.getMessage());
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

    }

    private void showLoginfailedDialog(String message) {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Multiple login detected")
                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
                            RequestBody deviceRequestBody = RequestBody.create(MediaType.parse("text/plain"), Utils.getDviceID(LoginActivity.this));


                            RestClient.loginWithFacebook(facebookRequestBody, deviceRequestBody, new Callback<FacebookLoginResponse>() {
                                @Override
                                public void onResponse(Call<FacebookLoginResponse> call, Response<FacebookLoginResponse> response) {

                                    FacebookLoginResponse facebookLoginResponse = response.body();
                                    if (facebookLoginResponse != null && Integer.parseInt(facebookLoginResponse.getStatus()) == 1) {
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
                                                DnaPrefs.putString(getApplicationContext(), Constants.LOGIN_ID, facebookLoginResponse.getLoginDetails().get(0).getId());
                                                DnaPrefs.putString(getApplicationContext(), Constants.MOBILE, facebookLoginResponse.getLoginDetails().get(0).getMobileNo());
                                                DnaPrefs.putString(getApplicationContext(), "NAME", name);
                                                DnaPrefs.putString(getApplicationContext(), "URL", pictureurl);

                                                if (!TextUtils.isEmpty(facebookLoginResponse.getLoginDetails().get(0).getInstitute_id())) {
                                                    DnaPrefs.putString(getApplicationContext(), Constants.INST_ID, facebookLoginResponse.getLoginDetails().get(0).getInstitute_id());
                                                    DnaPrefs.putString(getApplicationContext(), Constants.INST_NAME, facebookLoginResponse.getLoginDetails().get(0).getInstitute_name());
                                                    DnaPrefs.putString(getApplicationContext(), Constants.INST_IMAGE, facebookLoginResponse.getLoginDetails().get(0).getInstitute_logo());

                                                } else {
                                                    DnaPrefs.putString(getApplicationContext(), Constants.INST_ID, "0");
                                                    DnaPrefs.putString(getApplicationContext(), Constants.INST_NAME, "");
                                                    DnaPrefs.putString(getApplicationContext(), Constants.INST_IMAGE, "");


                                                }

                                                DnaPrefs.putString(getApplicationContext(), "EMAIL", facebookLoginResponse.getLoginDetails().get(0).getEmailId());
                                                DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", false);
                                                DnaPrefs.putString(getApplicationContext(), "STATE", facebookLoginResponse.getLoginDetails().get(0).getState());
                                                DnaPrefs.putString(getApplicationContext(), "COLLEGE", facebookLoginResponse.getLoginDetails().get(0).getCollege());

                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    } else if (Integer.parseInt(facebookLoginResponse.getStatus()) == 2) {
                                        showLoginfailedDialog(facebookLoginResponse.getMessage());

                                    } else if (Integer.parseInt(facebookLoginResponse.getStatus()) == 3) {

                                        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);

                                        intent.putExtra(Constants.LOGIN_ID, "");
                                        intent.putExtra(Constants.MOBILE, "");

                                        intent.putExtra(Constants.FB_ID, facebook_id);
                                        intent.putExtra(Constants.NAME, name);
                                        intent.putExtra(Constants.EMAILID, email);
                                        startActivity(intent);
                                        finish();

                                    }


                                }

                                @Override
                                public void onFailure(Call<FacebookLoginResponse> call, Throwable t) {
                                    Log.d("Data", "Error in login");

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
