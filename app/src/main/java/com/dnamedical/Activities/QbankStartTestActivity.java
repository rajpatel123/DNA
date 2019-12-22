package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.newqbankmodule.Module;
import com.dnamedical.Models.newqbankmodule.ModuleResponse;
import com.dnamedical.Models.newqbankmodule.ModulesMcq;
import com.dnamedical.Models.qbankstart.QbankstartResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

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


            if (module != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle(module.getChapterName());
                // getActionBar().setTitle(qbank_name);
                updateData();

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
                if (module.getTotalMcq()<=module.getTotalAttemptedmcq()) {
                    Intent intent = new Intent(QbankStartTestActivity.this, QbankResultActivity.class);
                    intent.putExtra("module_id", module.getModuleId());
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(QbankStartTestActivity.this, QbankTestActivity.class);
                    intent.putExtra("qmodule_id", module.getModuleId());
                    intent.putExtra("userId", userId);
                    intent.putExtra("chap_ID", module.getChapterId());
                    intent.putExtra("questionStartId", module.getTotalAttemptedmcq());
                    startActivityForResult(intent, 12);
                    finish();
                }

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateStatus();


    }


    public void updateStatus() {
        // Utils.showProgressDialog(qbankTestActivity);

        RequestBody moduleID = RequestBody.create(MediaType.parse("text/plain"), module.getModuleId());

        RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plain"), userId);




        RestClient.updateQBankStatus(userIdRequest,moduleID, new Callback<ModuleResponse>() {
            @Override
            public void onResponse(Call<ModuleResponse> call, Response<ModuleResponse> response) {
                // Utils.dismissProgressDialog();
                module =response.body().getDetails().get(0);

                updateData();
            }

            @Override
            public void onFailure(Call<ModuleResponse> call, Throwable t) {
                // Utils.dismissProgressDialog();
            }
        });
    }

    private void updateData() {
        if (module!=null){
            testModuleName.setText(module.getChapterName());
            testTotalQuestion.setText(module.getTotalMcq() + " MCQ's");
            linearLayoutStatus.setVisibility(View.GONE);
            if (module.getTotalMcq()<=module.getTotalAttemptedmcq()) {
                btnStart.setText("REVIEW");
                testCompletedQuestion.setText("All Completed");
                Picasso.with(this).load(R.drawable.qbank_right_answer).into(pauseImage);
                linearLayoutStatus.setVisibility(View.VISIBLE);
                testTime.setText("You have paused this mudule on " + Utils.dateFormatForPlan(module.getModule_submit_time()));
            } else {
                testCompletedQuestion.setText(module.getTotalAttemptedmcq() + " Completed");
                btnStart.setText("SOLVE");
                if (module.getTotalAttemptedmcq() >0) {
                    testTime.setText("You have completed this mudule on " + Utils.dateFormatForPlan(module.getModule_submit_time()));
                    linearLayoutStatus.setVisibility(View.VISIBLE);
                    Picasso.with(this).load(R.drawable.paused_icon).into(pauseImage);

                }
            }


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

