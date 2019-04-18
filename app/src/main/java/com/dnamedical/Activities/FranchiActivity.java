package com.dnamedical.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dnamedical.Models.franchies.FranchiesResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FranchiActivity extends AppCompatActivity {

    private EditText editUsername, edituserEmail, edituserMobile, edituserComment;
    String username1, email, mobile, comment1;

    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchi);
        editUsername = findViewById(R.id.edit_name_frenchi);
        edituserEmail = findViewById(R.id.edit_emailId_frenchi);
        edituserMobile = findViewById(R.id.edit_phone_frenchi);
        edituserComment = findViewById(R.id.edit_comment_frenchi);
        btnSubmit = findViewById(R.id.btn_submit);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFrenchies();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void submitFrenchies() {
        username1 = editUsername.getText().toString().trim();
        email = edituserEmail.getText().toString().trim();
        mobile = edituserMobile.getText().toString().trim();
        comment1 = edituserComment.getText().toString().trim();

        if (TextUtils.isEmpty(username1.trim()) || username1.length() == 0) {
            editUsername.setError(getString(R.string.invalid_name));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_name));
            return;
        }
        if (TextUtils.isEmpty(email.trim()) || email.length() == 0) {
            edituserEmail.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }
     /* if (TextUtils.isEmpty(edit_password.trim()) || edit_password.length() == 0) {
            editPassword.setError(getString(R.string.invalid_password));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_password));
            return;
        }*
*/
        if (TextUtils.isEmpty(comment1.trim()) || comment1.length() == 0) {
            editUsername.setError("Please Fill the data");
            Utils.displayToast(getApplicationContext(), "Please Fill the data");
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edituserEmail.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }

        if (TextUtils.isEmpty(mobile)) {

            edituserMobile.setError(getString(R.string.invalid_email));

            return;
        } else {
            if (mobile.length() < 10) {
                edituserMobile.setError(getString(R.string.valid_phone));
                return;
            }
        }


        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), username1);
        RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), mobile);
        RequestBody usermail = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody comment = RequestBody.create(MediaType.parse("text/plain"), comment1);


        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.franchiesRegister(username, phoneno, usermail, comment, new Callback<FranchiesResponse>() {
                @Override
                public void onResponse(Call<FranchiesResponse> call, Response<FranchiesResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {

                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            Toast.makeText(FranchiActivity.this, "Successfully Send", Toast.LENGTH_SHORT).show();
                        }

                    }

                }

                @Override
                public void onFailure(Call<FranchiesResponse> call, Throwable t) {

                    Utils.dismissProgressDialog();
                    Toast.makeText(FranchiActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, " Internet Connection Failed!!!", Toast.LENGTH_SHORT).show();
        }


    }
}
