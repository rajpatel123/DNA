package com.dnamedical.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Models.qbankstart.Detail;
import com.dnamedical.Models.qbankstart.QbankstartResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QbankStartTestActivity extends AppCompatActivity implements View.OnClickListener {

    TextView testName;
    ImageView backImage, pauseImage;
    TextView testModuleName, testCompletedQuestion, testTotalQuestion, testTime;
    String qbank_module_id, qbank_name;
    Button btnStart;
    String num;
    String userId;
    LinearLayout linearLayoutStatus;
    QbankstartResponse qbankstartResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_start_test);
        btnStart = findViewById(R.id.start_test);
        testModuleName = findViewById(R.id.test_name);
        testTotalQuestion = findViewById(R.id.total_questions);
        pauseImage = findViewById(R.id.pause_image);
        testTime = findViewById(R.id.test_time);
        testCompletedQuestion = findViewById(R.id.completed_question);
        linearLayoutStatus = findViewById(R.id.status);

        testName = findViewById(R.id.qbank_sub_subcategory_name);

        backImage = findViewById(R.id.back_button);
        btnStart.setOnClickListener(this);
        backImage.setOnClickListener(this);
        if (getIntent().hasExtra("qmodule_id")) {
            qbank_module_id = getIntent().getStringExtra("qmodule_id");
            qbank_name = getIntent().getStringExtra("qmodule_name");

        }
        testName.setText(qbank_name);
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // getActionBar().setTitle(qbank_name);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStartData();
    }

    private void getStartData() {
        userId = DnaPrefs.getString(getApplicationContext(), "Login_Id");
        String isPaused = "1";
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody is_paused = RequestBody.create(MediaType.parse("text/plain"), isPaused);
        RequestBody qmodule_id = RequestBody.create(MediaType.parse("text/plain"), qbank_module_id);
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.qbankStart(qmodule_id, user_id, is_paused, new Callback<QbankstartResponse>() {
                @Override
                public void onResponse(Call<QbankstartResponse> call, Response<QbankstartResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            qbankstartResponse = response.body();
                            testModuleName.setText("" + qbankstartResponse.getDetails().get(0).getModuleName());
                            testTotalQuestion.setText("" + qbankstartResponse.getDetails().get(0).getTotalmcq() + " MCQs");
                            testCompletedQuestion.setText("" + qbankstartResponse.getDetails().get(0).getTotalattempedmcq() + " Completed");
                            //num = String.valueOf(Integer.parseInt(qbankstartResponse.getDetails().get(0).getTotalattempedmcq()));
                            if (qbankstartResponse.getDetails().get(0).getTotalmcq().equalsIgnoreCase(qbankstartResponse.getDetails().get(0).getTotalattempedmcq())) {
                                btnStart.setText("Review");
                                pauseImage.setImageResource(R.drawable.qbank_right_answer);
                                testTime.setText("You've Completed this module " + qbankstartResponse.getDetails().get(0).getLastattempedquesdate());
                                testCompletedQuestion.setText(qbankstartResponse.getDetails().get(0).getTotalattempedmcq() + " " + "Completed");
                            } else {
                                if (qbankstartResponse.getDetails().get(0).getLastattempedquesdate() != null) {
                                    testTime.setText("You paused this module on " + qbankstartResponse.getDetails().get(0).getLastattempedquesdate());
                                } else {
                                    linearLayoutStatus.setVisibility(View.GONE);
                                }

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<QbankstartResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(QbankStartTestActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Connection Internet Failed", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_test:
                getTest();
                break;
        }

    }

    private void getTest() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qbankstartResponse.getDetails().get(0).getTotalmcq()
                        .equalsIgnoreCase(qbankstartResponse.getDetails().get(0).getTotalattempedmcq())) {
                    Intent intent = new Intent(QbankStartTestActivity.this, QbankResultListActivity.class);
                    intent.putExtra("qmodule_id", qbank_module_id);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                    //Toast.makeText(QbankStartTestActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(QbankStartTestActivity.this, QbankTestActivity.class);
                    intent.putExtra("qmodule_id", qbank_module_id);
                    intent.putExtra("userId", userId);
                    intent.putExtra("questionStartId", qbankstartResponse.getDetails().get(0).getTotalattempedmcq());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
  /*  // Enable or disable and change button text by EditText text length.
    private void processButtonByTextLength()
    {

        if(testCompletedQuestion==testTotalQuestion)
        {
            button.setText("I Am Enabled.");
            button.setEnabled(true);
        }else
        {
             button.setText("I Am Disabled.");
             button.setEnabled(false);
        }
    }*/
}

