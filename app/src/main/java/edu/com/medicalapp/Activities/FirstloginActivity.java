package edu.com.medicalapp.Activities;

import android.content.Intent;
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
import edu.com.medicalapp.Models.FacebookLoginData;
import edu.com.medicalapp.R;
import edu.com.medicalapp.utils.Constants;
import edu.com.medicalapp.utils.DnaPrefs;

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
                startActivity(new Intent(FirstloginActivity.this, PhoneloginActivity.class));
                finish();
            }
        });


        SpannableString spannableString = new SpannableString(getString(R.string.terms));
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termsTV.setText(spannableString);

        SpannableString spannableString1= new SpannableString(getString(R.string.already_member));
        spannableString1.setSpan(new UnderlineSpan(), 16, spannableString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginText.setText(spannableString1);


        SpannableString privacytxtstr= new SpannableString(getString(R.string.privacy));
        privacytxtstr.setSpan(new UnderlineSpan(), 4, privacytxtstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        privacytxt.setText(privacytxtstr);

    }

    private void loginwithFb() {

 /*       loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                *//*Profile profile = Profile.getCurrentProfile();
                String name = profile.getName();
                String link = profile.getLinkUri().toString();

                Intent intent = new Intent(FirstloginActivity.this, MainActivity.class);
                intent.putExtra("Name", name);
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
                     JSONObject data=response.getJSONObject();
                        try {
                            String name=data.getString("name");
                            String email=data.getString("email");
                            String pictureurl=data.getJSONObject("picture").getJSONObject("data").getString("url");
                            DnaPrefs.putBoolean(FirstloginActivity.this, Constants.LoginCheck,true);

                            Intent intent = new Intent(FirstloginActivity.this,MainActivity.class);
                            DnaPrefs.putString(getApplicationContext(),"NAME",name);
                            DnaPrefs.putString(getApplicationContext(),"URL",pictureurl);
                            DnaPrefs.putString(getApplicationContext(),"EMAIL",email);


                            startActivity(intent);
                            finish();
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
                LoginManager.getInstance().logInWithReadPermissions(FirstloginActivity.this,Arrays.asList("public_profile","email"));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    }