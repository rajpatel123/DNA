package com.dnamedical.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.dnamedical.Models.VerifyOtpResponse;
import com.dnamedical.Models.login.loginResponse;
import com.dnamedical.Models.registration.CommonResponse;
import com.dnamedical.Models.verify.VerifyOtpModel;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerifyNewOTPActivity extends AppCompatActivity {

    @BindView(R.id.pin_layout)
    PinEntryEditText pinLayout;
    @BindView(R.id.resendOtp)
    TextView resendOTP;
    @BindView(R.id.nextBtn)
    Button nextBtn;
    @BindView(R.id.phone)
    TextView phoneTV;

    @BindView(R.id.editTV)
    TextView editTV;


    boolean isSocial;
    boolean isPhone;

    private final int COUNT_TIMER = 60 * 1000;
    private final int COUNT_INTERVAL = 1000;
    private String user_id;
    private String phone;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_otp_new);
        ButterKnife.bind(this);
        startTimer();


        editTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        phoneTV.setText(getIntent().getStringExtra(Constants.USERPHNUMBER));
        SpannableString spannableString = new SpannableString(resendOTP.getText().toString());
        spannableString.setSpan(new UnderlineSpan(), 0, resendOTP.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        resendOTP.setText(spannableString);

        Intent intent = getIntent();

        user_id = intent.getStringExtra("user_id");
        phone = intent.getStringExtra(Constants.USERPHNUMBER);

        openKeyboard();

        pinLayout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (pinLayout.getText().toString().trim().length() >= 6) {
                    verifyOtp(pinLayout.getText().toString().trim());
                }
            }
        });

    }

    private void verifyOtp(String code) {

        Utils.showProgressDialog(this);
        //showProgressDialog(this);
        RestClient.verifyOtp(phone, code,"NEET-UG", new Callback<VerifyOtpModel>() {
            @Override
            public void onResponse(Call<VerifyOtpModel> call, Response<VerifyOtpModel> response) {
                Utils.dismissProgressDialog();
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Utils.displayToast(getApplicationContext(), "Otp Verified Successfully");
                   if (!TextUtils.isEmpty(response.body().getUser().getName())){

                       if (Integer.parseInt(loginResponse.getStatus()) == 1) {
                           //Utils.displayToast(LoginActivity.this, loginResponse.getMessage());
                           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                           String id = loginResponse.getLoginDetails().get(0).getId();
                           String state = loginResponse.getLoginDetails().get(0).getState();
                           String college = loginResponse.getLoginDetails().get(0).getCollege();
                           String username = loginResponse.getLoginDetails().get(0).getName();
                           String login_token = loginResponse.getLoginDetails().get(0).getLogin_token();
                           DnaPrefs.putString(getApplicationContext(), Constants.LOGIN_TOKEN,login_token);

                           DnaPrefs.putString(getApplicationContext(), Constants.f_id, loginResponse.getLoginDetails().get(0).getF_id());
                           DnaPrefs.putString(getApplicationContext(), Constants.LOGIN_ID, id);
                           DnaPrefs.putBoolean(getApplicationContext(), "isFacebook", false);
                           DnaPrefs.putString(getApplicationContext(), "STATE", state);
                           DnaPrefs.putString(getApplicationContext(), "COLLEGE", college);
                           DnaPrefs.putString(getApplicationContext(), Constants.MOBILE, loginResponse.getLoginDetails().get(0).getMobileNo());



                           if (!TextUtils.isEmpty(loginResponse.getLoginDetails().get(0).getInstitute_id())) {
                               DnaPrefs.putString(getApplicationContext(), Constants.INST_ID, loginResponse.getLoginDetails().get(0).getInstitute_id());
                               DnaPrefs.putString(getApplicationContext(), Constants.INST_NAME, loginResponse.getLoginDetails().get(0).getInstitute_name());
                               DnaPrefs.putString(getApplicationContext(), Constants.INST_IMAGE, loginResponse.getLoginDetails().get(0).getInstitute_logo());
                           } else {
                               DnaPrefs.putString(getApplicationContext(), Constants.INST_ID, "0");
                               DnaPrefs.putString(getApplicationContext(), Constants.INST_NAME, "");
                               DnaPrefs.putString(getApplicationContext(), Constants.INST_IMAGE, "");

                           }
                           DnaPrefs.putString(getApplicationContext(), "NAME", username);
                           DnaPrefs.putString(getApplicationContext(), "URL", "");
                           DnaPrefs.putString(getApplicationContext(), "EMAIL", email_str);

                           DnaPrefs.putBoolean(LoginActivity.this, Constants.LoginCheck, true);
                           startActivity(new Intent(LoginActivity.this, MainActivity.class));
                           finish();
                       } else if (Integer.parseInt(loginResponse.getStatus()) == 2) {
                           showLoginfailedDialog(loginResponse.getMessage());
                       } else {
                           Utils.displayToast(LoginActivity.this, "Invalid login detail");




                           Intent intent = new Intent(VerifyNewOTPActivity.this, ReferalActivity.class);
                       startActivity(intent);
                   }

                }
            }

            @Override
            public void onFailure(Call<VerifyOtpModel> call, Throwable t) {

            }
        });

    }




    @OnClick(R.id.nextBtn)
    public void onNextBtnClick() {
        if (pinLayout.getText().toString().length() >= 6) {
            verifyOtp(pinLayout.getText().toString());
        } else {
            Utils.displayToast(this, "Please enter OTP");
        }
    }


    @OnClick(R.id.resendOtp)
    public void resendOtp() {
        if (resendOTP.isClickable()) {
            RequestBody phonebRequestBody = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("mobile"));
            RequestBody course = RequestBody.create(MediaType.parse("text/plain"), "NEET-UG");
            Utils.showProgressDialog(this);
            RestClient.sendOtp(phonebRequestBody, new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        Utils.displayToast(getApplicationContext(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {


                    Toast.makeText(VerifyNewOTPActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void openKeyboard() {
        new Handler().postDelayed(() -> {
            pinLayout.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
            pinLayout.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
        }, 100);

    }


    // Hide soft keyboard
    private void hideKeyboard(View view) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null && view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void startTimer() {
        long otpTimerDuration = 30 * 1000; // 30 seconds in milliseconds

        CountDownTimer countTime = new CountDownTimer(otpTimerDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                resendOTP.setText("Resend OTP in: " + secondsLeft + " s");
            }

            @Override
            public void onFinish() {
                resendOTP.setText(getString(R.string.resend_otp));
            }


        };

        countTime.start();
    }

}