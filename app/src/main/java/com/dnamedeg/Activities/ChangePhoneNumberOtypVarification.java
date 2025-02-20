package com.dnamedeg.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.chaos.view.PinView;
import com.dnamedeg.Models.changePhoneNumber.ChangePhoneNumberOtpResponse;
import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.messageReceiver.SmsListener;
import com.dnamedeg.messageReceiver.SmsReceiver;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.DnaPrefs;
import com.dnamedeg.utils.Utils;

import java.util.concurrent.TimeUnit;


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
    public PinView printVerifyPin;
    public TextView timerTV;
    public Button btnOtpVerify;
    String otpNumber;
    private TextView resendOtp;
    private ImageView crossBtn;

    private String updatePhoneNumber;
    private TextView changeNumber;
    private TextView mobileNumber;
    private TextView resendTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number_otyp_varification);


        printVerifyPin = findViewById(R.id.prntEdtChangePhoneOtp);
        btnOtpVerify = findViewById(R.id.btnVerify);
        resendOtp = findViewById(R.id.resend);
        crossBtn = findViewById(R.id.crossBtn);
        changeNumber = findViewById(R.id.changeNumber);
        mobileNumber = findViewById(R.id.mobileNumber);
        resendTimer = findViewById(R.id.resendTimer);
        startTimer();
        mobileNumber.setText(DnaPrefs.getString(getApplicationContext(), Constants.USERPHNUMBER));

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


        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtpVerify();
            }
        });

        changeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChanePhoneNumberActivity.class);
                intent.putExtra("title", "Change Mobile Number");
                startActivity(intent);
                finish();
            }
        });

    }


    private void startTimer() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text =
                        "<font color=#565656>OTP Valid for:</font> <font color=#80272525>" + TimeUnit.MILLISECONDS.toSeconds(
                                millisUntilFinished
                        ) + "</font>";
                resendTimer.setText(Html.fromHtml(text));
                resendTimer.setVisibility(View.VISIBLE);


            }

            @Override
            public void onFinish() {
                resendOtp.setAlpha(1);
                resendOtp.setEnabled(true);
                resendTimer.setVisibility(View.GONE);

            }
        }.start();
    }

    private void resendOtpVerify() {
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
                    startTimer();
                    resendTimer.setEnabled(false);
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
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
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


