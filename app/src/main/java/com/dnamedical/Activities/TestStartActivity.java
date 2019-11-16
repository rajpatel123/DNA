package com.dnamedical.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.R;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    String id, duration, testName, testQuestion = "0", testPaid;
    String description;
    private long startDate;
    private long resultDate;
    private String testStatus;
    private String subjectName = "19 Subjects of MBBS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_start);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            duration = intent.getStringExtra("duration");
            startDate = Long.parseLong(intent.getStringExtra("startDate"));
            resultDate = Long.parseLong(intent.getStringExtra("resultDate"));

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

                        if (DnaPrefs.getString(TestStartActivity.this, Constants.INST_ID).equalsIgnoreCase("0")) {
                            btnStart.setText("Test result declared you can not attempt this test");
                            btnStart.setVisibility(View.VISIBLE);
                        } else {
                            btnStart.setText("Test result declared want to start the test");
                            btnStart.setVisibility(View.VISIBLE);


                        }
                        return;

                    } else {
                        btnStart.setText("Start The Test");
                    }

                    testTopic.setText(testName);
                    updateTestTypeText(type);
                    testTopic.setText(testName);
                    updateTestTypeText(type);
                    btnStart.setText("Start The Test");
                    btnStart.setVisibility(View.VISIBLE);
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
                        Intent intent = new Intent(TestStartActivity.this, ResultActivity.class);
                        intent.putExtra("testid", id);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(TestStartActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    private void PaymentAlertDialog() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.payment_alert_dialog, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        Button btn_learnmore = dialogView.findViewById(R.id.btn_learn_more);
        Button btn_view_plans = dialogView.findViewById(R.id.btn_view_plans);

        TextView text_cancel = dialogView.findViewById(R.id.text_cancel);
        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        btn_learnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TestStartActivity.this, DNAKnowmoreActivity.class);
                startActivity(intent);
                finish();
                dialog.dismiss();


            }
        });

        btn_view_plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TestStartActivity.this, DNASuscribeActivity.class);
                startActivity(intent);
                finish();
                dialog.dismiss();

            }
        });

        dialog.show();

    }


    private void updateTestTypeText(String type) {
        switch (type) {

            case "daily_test":


                if (DnaPrefs.getBoolean(this, Constants.FROM_INSTITUTE)) {
                    testInformation.setText("This test contains " + testQuestion + " Q's from all 3 Subjects with time duration of " + Utils.getTestDurationDuration(Integer.parseInt(duration)));
                } else {
                    testInformation.setText("This test contains " + testQuestion + " Q's from all 19 Subjects with time duration of " + Utils.getTestDurationDuration(Integer.parseInt(duration)));
                }
                break;

            case "grand_test":
                testInformation.setText("This test contains " + testQuestion + " Q's from all 19 Subjects of MBBS with time duration of " + Utils.getTestDurationDuration(Integer.parseInt(duration)));
                break;

            case "mini_test":
                testInformation.setText("This test contains " + testQuestion + " Q's from all 19 Subjects of MBBS with time duration of " + Utils.getTestDurationDuration(Integer.parseInt(duration)));
                break;

            case "subject_test":
                testInformation.setText("This test contains " + testQuestion + " Q's from " + subjectName + " Subject of MBBS with time duration of " + Utils.getTestDurationDuration(Integer.parseInt(duration)));
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
                    Intent intent = new Intent(TestStartActivity.this, TestV1Activity.class);
                    DnaPrefs.putBoolean(TestStartActivity.this, Constants.Resultsubmit, true);
                    id = getIntent().getStringExtra("id");
                    testName = getIntent().getStringExtra("testName");
                    intent.putExtra("id", id);
                    intent.putExtra("duration", duration);
                    intent.putExtra("testName", testName);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(TestStartActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();


            }
        });

        dialog.show();

    }


}