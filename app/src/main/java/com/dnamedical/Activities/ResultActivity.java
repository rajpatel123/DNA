package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.dnamedical.Adapters.ResultAdapter;
import com.dnamedical.Adapters.result;
import com.dnamedical.Models.ResultData.AllReult;
import com.dnamedical.Models.ResultData.ResultList;
import com.dnamedical.Models.ResultData.UserResult;
import com.dnamedical.Models.test.testresult.TestResult;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import hiennguyen.me.circleseekbar.CircleSeekBar;
import me.tankery.lib.circularseekbar.CircularSeekBar;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Starting new repository
 */
public class ResultActivity extends AppCompatActivity {


    TextView dateTv, percentValue, testNameTv, totalUser, totalQuestion, userRank, userNumber;
    CircularSeekBar correct, wrong, skipped;
    TextView correctTXT, wrongTXT, skippedTXT;

    private List<UserResult> userResults;
    private List<ResultList> resultLists;
    private List<AllReult> allReults;
    private RecyclerView recyclerView;
    private ResultAdapter resultAdapter;
    private Button reviewButton, shareButton;
    private CircleSeekBar circleSeekBar;
    String user_id;
    String tquestion, average, canswer, wanswer, sanswer;
    TestResult testResult;
    TextView rankTV,dateTV,startTimeTV,endTimeTv,yourScoreTV,totalMarksTv,
            percentageTV,percentaileTV;
    TextView guessCtoWtv,guessWtoCtv,guessWtoWtv,guessTotalSwitchedtv;
    TextView diffEasyCurrectTV,diffMediumCurrectTV,diffHardCurrectTV,
             diffEasyWrongTV,diffMediumWrongTV,diffHardWrongTV,diffEasySkipTV,diffMediumSkipTV,diffHardSkipTV;
    TextView timeTakenMarkedTV,timeTakenSkipTV,totalTimeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        rankTV=findViewById(R.id.resultRank);
        dateTV=findViewById(R.id.resultDate);
        startTimeTV=findViewById(R.id.resultStratTime);
        endTimeTv=findViewById(R.id.resultEndTime);
        yourScoreTV=findViewById(R.id.yourScore);
        totalMarksTv=findViewById(R.id.totalMarks);
        percentageTV=findViewById(R.id.percentage);
        percentaileTV=findViewById(R.id.percentaile);

        guessCtoWtv=findViewById(R.id.cToW);
        guessWtoCtv=findViewById(R.id.wToc);
        guessWtoWtv=findViewById(R.id.wTow);
        guessTotalSwitchedtv=findViewById(R.id.totalSwitched);

        diffEasyCurrectTV=findViewById(R.id.diffEasyCurrect);
        diffMediumCurrectTV=findViewById(R.id.diffMediumCurrect);
        diffHardCurrectTV=findViewById(R.id.diffHardCurrect);

        diffEasyWrongTV=findViewById(R.id.diffEasyWrong);
        diffMediumWrongTV=findViewById(R.id.diffMediumWrong);
        diffHardWrongTV=findViewById(R.id.diffHardWrong);

        diffEasySkipTV=findViewById(R.id.diffEasySkip);
        diffMediumSkipTV=findViewById(R.id.diffMediumSkip);
        diffHardSkipTV=findViewById(R.id.diffHardSkip);
        timeTakenMarkedTV=findViewById(R.id.timeTakenMarkedQus);
        timeTakenSkipTV=findViewById(R.id.timeTakenSkipQus);
        totalTimeTV=findViewById(R.id.total_Time_Qus);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.RESULT)) {
            testResult = intent.getParcelableExtra(Constants.RESULT);
        }
        //Profile Rank

        rankTV.setText(testResult.getData().getRank());
        startTimeTV.setText(testResult.getData().getStartTime());
        endTimeTv.setText(testResult.getData().getEndTime());
        yourScoreTV.setText(testResult.getData().getYourScore());
        totalMarksTv.setText(testResult.getData().getTotalMarks());
        percentageTV.setText(testResult.getData().getPercenatge());
        percentaileTV.setText(testResult.getData().getPercentile());

        //Guess Analysis
        guessCtoWtv.setText(testResult.getData().getGuessAnalysis().getCorrectToWrong());
        guessWtoCtv.setText(testResult.getData().getGuessAnalysis().getWrongToCorrect());
        guessWtoWtv.setText(testResult.getData().getGuessAnalysis().getWrongToWrong());
        guessTotalSwitchedtv.setText(testResult.getData().getGuessAnalysis().getTotalSwitch());

        //DifficultyLabel
        diffEasyCurrectTV.setText(testResult.getData().getDiffcultyLevelAnalysis().getEasy().getCorrect());
        diffMediumCurrectTV.setText(testResult.getData().getDiffcultyLevelAnalysis().getMedium().getCorrect());
        diffHardCurrectTV.setText(testResult.getData().getDiffcultyLevelAnalysis().getHard().getCorrect());

        diffEasyWrongTV.setText(testResult.getData().getDiffcultyLevelAnalysis().getEasy().getWrong());
        diffMediumWrongTV.setText(testResult.getData().getDiffcultyLevelAnalysis().getMedium().getWrong());
        diffHardWrongTV.setText(testResult.getData().getDiffcultyLevelAnalysis().getHard().getWrong());

        diffEasySkipTV.setText(testResult.getData().getDiffcultyLevelAnalysis().getEasy().getSkip());
        diffMediumSkipTV.setText(testResult.getData().getDiffcultyLevelAnalysis().getMedium().getSkip());
        diffHardSkipTV.setText(testResult.getData().getDiffcultyLevelAnalysis().getHard().getSkip());

        //Time Analysis
        timeTakenMarkedTV.setText(testResult.getData().getTimeAnalysis().getChangeOption());
        timeTakenSkipTV.setText(testResult.getData().getTimeAnalysis().getChangeQuestion());
        totalTimeTV.setText(testResult.getData().getTimeAnalysis().getTotalTime());

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "DNA");
                share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.dnamedical");
                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });

        totalQuestion = findViewById(R.id.total_question);
