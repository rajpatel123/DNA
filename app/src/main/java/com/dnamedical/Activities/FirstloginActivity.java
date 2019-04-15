package com.dnamedical.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
                            String email = data.optString("email");
                            String facebook_id = data.getString("id");
                            String pictureurl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                            DnaPrefs.putBoolean(FirstloginActivity.this, Constants.LoginCheck, true);

                            RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), email);
                            RequestBody Name = RequestBody.create(MediaType.parse("text/plain"), name);
                            RequestBody Facebook_id = RequestBody.create(MediaType.parse("text/plain"), facebook_id);
                            Utils.showProgressDialog(FirstloginActivity.this);
                            RestClient.facebookRegister(Name, Email, Facebook_id, new Callback<FacebookResponse>() {
                                @Override
                                public void onResponse(Call<FacebookResponse> call, Response<FacebookResponse> response) {
                                    Utils.dismissProgressDialog();
                                    if (response != null && response.body() != null) {
                                        FacebookResponse facebookResponse = response.body();
                                        if (Integer.parseInt(facebookResponse.getStatus()) == 1) {
                                            Utils.displayToast(FirstloginActivity.this, facebookResponse.getMessage());
                                            Intent intent = new Intent(FirstloginActivity.this, MainActivity.class);
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
                                            Utils.displayToast(FirstloginActivity.this, "Invalid login detail");
                                        }

                                    } else {
                                        Utils.displayToast(FirstloginActivity.this, "Invalid login detail");

                                    }
                                }

                                @Override
                                public void onFailure(Call<FacebookResponse> call, Throwable t) {
                                    Utils.dismissProgressDialog();
                                    Utils.displayToast(FirstloginActivity.this, "Invalid login detail");

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}