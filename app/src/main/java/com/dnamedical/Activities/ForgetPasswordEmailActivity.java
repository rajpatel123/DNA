package com.dnamedical.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dnamedical.Models.forgetpassword.ForgetPasswordResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordEmailActivity extends AppCompatActivity {

    @BindView(R.id.edit_token)
    EditText editTextToken;

    @BindView(R.id.edit_password)
    EditText editTextPassword;

    @BindView(R.id.edit_confirm_password)
    EditText editTextConfirmPassword;

    @BindView(R.id.edit_emailid)
    EditText editTextEmailId;

    @BindView(R.id.btnChangePassword)
    Button btnchangePassword;

    String tokens, emailId, password, confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_email);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        Intent in = getIntent();

        if(in.hasExtra("email")){
            editTextEmailId.setText(in.getStringExtra("email"));
            editTextEmailId.setEnabled(false);
        }

        btnchangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyNewpassword();
            }
        });
    }

    private void verifyNewpassword() {

        tokens = editTextToken.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        confirmPassword = editTextConfirmPassword.getText().toString().trim();
        emailId = editTextEmailId.getText().toString().trim();

        if (TextUtils.isEmpty(tokens.trim()) || tokens.length() == 0) {
            editTextToken.setError(getString(R.string.invalid_token));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_token));
            return;
        }
        if (TextUtils.isEmpty(password.trim()) || password.length() == 0) {
            editTextPassword.setError(getString(R.string.invalid_password));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_password));
            return;
        }
        if (TextUtils.isEmpty(confirmPassword.trim()) || confirmPassword.length() == 0) {
            editTextConfirmPassword.setError(getString(R.string.invalid_password));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_password));
            return;
        }
        if (TextUtils.isEmpty(emailId.trim()) || emailId.length() == 0) {
            editTextEmailId.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            editTextEmailId.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError(getString(R.string.invalid_too_short));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_too_short));
            return;

        }

        if (confirmPassword.length() < 6) {
            editTextConfirmPassword.setError(getString(R.string.invalid_too_short));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_too_short));
            return;

        }


        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), tokens);
        RequestBody new_password = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody retype_password = RequestBody.create(MediaType.parse("text/plain"), confirmPassword);
        RequestBody email_id = RequestBody.create(MediaType.parse("text/plain"), emailId);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.forgetPassword(email_id, token, new_password, retype_password, new Callback<ForgetPasswordResponse>() {
                @Override
                public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            Intent intent = new Intent(ForgetPasswordEmailActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(ForgetPasswordEmailActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (response.body().getStatus().equalsIgnoreCase("false")) {
                            Toast.makeText(ForgetPasswordEmailActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ForgetPasswordResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(ForgetPasswordEmailActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connections Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

