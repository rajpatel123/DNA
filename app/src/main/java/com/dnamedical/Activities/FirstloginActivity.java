package com.dnamedical.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.io.IOException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

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

public class FirstloginActivity extends AppCompatActivity {


    @BindView(R.id.login_button)
    LoginButton loginButton;

    @BindView(R.id.btn_email)
    Button btnEmail;

    @BindView(R.id.FirstLoginText)
    TextView loginText;
    @BindView(R.id.privacy)
    TextView privacytxt;

    @BindView(R.id.terms)
    TextView termsTV;

    CallbackManager callbackManager;

    private Button customFacebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_firstlogin);
        customFacebook = findViewById(R.id.custom_login);
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();
        loginwithFb();

        if (ContextCompat.checkSelfPermission(FirstloginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

        }

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstloginActivity.this, RegistrationActivity.class));
                finish();

            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstloginActivity.this, LoginActivity.class));
                finish();
            }
        });


        SpannableString spannableString = new SpannableString(getString(R.string.terms));
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termsTV.setText(spannableString);
        termsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstloginActivity.this, WebViewActivity.class);
                intent.putExtra("title", "Terms & Conditions");
                startActivity(intent);

            }
        });

        SpannableString spannableString1 = new SpannableString(getString(R.string.already_member));
        spannableString1.setSpan(new UnderlineSpan(), 16, spannableString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginText.setText(spannableString1);


        SpannableString privacytxtstr = new SpannableString(getString(R.string.privacy));
        privacytxtstr.setSpan(new UnderlineSpan(), 4, privacytxtstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        privacytxt.setText(privacytxtstr);
        privacytxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstloginActivity.this, WebViewActivity.class);
                intent.putExtra("title", "Privacy Policy");
                startActivity(intent);
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                finish();
            }
        }
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
                            String name = data.optString("name");
                            String email = data.optString("email");
                            String facebook_id = data.optString("id");
                            String pictureurl = data.getJSONObject("picture").getJSONObject("data").getString("url");

                            RequestBody facebookRequestBody = RequestBody.create(MediaType.parse("text/plain"), facebook_id);


                            RestClient.loginWithFacebook(facebookRequestBody, new Callback<FacebookLoginResponse>() {
                                @Override
                                public void onResponse(Call<FacebookLoginResponse> call, Response<FacebookLoginResponse> response) {

                                    FacebookLoginResponse facebookLoginResponse = response.body();
                                    if (facebookLoginResponse != null && facebookLoginResponse.getLoginDetails() != null) {
                                        if (TextUtils.isEmpty(facebookLoginResponse.getLoginDetails().get(0).getState()) || TextUtils.isEmpty(facebookLoginResponse.getLoginDetails().get(0).getEmailId()) || TextUtils.isEmpty(facebookLoginResponse.getLoginDetails().get(0).getMobileNo())) {
                                            Intent intent = new Intent(FirstloginActivity.this, RegistrationActivity.class);

                                            intent.putExtra(Constants.LOGIN_ID, facebookLoginResponse.getLoginDetails().get(0).getId());
                                            intent.putExtra(Constants.MOBILE, facebookLoginResponse.getLoginDetails().get(0).getMobileNo());
                                            intent.putExtra(Constants.NAME, facebookLoginResponse.getLoginDetails().get(0).getName());
                                            intent.putExtra(Constants.EMAILID, facebookLoginResponse.getLoginDetails().get(0).getEmailId());
                                            intent.putExtra(Constants.FB_ID, facebook_id);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(FirstloginActivity.this, MainActivity.class);
                                            DnaPrefs.putBoolean(FirstloginActivity.this, Constants.LoginCheck, true);
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
                                        Intent intent = new Intent(FirstloginActivity.this, RegistrationActivity.class);

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
                Toast.makeText(FirstloginActivity.this, "Login Cancel: " + getString(R.string.login_cancel), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(FirstloginActivity.this, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        customFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(FirstloginActivity.this, Arrays.asList("public_profile", "email"));
            }
        });
    }

//    private void gotoLoginWithFacebook(String name, String email, String facebook_id, String pictureurl) {
//        RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), email);
//        RequestBody Name = RequestBody.create(MediaType.parse("text/plain"), name);
//        RequestBody Facebook_id = RequestBody.create(MediaType.parse("text/plain"), facebook_id);
//        Utils.showProgressDialog(FirstloginActivity.this);
//        RestClient.facebookRegister(Name, Email, Facebook_id, new Callback<FacebookResponse>() {
//            @Override
//            public void onResponse(Call<FacebookResponse> call, Response<FacebookResponse> response) {
//                Utils.dismissProgressDialog();
//                FacebookResponse facebookResponse = response.body();
//                if (facebookResponse.getFacebookDetails() != null && facebookResponse.getFacebookDetails().size() > 0) {
//                    int ids = facebookResponse.getFacebookDetails().get(0).getId();
//                    DnaPrefs.putString(getApplicationContext(), "Login_Id", "" + ids);
//                    if (response != null && response.body() != null) {
//
//
//                        if (Integer.parseInt(facebookResponse.getStatus()) == 1) {
//                            Utils.displayToast(FirstloginActivity.this, facebookResponse.getMessage());
//
//                            RestClient.getMobile(Email, new Callback<MobileResponse>() {
//                                @Override
//                                public void onResponse(Call<MobileResponse> call, Response<MobileResponse> response) {
//                                    Utils.dismissProgressDialog();
//                                    if (response != null && response.body() != null) {
//                                        if (!TextUtils.isEmpty(response.body().getMobile())) {
//                                            Intent intent = new Intent(FirstloginActivity.this, MainActivity.class);
//                                            int fb_id = facebookResponse.getFacebookDetails().get(0).getId();
//                                            DnaPrefs.putBoolean(FirstloginActivity.this, Constants.LoginCheck, true);
//                                            DnaPrefs.putInt(getApplicationContext(), "fB_ID", fb_id);
//                                            DnaPrefs.putString(getApplicationContext(), Constants.MOBILE, response.body().getMobile());
//                                            DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", true);
//                                            DnaPrefs.putString(getApplicationContext(), "NAME", name);
//                                            DnaPrefs.putString(getApplicationContext(), "URL", pictureurl);
//                                            DnaPrefs.putString(getApplicationContext(), "EMAIL", email);
//                                            DnaPrefs.putString(getApplicationContext(), "FBID", facebook_id);
//
//                                            startActivity(intent);
//                                            finish();
//                                        } else {
//                                            Intent intent = new Intent(FirstloginActivity.this, Mobilenumber.class);
//                                            int fb_id = facebookResponse.getFacebookDetails().get(0).getId();
//                                            DnaPrefs.putInt(getApplicationContext(), "fB_ID", fb_id);
//                                            DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", true);
//                                            DnaPrefs.putString(getApplicationContext(), "NAME", name);
//                                            DnaPrefs.putString(getApplicationContext(), "URL", pictureurl);
//                                            DnaPrefs.putString(getApplicationContext(), "EMAIL", email);
//                                            DnaPrefs.putString(getApplicationContext(), "FBID", facebook_id);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//
//                                    } else {
//                                        Toast.makeText(FirstloginActivity.this, "Some Thing Went Wrong", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//
//
//                                @Override
//                                public void onFailure(Call<MobileResponse> call, Throwable t) {
//                                    Toast.makeText(FirstloginActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//
//                        } else {
//                            Utils.displayToast(FirstloginActivity.this, "Invalid login detail");
//
//                        }
//                    }
//
//
//                } else {
//                    Toast.makeText(FirstloginActivity.this, "", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<FacebookResponse> call, Throwable t) {
//                Utils.dismissProgressDialog();
//                Utils.displayToast(FirstloginActivity.this, "Invalid login detail");
//
//            }
//        });
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}