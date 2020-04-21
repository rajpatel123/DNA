package com.dnamedical.Activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.dnamedical.Models.changePhoneNumber.ChangePhoneNumberOtpResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.messageReceiver.SmsListener;
import com.dnamedical.messageReceiver.SmsReceiver;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePhoneNumberOtypVarification extends AppCompatActivity implements SmsListener {

    /*  @BindView(R.id.resendOtp)
      TextView resendOtp;
      @BindView(R.id.nextBtn)
      Button btnVerify;*/
    SmsReceiver smsReceiver = new SmsReceiver();
    public PinEntryEditText printVerifyPin;
    public TextView timerTV;
    public Button btnOtpVerify;
    String otpNumber;
    private TextView resendOtp;
    private String updatePhoneNumber;
    private TextView changeNumber;
    private TextView mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number_otyp_varification);
        ButterKnife.bind(this);

        printVerifyPin = findViewById(R.id.prntEdtChangePhoneOtp);
        btnOtpVerify = findViewById(R.id.btnVerify);
        resendOtp = findViewById(R.id.resend);
        changeNumber = findViewById(R.id.changeNumber);
        mobileNumber = findViewById(R.id.mobileNumber);

        mobileNumber.setText(DnaPrefs.getString(getApplicationContext(),Constants.MOBILE));

        //timerTV = findViewById(R.id.otpTxtView);

//        SpannableString spannableString = new SpannableString(resendOtp.getText().toString());
//        spannableString.setSpan(new UnderlineSpan(), 0, resendOtp.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        resendOtp.setText(spannableString);

        //timerInOtp();
        openKeyboard();

        smsReceiver.bindListener(this);

        btnOtpVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPinOperation();
            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resenOtpVerify();
            }
        });

        changeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChanePhoneNumberActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void resenOtpVerify() {
        Utils.showProgressDialog(this);
        String userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        updatePhoneNumber = DnaPrefs.getString(getApplicationContext(), Constants.MOBILE);

        RequestBody UserId = RequestBody.create(MediaType.parse("text/plane"), userId);
        RequestBody phoneNo = RequestBody.create(MediaType.parse("text/plane"), updatePhoneNumber);

        RestClient.changePhoneNumber(UserId, phoneNo, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utils.dismissProgressDialog();
                if (response != null && response.code() == 200 && response.body() != null) {
                    Toast.makeText(getApplicationContext(), "Otp resent your mobile no", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

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

        if (otpNumber.isEmpty() && printVerifyPin.length() == 5) {
            printVerifyPin.setError("enter a valid otp");
            check = false;
        } else {
            printVerifyPin.setError(null);
        }
        return check;
    }

    public void verifyPinOperation() {
        if (inputPinOperation()) {

            String userId = DnaPrefs.getString(this, Constants.LOGIN_ID);
            String phoneNumber = DnaPrefs.getString(this, Constants.USERPHNUMBER);


            RequestBody UserId = RequestBody.create(MediaType.parse("text/plane"), userId);
            RequestBody PhoneNo = RequestBody.create(MediaType.parse("text/plane"), phoneNumber);
            RequestBody Otp = RequestBody.create(MediaType.parse("text/plane"), otpNumber);

            Utils.showProgressDialog(ChangePhoneNumberOtypVarification.this);
            RestClient.changePhoneNoOTPVerification(UserId, PhoneNo, Otp, new Callback<ChangePhoneNumberOtpResponse>() {
                @Override
                public void onResponse(Call<ChangePhoneNumberOtpResponse> call, Response<ChangePhoneNumberOtpResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus() == "true") {
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


    @Override
    public void messageReceived(String messageText) {
        printVerifyPin.setText(messageText);
        verifyPinOperation();

    }
}


