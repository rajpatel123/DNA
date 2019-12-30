package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.newqbankmodule.Module;
import com.dnamedical.Models.newqbankmodule.QBankResultResponse;
import com.dnamedical.Models.newqbankmodule.ResultData;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.dnamedical.views.CustomSeekBar;
import com.dnamedical.views.ProgressItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QbankStartTestActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView incorrectAnswer, correctAnswer, skkipedAnswer, yousolved, skipReview, curved,totalBookmark;
    private Button reviewMCQ;
    private String moduleID;
    private String userId;
    private LinearLayout dataView;
    CustomSeekBar seekBar;
    private ArrayList<ProgressItem> progressItemList;
    ImageView pauseImage;
    TextView testModuleName, testCompletedQuestion, testTotalQuestion, testTime;
    Button btnStart;
    Module module;
    LinearLayout linearLayoutStatus,bookmarLL;
    private long attemptedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_start_test);
        btnStart = findViewById(R.id.start_test);
        testModuleName = findViewById(R.id.test_name);
        testTotalQuestion = findViewById(R.id.total_questions);
        totalBookmark = findViewById(R.id.totalBookmark);
        pauseImage = findViewById(R.id.pause_image);
        testTime = findViewById(R.id.test_time);
        bookmarLL = findViewById(R.id.bookmarLL);
        testCompletedQuestion = findViewById(R.id.completed_question);
        linearLayoutStatus = findViewById(R.id.status);


        userId = DnaPrefs.getString(this, Constants.LOGIN_ID);
        btnStart.setOnClickListener(this);
        if (getIntent().hasExtra("module")) {
            module = getIntent().getParcelableExtra("module");
            attemptedTime = getIntent().getLongExtra("attemptedTime", 0);

            moduleID = module.getModuleId();
            if (module != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle(module.getChapterName());
                // getActionBar().setTitle(qbank_name);
                updateDataStatus();

            }
        }


        incorrectAnswer = findViewById(R.id.text_incorrect);
        correctAnswer = findViewById(R.id.correct_answer);
        skkipedAnswer = findViewById(R.id.skkiped_answer);
        yousolved = findViewById(R.id.yousolved);
        dataView = findViewById(R.id.dataView);
        seekBar = findViewById(R.id.seekBar);
        curved = findViewById(R.id.curved);

        skipReview = findViewById(R.id.skipreviewandexit);


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        reviewMCQ = findViewById(R.id.reviewbutton);

        skipReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        bookmarLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        reviewMCQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QbankStartTestActivity.this, QBankReviewResultActivity.class);
                intent.putExtra("module_id", moduleID);
                startActivity(intent);
            }
        });


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
                if (module.getTotalMcq() <= module.getTotalAttemptedmcq()) {

                    Intent intent = new Intent(QbankStartTestActivity.this, QBankReviewResultActivity.class);
                    intent.putExtra("module_id", moduleID);
                    startActivity(intent);
//                    Intent intent = new Intent(QbankStartTestActivity.this, QbankResultActivity.class);
//                    intent.putExtra("module_id", module.getModuleId());
//                    intent.putExtra("userId", userId);
//                    startActivity(intent);
//                    finish();
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
        // updateStatus();


    }




    private void updateDataStatus() {
        if (module != null) {
            testModuleName.setText(module.getChapterName());
            testTotalQuestion.setText(module.getTotalMcq() + " MCQ's");
            totalBookmark.setText(module.getTotal_bookmarks() + " MCQ's Bookmarked");
            linearLayoutStatus.setVisibility(View.GONE);
            if (module.getTotalMcq() <= module.getTotalAttemptedmcq()) {
                btnStart.setText("REVIEW");
                testCompletedQuestion.setText("All Completed");
                Picasso.with(this).load(R.drawable.qbank_right_answer).into(pauseImage);
                linearLayoutStatus.setVisibility(View.VISIBLE);
                getMCQResult();

                testTime.setText("You have completed this mudule on " + Utils.dateFormatForPlan(attemptedTime));
            } else {
                testCompletedQuestion.setText(module.getTotalAttemptedmcq() + " Completed");
                btnStart.setText("SOLVE");
                if (module.getTotalAttemptedmcq() > 0) {
                    testTime.setText("You have paused this mudule on " + Utils.dateFormatForPlan(attemptedTime));
                    linearLayoutStatus.setVisibility(View.VISIBLE);
                    Picasso.with(this).load(R.drawable.paused_icon).into(pauseImage);

                }
            }


        }
    }

    private void getMCQResult() {

        userId = DnaPrefs.getString(this, Constants.LOGIN_ID);

        Utils.showProgressDialog(QbankStartTestActivity.this);
        RequestBody module = RequestBody.create(MediaType.parse("text/plain"), moduleID);

        RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plain"), userId);


        RestClient.getMCQResult(userIdRequest, module, new Callback<QBankResultResponse>() {
            @Override
            public void onResponse(Call<QBankResultResponse> call, Response<QBankResultResponse> response) {
                Utils.dismissProgressDialog();
                QBankResultResponse resultResponse = response.body();
                updateData(resultResponse);
            }

            @Override
            public void onFailure(Call<QBankResultResponse> call, Throwable t) {
                Utils.dismissProgressDialog();
                Log.d("Error", "" + t.getMessage());
            }
        });

    }

    private void updateData(QBankResultResponse resultResponse) {

        if (resultResponse != null && resultResponse.getStatus()) {

            ResultData result = resultResponse.getResult();
            incorrectAnswer.setText("" + result.getWrong());
            correctAnswer.setText("" + result.getCurrect());
            skkipedAnswer.setText("" + result.getSkipped());

            float wrong = Float.parseFloat(result.getWrong());
            float correct = Float.parseFloat(result.getCurrect());
            float skip = Float.parseFloat(result.getSkipped());



            progressItemList = new ArrayList<ProgressItem>();
            // red span
            ProgressItem progressItem = new ProgressItem();
            progressItem.progressItemPercentage = (30);
            progressItem.progressItemPercentage = (wrong / result.getTotalMcq()) * 100;

            Log.i("Mainactivity", progressItem.progressItemPercentage + "");
            progressItemList.add(progressItem);


            ProgressItem progressItemCorrect = new ProgressItem();
            progressItemCorrect.progressItemPercentage = (correct / result.getTotalMcq()) * 100;
            Log.i("Mainactivity", progressItemCorrect.progressItemPercentage + "");
            progressItemList.add(progressItemCorrect);

            ProgressItem progressItemSkipp = new ProgressItem();
            progressItemSkipp.progressItemPercentage =(skip / result.getTotalMcq()) * 100;
            Log.i("Mainactivity", progressItemSkipp.progressItemPercentage + "");
            progressItemList.add(progressItemSkipp);

            seekBar.initData(progressItemList);
            seekBar.invalidate();
            seekBar.setThumb(null);

            curved.setText(result.getPercentage()+"%");
            yousolved.setText("You solved " + result.getTotalMcq() + " high yield MCQs and got " + result.getPercentage() + "% correct");
            dataView.setVisibility(View.VISIBLE);
        }
    }
}










