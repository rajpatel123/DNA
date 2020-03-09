package com.dnamedical.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dnamedical.Models.mailsent.ForgetMailSentResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordSendEmailActiivty extends AppCompatActivity {

    @BindView(R.id.edit_send_email)
    EditText editTextEmail;

    @BindView(R.id.btnSendEmail)
    Button btnsendEmail;

    String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_send_email_actiivty);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        btnsendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();


            }
        });
    }

    private void sendEmail() {

        emailId = editTextEmail.getText().toString().trim();
        if (TextUtils.isEmpty(emailId.trim()) || emailId.length() == 0) {
            editTextEmail.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            editTextEmail.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }

        RequestBody email_id = RequestBody.create(MediaType.parse("text/plain"), emailId);
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.sentMail(email_id, new Callback<ForgetMailSentResponse>() {
                @Override
                public void onResponse(Call<ForgetMailSentResponse> call, Response<ForgetMailSentResponse> response) {

                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            Intent intent = new Intent(ForgetPasswordSendEmailActiivty.this, ForgetPasswordEmailActivity.class);
                             intent.putExtra("email",emailId);
                            startActivity(intent);
                            finish();
                        } else {
                            if (response.body().getStatus().equalsIgnoreCase("false")) {
                                Utils.dismissProgressDialog();
                                Toast.makeText(ForgetPasswordSendEmailActiivty.this, "Email Doesn't Exist!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ForgetMailSentResponse> call, Throwable t) {
                    Toast.makeText(ForgetPasswordSendEmailActiivty.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                    Utils.dismissProgressDialog();
                }
            });
        } else {
            Toast.makeText(this, "Internet Connections Failed", Toast.LENGTH_SHORT).show();
            Utils.dismissProgressDialog();
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

