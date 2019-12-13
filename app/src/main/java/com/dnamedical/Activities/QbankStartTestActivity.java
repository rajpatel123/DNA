package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.newqbankmodule.Module;
import com.dnamedical.Models.qbankstart.QbankstartResponse;
import com.dnamedical.R;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

public class QbankStartTestActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView backImage, pauseImage;
    TextView testModuleName, testCompletedQuestion, testTotalQuestion, testTime;
    String qbank_module_id, qbank_name;
    Button btnStart;
    String num;
    String userId;
    Module module;
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


        userId = DnaPrefs.getString(this, Constants.LOGIN_ID);
        btnStart.setOnClickListener(this);
        if (getIntent().hasExtra("module")) {
            module = getIntent().getParcelableExtra("module");
        }


        // getActionBar().setTitle(qbank_name);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(module.getChapterName());
            testModuleName.setText(module.getChapterName());
            testTotalQuestion.setText(module.getTotalMcq() + " MCQ's");
            testCompletedQuestion.setText(module.getTotalAttemptedmcq() + " Completed");


            if (module.getTotalAttemptedmcq() > 0) {
                linearLayoutStatus.setVisibility(View.VISIBLE);
                testTime.setText("You have paused this mudule on " + Utils.dateFormatForPlan(System.currentTimeMillis()));
            } else {
                linearLayoutStatus.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_test:
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!TextUtils.isEmpty(module.getIsCompleted()) && module.getIsCompleted().equalsIgnoreCase("1")) {
                            Intent intent = new Intent(QbankStartTestActivity.this, TestReviewResultActivity.class);
                            intent.putExtra("qmodule_id", module.getModuleId());
                            intent.putExtra("userId", userId);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(QbankStartTestActivity.this, QbankTestActivity.class);
                            intent.putExtra("qmodule_id", module.getModuleId());
                            intent.putExtra("userId", userId);
                            intent.putExtra("questionStartId", module.getTotalAttemptedmcq()+1);
                            startActivity(intent);
                            finish();
                        }

                    }
                });

                break;
        }

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

