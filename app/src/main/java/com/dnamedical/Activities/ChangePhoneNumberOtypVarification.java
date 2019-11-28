package com.dnamedical.Activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.dnamedical.Models.changePhoneNumber.ChangePhoneNumberOtpResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePhoneNumberOtypVarification extends AppCompatActivity {

  /*  @BindView(R.id.resendOtp)
    TextView resendOtp;
    @BindView(R.id.nextBtn)
    Button btnVerify;*/

    public PinEntryEditText printVerifyPin;
    public TextView timerTV;
    public Button btnOtpVerify;
    String otpNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number_otyp_varification);
        ButterKnife.bind(this);

        printVerifyPin = findViewById(R.id.prntEdtChangePhoneOtp);
        btnOtpVerify = findViewById(R.id.btnVerify);
        timerTV = findViewById(R.id.otpTxtView);

//        SpannableString spannableString = new SpannableString(resendOtp.getText().toString());
//        spannableString.setSpan(new UnderlineSpan(), 0, resendOtp.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        resendOtp.setText(spannableString);

        timerInOtp();
        openKeyboard();

        btnOtpVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPinOperation();
            }
        });

    }

    public void timerInOtp() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTV.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerTV.setText("Resend otp");
            }
        }.start();
    }

    public boolean inputPinOperation() {

        boolean check = true;
        otpNumber = printVerifyPin.getText().toString().trim();

        if (otpNumber.isEmpty() && printVerifyPin.length()==5) {
            printVerifyPin.setError("enter a valid otp");
            check = false;
        } else {
            printVerifyPin.setError(null);
        }
        return check;
    }

    public void verifyPinOperation(){
        if (inputPinOperation()){

            String userId = DnaPrefs.getString(this,Constants.LOGIN_ID);
            String phoneNumber =  DnaPrefs.getString(this,Constants.USERPHNUMBER);


            RequestBody UserId = RequestBody.create(MediaType.parse("text/plane"), userId);
            RequestBody PhoneNo = RequestBody.create(MediaType.parse("text/plane"), otpNumber);
            RequestBody Otp = RequestBody.create(MediaType.parse("text/plane"),phoneNumber);

            RestClient.changePhoneNoOTPVerification(UserId, PhoneNo, Otp, new Callback<ChangePhoneNumberOtpResponse>() {
                @Override
                public void onResponse(Call<ChangePhoneNumberOtpResponse> call, Response<ChangePhoneNumberOtpResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body()!=null){
                        if (response.body().getStatus()=="1"){
                            finish();
                            Toast.makeText(getApplicationContext(), "Phone number updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ChangePhoneNumberOtpResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Phone number not updated", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void openKeyboard() {
        new Handler().postDelayed(() -> {
            printVerifyPin.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
            printVerifyPin.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
        }, 100);

    }

}


