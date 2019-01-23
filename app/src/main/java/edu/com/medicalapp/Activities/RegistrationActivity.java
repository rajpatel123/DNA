package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.Models.registration.CommonResponse;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.utils.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.edit_name)
    EditText editName;

    @BindView(R.id.edit_username)
    EditText editUsername;

    @BindView(R.id.edit_emailId)
    EditText editEmailId;

    @BindView(R.id.edit_Passwword)
    EditText editPassword;

    @BindView(R.id.btn_signUp)
    Button btnSignUp;

    @BindView(R.id.text_login)
    TextView textLogin;

    String edit_name,edit_username,edit_email,edit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        btnSignUp.setOnClickListener(this);
        textLogin.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_signUp:
               validation();
               break;

            case R.id.text_login:
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                finish();
                break;
        }

    }

    private void validation() {

        edit_name = editName.getText().toString();
        edit_username = editUsername.getText().toString();
        edit_email=editEmailId.getText().toString();
        edit_password=editPassword.getText().toString();

        if (TextUtils.isEmpty(edit_name.trim()) || edit_name.length() ==0) {
            editName.setError(getString(R.string.invalid_name));
            Utils.displayToast(getApplicationContext(),getString(R.string.invalid_name));
            return;
        }
        if (TextUtils.isEmpty(edit_username.trim()) || edit_username.length() == 0) {
            editUsername.setError(getString(R.string.invalid_username));
            Utils.displayToast(getApplicationContext(),getString(R.string.invalid_username));
            return;
        }
        if (TextUtils.isEmpty(edit_email.trim()) || edit_email.length() == 0) {
            editEmailId.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(),getString(R.string.invalid_email));
            return;
        }
        if (TextUtils.isEmpty(edit_password.trim()) || edit_password.length() == 0) {
            editPassword.setError(getString(R.string.invalid_password));
            Utils.displayToast(getApplicationContext(),getString(R.string.invalid_password));
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(edit_email).matches())
        {
            editEmailId.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(),getString(R.string.invalid_email));
            return;
        }
        if(edit_password.length()<6)
        {
            editPassword.setError(getString(R.string.invalid_too_short));
            Utils.displayToast(getApplicationContext(),getString(R.string.invalid_too_short));
            return;

        }
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"),edit_name);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edit_email);
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"),edit_password);
        RequestBody username=RequestBody.create(MediaType.parse("text/plain"),edit_username);

        //showProgressDialog(this);

        RestClient.registerUser(name,username,email, password, new Callback<CommonResponse>() {
           /* private Call<CommonResponse> call;
            private Response<CommonResponse> response;
*/
            @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
               /* this.call = call;
                this.response = response;*/
                Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            Utils.displayToast(getApplicationContext(), "Successfuly registered");
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }
                    }
                }
                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t)
                {
                    Utils.dismissProgressDialog();
                    Utils.displayToast(getApplicationContext(), "Unable to register, please try again later");

              }
            });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.home)
        {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


}
