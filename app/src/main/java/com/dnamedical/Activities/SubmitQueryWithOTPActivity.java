package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.dnamedical.Models.franchies.FranchiesResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.messageReceiver.SmsListener;
import com.dnamedical.messageReceiver.SmsReceiver;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitQueryWithOTPActivity extends AppCompatActivity implements SmsListener {

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
    private TextView resendTimer;

    String username1;
    String email;
    String mobile;
    String whatsppNumbertxt;

    String pCitytxt;
    String pStatetxt;
    String pAddresstxt;
    String pLandmarktxt;

    String pPincodetxt;
    String collegaeFrenchisetxt;
    String cMedicalCollegaetxt;
    String sMedicalCollegetxt;
    String pinMedicalCollegetxt;
    String amountToInveststr;

    String canCallStr;
    String comment1;

    FranchiActivity franchiActivity;

    public static void start(FranchiActivity franchiActivity, String username1, String email, String mobile, String whatsppNumbertxt,
                             String pCitytxt, String pStatetxt, String pAddresstxt, String pLandmarktxt,
                             String pPincodetxt, String collegaeFrenchisetxt, String cMedicalCollegaetxt,
                             String sMedicalCollegetxt, String pinMedicalCollegetxt, String amountToInveststr,
                             String canCallStr, String comment1) {

        Intent intent = new Intent(franchiActivity, SubmitQueryWithOTPActivity.class);
        intent.putExtra("username1", username1);
        intent.putExtra("email", email);
        intent.putExtra("mobile", mobile);
        intent.putExtra("whatsppNumbertxt", whatsppNumbertxt);
        intent.putExtra("pCitytxt", pCitytxt);
        intent.putExtra("pStatetxt", pStatetxt);
        intent.putExtra("pAddresstxt", pAddresstxt);
        intent.putExtra("pLandmarktxt", pLandmarktxt);
        intent.putExtra("pPincodetxt", pPincodetxt);
        intent.putExtra("collegaeFrenchisetxt", collegaeFrenchisetxt);
        intent.putExtra("cMedicalCollegaetxt", cMedicalCollegaetxt);
        intent.putExtra("sMedicalCollegetxt", sMedicalCollegetxt);
        intent.putExtra("pinMedicalCollegetxt", pinMedicalCollegetxt);
        intent.putExtra("amountToInveststr", amountToInveststr);
        intent.putExtra("canCallStr", canCallStr);
        intent.putExtra("comment1", comment1);

        franchiActivity.startActivityForResult(intent, 100);


    }


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
        resendTimer = findViewById(R.id.resendTimer);

        initDataForApi(getIntent());
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

    private void initDataForApi(Intent intent) {
        username1 = intent.getStringExtra("username1");
        email = intent.getStringExtra("email");
        mobile = intent.getStringExtra("mobile");
        whatsppNumbertxt = intent.getStringExtra("whatsppNumbertxt");
        pCitytxt = intent.getStringExtra("pCitytxt");
        pStatetxt = intent.getStringExtra("pStatetxt");
        pAddresstxt = intent.getStringExtra("pAddresstxt");
        pLandmarktxt = intent.getStringExtra("pLandmarktxt");
        pPincodetxt = intent.getStringExtra("pPincodetxt");
        collegaeFrenchisetxt = intent.getStringExtra("collegaeFrenchisetxt");
        cMedicalCollegaetxt = intent.getStringExtra("cMedicalCollegaetxt");
        sMedicalCollegetxt = intent.getStringExtra("sMedicalCollegetxt");
        pinMedicalCollegetxt = intent.getStringExtra("pinMedicalCollegetxt");
        amountToInveststr = intent.getStringExtra("amountToInveststr");
        canCallStr = intent.getStringExtra("canCallStr");
        comment1 = intent.getStringExtra("comment1");
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


        RequestBody name = RequestBody.create(MediaType.parse("text/plane"), username1);
        RequestBody phoneNo = RequestBody.create(MediaType.parse("text/plane"), mobile);

        RestClient.sendOTPFrenchise(name, phoneNo, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utils.dismissProgressDialog();
                if (response != null && response.code() == 200 && response.body() != null) {
                    Toast.makeText(getApplicationContext(), "Otp resent on your mobile number", Toast.LENGTH_SHORT).show();
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
            submitFrenchiesQuery();
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


    private void submitFrenchiesQuery() {
        RequestBody Otp = RequestBody.create(MediaType.parse("text/plane"), otpNumber);

        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), username1);
        RequestBody usermail = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody phoneno = RequestBody.create(MediaType.parse("text/plain"), mobile);
        RequestBody whatsppNumber = RequestBody.create(MediaType.parse("text/plain"), whatsppNumbertxt);

        RequestBody pCity = RequestBody.create(MediaType.parse("text/plain"), pCitytxt);
        RequestBody pState = RequestBody.create(MediaType.parse("text/plain"), pStatetxt);
        RequestBody pAddress = RequestBody.create(MediaType.parse("text/plain"), pAddresstxt);
        RequestBody pLandmark = RequestBody.create(MediaType.parse("text/plain"), pLandmarktxt);

        RequestBody pPincode = RequestBody.create(MediaType.parse("text/plain"), pPincodetxt);
        RequestBody collegaeFrenchise = RequestBody.create(MediaType.parse("text/plain"), collegaeFrenchisetxt);
        RequestBody cMedicalCollegae = RequestBody.create(MediaType.parse("text/plain"), cMedicalCollegaetxt);

        RequestBody sMedicalCollege = RequestBody.create(MediaType.parse("text/plain"), sMedicalCollegetxt);
        RequestBody pinMedicalCollege = RequestBody.create(MediaType.parse("text/plain"), pinMedicalCollegetxt);
        RequestBody amount = RequestBody.create(MediaType.parse("text/plain"), amountToInveststr);
        RequestBody canCallfromdna = RequestBody.create(MediaType.parse("text/plain"), canCallStr);
        RequestBody comment = RequestBody.create(MediaType.parse("text/plain"), comment1);


        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.franchiesRegister(username, usermail, phoneno, whatsppNumber, pCity, pState, pAddress,
                    pLandmark, pPincode, collegaeFrenchise, cMedicalCollegae, sMedicalCollege,
                    pinMedicalCollege, comment, amount, canCallfromdna, Otp,
                    new Callback<FranchiesResponse>() {
                        @Override
                        public void onResponse(Call<FranchiesResponse> call, Response<FranchiesResponse> response) {
                            Utils.dismissProgressDialog();
                            if (response.code() == 200 && response.body() != null) {
                                Toast.makeText(SubmitQueryWithOTPActivity.this, "Query submitted Successfully", Toast.LENGTH_SHORT).show();
                                Intent data = new Intent();
                                setResult(RESULT_OK, data);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<FranchiesResponse> call, Throwable t) {
                            Utils.dismissProgressDialog();
                            Toast.makeText(SubmitQueryWithOTPActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, " Internet Connection Failed!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Toast.makeText(SubmitQueryWithOTPActivity.this, "Query submitted Successfully", Toast.LENGTH_SHORT).show();
        Intent data = new Intent();
        setResult(RESULT_CANCELED, data);
        finish();
    }
}


