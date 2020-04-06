package com.dnamedical.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Models.changePhoneNumber.ChangePhoneNumberResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dnamedical.utils.Constants.USERID;

public class ChanePhoneNumberActivity extends AppCompatActivity {

    /* @BindView(R.id.change_phone)*/
    public EditText edtChangPhoneNo;
    public Button btnSendOtp;
    String updatePhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chane_phone_number);
        /*ButterKnife.bind(this);*/

        edtChangPhoneNo = findViewById(R.id.enter_phoneNo);
        btnSendOtp = findViewById(R.id.ContactNo_sendOtp_Btn);

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationChangePhoneNo();
            }
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Change Mobile No's");
        }

    }

    public boolean inputValidation() {
        boolean check = true;

        updatePhoneNumber = edtChangPhoneNo.getText().toString();

        if (updatePhoneNumber.isEmpty() && edtChangPhoneNo.length() == 10) {
            edtChangPhoneNo.setError(" enter a valid phone number ");
            check = false;
        } else {
            edtChangPhoneNo.setError(null);
            check = true;
        }
        return check;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here

        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish(); // close this activity and return to preview activity (if there is any)


        return super.onOptionsItemSelected(item);
    }

    public void validationChangePhoneNo() {

        if (inputValidation()) {

            Utils.showProgressDialog(this);

            String phoneNumber = getIntent().getStringExtra("phoneNo");
            String userId = DnaPrefs.getString(getApplicationContext(),Constants.LOGIN_ID);

            RequestBody UserId = RequestBody.create(MediaType.parse("text/plane"), userId);
            RequestBody phoneNo = RequestBody.create(MediaType.parse("text/plane"), updatePhoneNumber);


            RestClient.changePhoneNumber(UserId, phoneNo, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utils.dismissProgressDialog();
                    if (response!=null && response.code()==200 && response.body()!=null) {
                            Intent intent = new Intent(getApplicationContext(), ChangePhoneNumberOtypVarification.class);
                            DnaPrefs.putString(getApplicationContext(), Constants.USERPHNUMBER, updatePhoneNumber);
                            //DnaPrefs.putString(getApplicationContext(), Constants.USERID, userId);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Otp sent successfully", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        onBackPressed();
        return super.onContextItemSelected(item);
    }
}
