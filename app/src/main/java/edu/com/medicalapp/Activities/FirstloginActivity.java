package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.R;

public class FirstloginActivity extends AppCompatActivity  {


    @BindView(R.id.login_button)
    LoginButton loginButton;

    @BindView(R.id.btn_email)
    Button btnEmail;

    @BindView(R.id.FirstLoginText)
    TextView loginText;

    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_firstlogin);
        ButterKnife.bind(this);

       callbackManager=CallbackManager.Factory.create();



        loginwithFb();

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstloginActivity.this,RegistrationActivity.class));

            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstloginActivity.this,PhoneloginActivity.class));
            }
        });


    }

    private void loginwithFb() {

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                Toast.makeText(FirstloginActivity.this, "login reesult " + loginResult.getAccessToken(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(FirstloginActivity.this, MainActivity.class));


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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
