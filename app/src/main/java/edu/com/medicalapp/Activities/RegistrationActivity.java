package edu.com.medicalapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.com.medicalapp.Adapters.CustomAdapter;
import edu.com.medicalapp.Models.registration.CommonResponse;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.utils.Constants;
import edu.com.medicalapp.utils.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


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
    @BindView(R.id.terms)
    TextView termsTV;
    @BindView(R.id.privacy)
    TextView privacy;

    @BindView(R.id.profileImage)
    CircleImageView profileImage;

    @BindView(R.id.edit_phone)
    EditText edit_phone;

    @BindView(R.id.selectState)
    Spinner selectState;

    String edit_name, edit_username, edit_email, edit_password;
    String[] countryNames = {"Andhra Pradesh", "Arunachal Pradesh", "Gujarat", "Karnataka", "Maharashtra", "Utter Pradesh", "Bihar"};
    private String imagePath;
    private String statetxt;
    private String edit_phonetxt;

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
                startActivity(new Intent(RegistrationActivity.this, FirstloginActivity.class));
                finish();
            }
        });

        SpannableString spannableString = new SpannableString(getString(R.string.terms));
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termsTV.setText(spannableString);
        termsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, WebViewActivity.class);
                intent.putExtra("title", "Terms & Conditions");
                startActivity(intent);

            }
        });



        SpannableString spannableString1 = new SpannableString(getString(R.string.already_member));
        spannableString1.setSpan(new UnderlineSpan(), 16, spannableString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textLogin.setText(spannableString1);


        SpannableString privacytxt = new SpannableString(getString(R.string.privacy));
        privacytxt.setSpan(new UnderlineSpan(), 4, privacytxt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        privacy.setText(privacytxt);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, WebViewActivity.class);
                intent.putExtra("title", "Privacy Policy");
                startActivity(intent);
            }
        });


        //state spinner
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.selectState);
        spin.setOnItemSelectedListener(this);

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), countryNames);
        spin.setAdapter(customAdapter);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Constants.CAPTURE_IMAGE);
            }
        }
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_signUp:
                validation();
                break;

            case R.id.text_login:
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
                break;
        }

    }


    private void validation() {

        edit_name = editName.getText().toString();
        edit_username = editUsername.getText().toString();
        edit_email = editEmailId.getText().toString();
        edit_phonetxt = edit_phone.getText().toString();
        edit_password = editPassword.getText().toString();

        if (TextUtils.isEmpty(edit_name.trim()) || edit_name.length() == 0) {
            editName.setError(getString(R.string.invalid_name));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_name));
            return;
        }
        if (TextUtils.isEmpty(edit_username.trim()) || edit_username.length() == 0) {
            editUsername.setError(getString(R.string.invalid_username));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_username));
            return;
        }
        if (TextUtils.isEmpty(edit_email.trim()) || edit_email.length() == 0) {
            editEmailId.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }
        if (TextUtils.isEmpty(edit_password.trim()) || edit_password.length() == 0) {
            editPassword.setError(getString(R.string.invalid_password));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_password));
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(edit_email).matches()) {
            editEmailId.setError(getString(R.string.invalid_email));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_email));
            return;
        }

        if (TextUtils.isEmpty(edit_phonetxt)) {

            edit_phone.setError(getString(R.string.invalid_email));

            return;
        }else{
            if (edit_phonetxt.length()<10){
                edit_phone.setError(getString(R.string.valid_phone));
                return;
            }
        }
        if (edit_password.length() < 6) {
            editPassword.setError(getString(R.string.invalid_too_short));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_too_short));
            return;

        }

        if (TextUtils.isEmpty(statetxt)) {
            Utils.displayToast(getApplicationContext(), "Please select state");
            return;

        }


        Uri uri = Uri.parse("android.resource://edu.com.medicalapp/drawable/dna_log_new");
        File videoFile = new File(getRealPath(uri));
        RequestBody videoBody = RequestBody.create(
                okhttp3.MultipartBody.FORM, "file");


        MultipartBody.Part vFile = MultipartBody.Part.createFormData("file", videoFile.getName(), videoBody);


        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), edit_name);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edit_email);
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), edit_phonetxt);
        RequestBody states = RequestBody.create(MediaType.parse("text/plain"), statetxt);

        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), edit_password);
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), edit_username);
        Utils.showProgressDialog(this);
        //showProgressDialog(this);
        Utils.showProgressDialog(this);
        RestClient.registerUser(name, username, email,phone,states, password,vFile, new Callback<CommonResponse>() {
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
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Utils.dismissProgressDialog();
                Utils.displayToast(getApplicationContext(), "Unable to register, please try again later");

            }
        });


    }

    private String getRealPath(Uri uri){
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor == null) {
                return uri.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                return cursor.getString(idx);
            }
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        statetxt = countryNames[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
