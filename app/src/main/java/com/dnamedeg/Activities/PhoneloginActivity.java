package com.dnamedeg.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.dnamedeg.Models.registration.CommonResponse;
import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneloginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText edit_phone;


    Button btnContinue;


    TextView tryOtherLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelogin);
         edit_phone=findViewById(R.id.edittext_phone);
         btnContinue=findViewById(R.id.btn_continue);
         tryOtherLogin=findViewById(R.id.try_login);

        edit_phone.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        tryOtherLogin.setOnClickListener(this);


        SpannableString spannableString = new SpannableString(getString(R.string.try_other_login));
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tryOtherLogin.setText(spannableString);


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PhoneloginActivity.this,FirstloginActivity.class));
                finish();
            }
        });



    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.btn_continue) {
//        {
//            case R.id.btn_continue:
            if (!TextUtils.isEmpty(edit_phone.getText().toString().trim())) {
                sentOTP(edit_phone.getText().toString().trim());
            } else {

            }
            return;
        } else if (view.getId()==R.id.try_login){
            startActivity(new Intent(PhoneloginActivity.this,LoginActivity.class));
            finish();
        }
    }

    private void sentOTP(String phone) {
        RequestBody phonebRequestBody = RequestBody.create(MediaType.parse("text/plain"), phone);
        Utils.showProgressDialog(this);
        //showProgressDialog(this);
        RestClient.sendOtp(phonebRequestBody, new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Utils.dismissProgressDialog();
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Utils.displayToast(getApplicationContext(), response.body().getMessage());
                    Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                    intent.putExtra("mobile", phone);
                    intent.putExtra("user_id", response.body().getUser_id());
                    startActivity(intent);
                    finish();
                } else {
                    Utils.displayToast(getApplicationContext(), response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Utils.dismissProgressDialog();

            }
        });
    }

    private void validation() {

          boolean check=true;
         String Phone=edit_phone.getText().toString().trim();

         if(Phone.isEmpty())
         {
             edit_phone.setError(getString(R.string.empty_field));
             check=false;
         }

         if(Phone.length()!=10)
         {

             edit_phone.setError(getString(R.string.full_number));
             check=false;
         }

         if(check==true)
         {
             Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
         }
         else
         {
             Toast.makeText(this, "Fill Right ReviewDetail", Toast.LENGTH_SHORT).show();
         }




    }
}
