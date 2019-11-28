package com.dnamedical.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dnamedical.Models.VerifyOtpResponse;
import com.dnamedical.Models.registration.CommonResponse;
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


public class VerifyOTPActivity extends AppCompatActivity {
    @BindView(R.id.crossBtn)
    AppCompatImageView crossBtn;

    @BindView(R.id.otpTxt)
    TextView otpTxt;
    @BindView(R.id.pin_layout)
    PinEntryEditText pinLayout;
    @BindView(R.id.resendOtp)
    TextView resendOtp;
    @BindView(R.id.nextBtn)
    AppCompatButton nextBtn;
    @BindView(R.id.pin_content_layout)
    RelativeLayout pinContentLayout;


    boolean isSocial;
    boolean isPhone;

    private final int COUNT_TIMER = 60 * 1000;
    private final int COUNT_INTERVAL = 1000;
    private String user_id;
    private String phone;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_otp);
        ButterKnife.bind(this);


        SpannableString spannableString = new SpannableString(resendOtp.getText().toString());
        spannableString.setSpan(new UnderlineSpan(), 0, resendOtp.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        resendOtp.setText(spannableString);

        Intent intent = getIntent();

        user_id = intent.getStringExtra("user_id");
        phone = intent.getStringExtra("mobile");

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

        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody otp = RequestBody.create(MediaType.parse("text/plain"), code);
        Utils.showProgressDialog(this);
        //showProgressDialog(this);
        RestClient.verifyOtp(userid,otp, new Callback<VerifyOtpResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                Utils.dismissProgressDialog();
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Utils.displayToast(getApplicationContext(), response.body().getMessage());
                    String id=response.body().getLoginDetails().get(0).getId();
                    DnaPrefs.putString(getApplicationContext(), Constants.LOGIN_ID,response.body().getLoginDetails().get(0).getId());
                    DnaPrefs.putBoolean(getApplicationContext(),"isFacebook",false);

                    DnaPrefs.putString(getApplicationContext(),"NAME",response.body().getLoginDetails().get(0).getName());
                    DnaPrefs.putString(getApplicationContext(),"URL","");
                    DnaPrefs.putString(getApplicationContext(),"EMAIL",response.body().getLoginDetails().get(0).getEmailId());
                    startActivity(new Intent(VerifyOTPActivity.this,MainActivity.class));
                    finish();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {

            }
        });

    }

    @OnClick(R.id.crossBtn)
    public void onCrossBtnClick() { finish();
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
        if (resendOtp.isClickable()) {
            RequestBody phonebRequestBody = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("mobile"));
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


                    Toast.makeText(VerifyOTPActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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


}