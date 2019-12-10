package com.dnamedical.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class TestStartActivity extends AppCompatActivity {


    @BindView(R.id.test_topic)
    TextView testTopic;

    @BindView(R.id.test_information)
    TextView testInformation;

    @BindView(R.id.start_desc)
    TextView start_desc;

    @BindView(R.id.start_date)
    TextView start_date;

    @BindView(R.id.btn_Start)
    Button btnStart;


    @BindView(R.id.card_view)
    CardView cardView;

    String test_id, duration, testName, testQuestion = "0", testPaid;
    String description;
    private long startDate;
    private long resultDate, endDate;
    private String testStatus;
    private String subjectName = "19 Subjects of MBBS";
    private String no_of_sub;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_start);
        ButterKnife.bind(this);


        user_id = DnaPrefs.getString(TestStartActivity.this, Constants.LOGIN_ID);

        Intent intent = getIntent();
        if (intent != null) {
            test_id = intent.getStringExtra("id");
            duration = intent.getStringExtra("duration");
            startDate = Long.parseLong(intent.getStringExtra("startDate"));
            resultDate = Long.parseLong(intent.getStringExtra("resultDate"));
            endDate = Long.parseLong(intent.getStringExtra("endDate"));
            no_of_sub = intent.getStringExtra("no_of_sub");

            if (intent.hasExtra("subjectName")) {
                subjectName = intent.getStringExtra("subjectName");
            }
            testName = intent.getStringExtra("testName");
            String type = intent.getStringExtra("type");
            testQuestion = intent.getStringExtra("testQuestion");
            testStatus = intent.getStringExtra("testStatus");
            description = intent.getStringExtra("desc");
            testPaid = intent.getStringExtra("testPaid");

            if (!testStatus.equalsIgnoreCase("open")) {
                testTopic.setText(testName);
                updateTestTypeText(type);
                testTopic.setText(testName);
                updateTestTypeText(type);
                btnStart.setText("Review Test");
                btnStart.setVisibility(View.VISIBLE);
                start_date.setVisibility(View.GONE);
                start_desc.setVisibility(View.GONE);

            } else {
                if ((System.currentTimeMillis() - (startDate * 1000) >= 0)) {
                    if (resultDate * 1000 < System.currentTimeMillis()) {
                        btnStart.setText("TEST RESULT HAS BEEN DECLARED, YOU CAN ATTEMPT THE TEST BUT RANK WILL BE RELATIVE RANK ( NOT ACTUAL RANK ).");
                        testTopic.setText(testName);
                        updateTestTypeText(type);
                        btnStart.setVisibility(View.VISIBLE);

                    } else {

                        if (endDate*1000<System.currentTimeMillis()){
                            btnStart.setText("TEST TIME OVER AND RESULT HAS NOT BEEN DECLARED.\n" +
                                    "YOU CAN ATTEMPT THE TEST AFTER RESULT DECLARATION.");
                            btnStart.setVisibility(View.VISIBLE);
                            btnStart.setEnabled(false);
                            btnStart.setVisibility(View.VISIBLE);
                        }else{
                            btnStart.setText("START THE TEST");
                            btnStart.setVisibility(View.VISIBLE);
                            btnStart.setEnabled(true);
                            btnStart.setVisibility(View.VISIBLE);
                        }


                    }

                    testTopic.setText(testName);
                    updateTestTypeText(type);
                    testTopic.setText(testName);
                    updateTestTypeText(type);
                    start_date.setVisibility(View.GONE);
                    start_desc.setVisibility(View.GONE);

                } else {
                    testTopic.setText(testName);
                    updateTestTypeText(type);
                    btnStart.setText("Start The Test");
                    btnStart.setVisibility(View.GONE);
                    start_date.setText(Utils.startTimeFormat(startDate * 1000));
                    start_desc.setText("This test will start on: ");
                    start_date.setVisibility(View.VISIBLE);
                    start_desc.setVisibility(View.VISIBLE);
                }

            }


        }

        /*if (testPaid.equalsIgnoreCase("No")) {
            btnStart.setVisibility(View.GONE);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaymentAlertDialog();
                }
            });
        }*/

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (testStatus.equalsIgnoreCase("open")) {
                    if (testQuestion.equalsIgnoreCase("0")) {
                        Toast.makeText(TestStartActivity.this, "No questions in this test", Toast.LENGTH_LONG).show();
                        return;
                    }
                    StartTest();
                } else {

                    if (Utils.isInternetConnected(TestStartActivity.this)) {
                        if (resultDate * 1000 > System.currentTimeMillis()) {
                            Toast.makeText(TestStartActivity.this, "Result does not declared, review will be available on " + Utils.startTimeFormat(resultDate * 1000), Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(TestStartActivity.this, ResultActivity.class);

                            intent.putExtra("resultDate", resultDate);
                            intent.putExtra(Constants.ISDAILY_TEST, false);
                            intent.putExtra("testid", test_id);
                            startActivity(intent);
                            finish();
                        }

                    } else {
                        Toast.makeText(TestStartActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
    }


    private void updateTestTypeText(String type) {
        switch (type) {

            case "daily_test":
                testInformation.setText("This test contains " + testQuestion + " Q's from " + no_of_sub + " with time duration of " + Utils.getTestDurationDuration(Integer.parseInt(duration)));
                break;

            case "grand_test":
                testInformation.setText("This test contains " + testQuestion + " Q's from " + no_of_sub + "  with time duration of " + Utils.getTestDurationDuration(Integer.parseInt(duration)));
                break;

            case "mini_test":
                testInformation.setText("This test contains " + testQuestion + " Q's from  " + no_of_sub + "  with time duration of " + Utils.getTestDurationDuration(Integer.parseInt(duration)));
                break;

            case "subject_test":
                testInformation.setText("This test contains " + testQuestion + " Q's from " + no_of_sub + "  with time duration of " + Utils.getTestDurationDuration(Integer.parseInt(duration)));
                break;

        }
    }

    private void StartTest() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.start_test_alertdialog, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog dialog = dialogBuilder.create();
        Button btn_yes = dialogView.findViewById(R.id.btn_done);
        TextView text_cancel = dialogView.findViewById(R.id.text_cancel);
        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isInternetConnected(TestStartActivity.this)) {

                    startTest();

                } else {
                    Toast.makeText(TestStartActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();


            }
        });

        dialog.show();

    }

    private void startTest() {
        if (!Utils.isInternetConnected(this)) {
            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(test_id)) {
            Toast.makeText(this, "Unable to start Test, lease try again later", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), test_id);
        RequestBody time = RequestBody.create(MediaType.parse("text/plain"), "" + (System.currentTimeMillis() / 1000));
        Utils.showProgressDialog(TestStartActivity.this);
        RestClient.startTest(userId, testID, time, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody testResult = response.body();
                Utils.dismissProgressDialog();
                Intent intent = new Intent(TestStartActivity.this, TestV1Activity.class);
                DnaPrefs.putBoolean(TestStartActivity.this, Constants.Resultsubmit, true);
                test_id = getIntent().getStringExtra("id");
                testName = getIntent().getStringExtra("testName");
                intent.putExtra("id", test_id);
                intent.putExtra(Constants.ISDAILY_TEST, false);
                intent.putExtra("duration", duration);
                intent.putExtra("testName", testName);
                intent.putExtra("resultDate", resultDate);
                intent.putExtra("endDate", endDate);


                startActivity(intent);
                finish();


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.dismissProgressDialog();
            }
        });

    }


}