//        skipped = findViewById(R.id.skipped);
//        wrong = findViewById(R.id.wrong);
//        correct = findViewById(R.id.correct);

        showRankResult();

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewSheet();
            }
        });

/*

        if (getIntent().hasExtra("tquestion")) {
            Intent intent = getIntent();
            average = intent.getStringExtra("average");
            String userid = intent.getStringExtra("user_Id");
            tquestion = intent.getStringExtra("tquestion");
            canswer = intent.getStringExtra("canswer");
            wanswer = intent.getStringExtra("wanswer");
            sanswer = intent.getStringExtra("sanswer");
            String testName = intent.getStringExtra("testName");
            percentValue.setText("  " + average);
            circleSeekBar.setProgressDisplay(Integer.parseInt(canswer));
            total.setText(tquestion);
            correct.setText(canswer);
            wrong.setText(wanswer);
            skipped.setText(sanswer);

        }

*/

        //dateTv.setText(Utils.tripDateFormat(System.currentTimeMillis()));

        //testNameTv.setText("" + testName);

    }

    private void ReviewSheet() {
        String test_id = getIntent().getStringExtra("Test_Id");
        Intent intent = new Intent(ResultActivity.this, ReviewQuestionList.class);
        intent.putExtra("id", test_id);
        startActivity(intent);
    }

    private void showRankResult() {
        String user_id;
        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            user_id = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            user_id = DnaPrefs.getString(getApplicationContext(), "Login_Id");
        }

        String testid = getIntent().getStringExtra("Test_Id");


        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody testId = RequestBody.create(MediaType.parse("text/plain"), testid);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.resultList(userId, testId, new Callback<ResultList>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                    Utils.dismissProgressDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            userResults = response.body().getUserResult();
                            totalUser.setText(userResults.get(0).getTotalUsersTest());
                            totalQuestion.setText("Maximum marks : " + userResults.get(0).getUserTotalScore());
                            correct.setMax(Integer.parseInt(userResults.get(0).getTotalQuestion()));
                            skipped.setMax(Integer.parseInt(userResults.get(0).getTotalQuestion()));
                            wrong.setMax(Integer.parseInt(userResults.get(0).getTotalQuestion()));
                            skipped.setProgress(Float.parseFloat(userResults.get(0).getSkipQuestion()));
                            userNumber.setText("Total Score : " + userResults.get(0).getUserScore());

                            correct.setEnabled(false);
                            skipped.setEnabled(false);
                            wrong.setEnabled(false);


                            if (!(userResults.get(0).getCurrectQuestion() != null)
                                    && TextUtils.isEmpty(userResults.get(0).getCurrectQuestion())) {
                                correct.setProgress(0);
                                correctTXT.setText(0 + "");

                            } else {
                                correct.setProgress(Integer.parseInt(userResults.get(0).getCurrectQuestion()));
                                correctTXT.setText(userResults.get(0).getCurrectQuestion());
                            }

                            if (!(userResults.get(0).getWrongQuestion() != null)
                                    && TextUtils.isEmpty(userResults.get(0).getWrongQuestion())) {
                                wrong.setProgress(0);
                                wrongTXT.setText("" + 0);

                            } else {
                                wrong.setProgress(Integer.parseInt(userResults.get(0).getWrongQuestion()));
                                wrongTXT.setText("" + userResults.get(0).getWrongQuestion());

                            }

                            if (!(userResults.get(0).getSkipQuestion() != null)
                                    && TextUtils.isEmpty(userResults.get(0).getSkipQuestion())) {
                                skipped.setProgress(0);
                                skippedTXT.setText("" + 0);


                            } else {
                                skipped.setProgress(Integer.parseInt(userResults.get(0).getSkipQuestion()));
                                skippedTXT.setText("" + userResults.get(0).getSkipQuestion());

                            }
                            totalUser.setText("Out of " + userResults.get(0).getTotalUsersTest());
                            percentValue.setText("Percentile : " + userResults.get(0).getPercentile());
                            userRank.setText("RANK\n" + userResults.get(0).getUserRank());

                            allReults = response.body().getAllReult();
                            resultAdapter = new ResultAdapter(allReults);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(resultAdapter);

                        } else {
                            Toast.makeText(ResultActivity.this, "Invalid Status", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ResultActivity.this, "Response is Invalid", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResultList> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(ResultActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }


}
