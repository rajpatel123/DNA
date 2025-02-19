package com.dnamedeg.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

import com.dnamedeg.Models.facebookloginnew.FacebookLoginResponse;
import com.dnamedeg.Models.login.SendOtpResponse;
import com.dnamedeg.Models.login.loginResponse;
import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.DnaPrefs;
import com.dnamedeg.utils.Utils;
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

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;



    @BindView(R.id.nextBtn)
    Button nextBtn;

    @BindView(R.id.privacy)
    TextView privacy;

    @BindView(R.id.terms)
    TextView terms;


    /*

        @BindView(R.id.login_button)
        LoginButton loginBtn;
    */


    String phone, pass_str;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        Utils.getDviceID(LoginActivity.this);
        ButterKnife.bind(this);




        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                intent.putExtra("title", "Privacy Policy");
                startActivity(intent);

            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                intent.putExtra("title", "Terms & Conditions");
                startActivity(intent);

            }
        });
        // loginFb();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Validation();
            }
        });
    }


    //Login Validation
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private void Validation() {

        phone = phoneNumber.getText().toString();

        if (TextUtils.isEmpty(phone.trim()) || phone.length() < 10) {
            Utils.displayToast(getApplicationContext(), "Please enter valid phone");
            return;
        }



        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.sendOtp(phone, "NEET-UG", new Callback<SendOtpResponse>() {
                @Override
                public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response != null && response.body() != null) {
                        SendOtpResponse sendOtpResponse = response.body();
                        if (sendOtpResponse.getStatus().equalsIgnoreCase("1")){
                        Intent intent = new Intent(LoginActivity.this, VerifyNewOTPActivity.class);
                        intent.putExtra(Constants.USERPHNUMBER,phone);
                        startActivity(intent);
                        }
                        Toast.makeText(LoginActivity.this, sendOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Utils.displayToast(LoginActivity.this, "Invalid login detail");

                    }
                }


                @Override
                public void onFailure(Call<SendOtpResponse> call, Throwable t) {
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
        super.onActivityResult(requestCode, resultCode, data);
    }


}
