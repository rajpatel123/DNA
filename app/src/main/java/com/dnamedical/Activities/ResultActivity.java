package com.dnamedical.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.dnamedical.Adapters.ResultAdapter;
import com.dnamedical.Adapters.result;
import com.dnamedical.Models.ResultData.AllReult;
import com.dnamedical.Models.ResultData.ResultList;
import com.dnamedical.Models.ResultData.UserResult;
import com.dnamedical.Models.test.testresult.ScoreAnalysi;
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
    private Button reviewButton, shareButton;
    String user_id;
    TestResult testResult;
    TextView rankTV, dateTV, startTimeTV, endTimeTv, yourScoreTV, totalMarksTv,
            percentageTV, percentaileTV;
    TextView guessCtoWtv, guessWtoCtv, guessWtoWtv, guessTotalSwitchedtv;
    TextView diffEasyCurrectTV, diffMediumCurrectTV, diffHardCurrectTV,
            diffEasyWrongTV, diffMediumWrongTV, diffHardWrongTV, diffEasySkipTV, diffMediumSkipTV, diffHardSkipTV;
    TextView timeTakenMarkedTV, timeTakenSkipTV, totalTimeTV;
    private String test_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        if (getIntent().hasExtra("Test_Id")) {
            test_id = getIntent().getStringExtra("Test_Id");

        }
        rankTV = findViewById(R.id.resultRank);
        dateTV = findViewById(R.id.resultDate);
        startTimeTV = findViewById(R.id.resultStratTime);
        endTimeTv = findViewById(R.id.resultEndTime);
        yourScoreTV = findViewById(R.id.yourScore);
        totalMarksTv = findViewById(R.id.totalMarks);
        percentageTV = findViewById(R.id.percentage);
        percentaileTV = findViewById(R.id.percentaile);
        shareButton = findViewById(R.id.shareBtn);

        guessCtoWtv = findViewById(R.id.cToW);
        guessWtoCtv = findViewById(R.id.wToc);
        guessWtoWtv = findViewById(R.id.wTow);
        guessTotalSwitchedtv = findViewById(R.id.totalSwitched);

        diffEasyCurrectTV = findViewById(R.id.diffEasyCurrect);
        diffMediumCurrectTV = findViewById(R.id.diffMediumCurrect);
        diffHardCurrectTV = findViewById(R.id.diffHardCurrect);

        diffEasyWrongTV = findViewById(R.id.diffEasyWrong);
        diffMediumWrongTV = findViewById(R.id.diffMediumWrong);
        diffHardWrongTV = findViewById(R.id.diffHardWrong);

        diffEasySkipTV = findViewById(R.id.diffEasySkip);
        diffMediumSkipTV = findViewById(R.id.diffMediumSkip);
        diffHardSkipTV = findViewById(R.id.diffHardSkip);
        timeTakenMarkedTV = findViewById(R.id.timeTakenMarkedQus);
        timeTakenSkipTV = findViewById(R.id.timeTakenSkipQus);
        totalTimeTV = findViewById(R.id.total_Time_Qus);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.RESULT)) {
            testResult = intent.getParcelableExtra(Constants.RESULT);
            updateResult(testResult);
        } else {
            submitTest();
        }
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
//                Intent share = new Intent(android.content.Intent.ACTION_SEND);
//                share.setType("text/plain");
//                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//                // Add data to the intent, the receiving app will decide
//                // what to do with it.
//                share.putExtra(Intent.EXTRA_SUBJECT, "DNA");
//                share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.dnamedical");
//                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });

        totalQuestion = findViewById(R.id.total_question);
//        skipped = findViewById(R.id.skipped);
//        wrong = findViewById(R.id.wrong);
//        correct = findViewById(R.id.correct);

        //  showRankResult();

//        reviewButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // ReviewSheet();
//            }
//        });
    }

    private void updateResult(TestResult testResult) {

        init(testResult.getData().getScoreAnalysis());
        rankTV.setText("" + testResult.getData().getRank());
        startTimeTV.setText("" + Utils.startTimeFormat((testResult.getData().getStartTime() * 1000)));
        endTimeTv.setText("" + Utils.startTimeFormat((testResult.getData().getEndTime() * 1000)));
        yourScoreTV.setText("" + testResult.getData().getYourScore());
        totalMarksTv.setText("" + testResult.getData().getTotalMarks());
        percentageTV.setText("" + testResult.getData().getPercenatge());
        percentaileTV.setText("" + testResult.getData().getPercentile());

        //Guess Analysis
        guessCtoWtv.setText("" + testResult.getData().getGuessAnalysis().getCorrectToWrong());
        guessWtoCtv.setText("" + testResult.getData().getGuessAnalysis().getWrongToCorrect());
        guessWtoWtv.setText("" + testResult.getData().getGuessAnalysis().getWrongToWrong());
        guessTotalSwitchedtv.setText("" + testResult.getData().getGuessAnalysis().getTotalSwitch());

        //DifficultyLabel
        diffEasyCurrectTV.setText("" + testResult.getData().getDiffcultyLevelAnalysis().getEasy().getCorrect());
        diffMediumCurrectTV.setText("" + testResult.getData().getDiffcultyLevelAnalysis().getMedium().getCorrect());
        diffHardCurrectTV.setText("" + testResult.getData().getDiffcultyLevelAnalysis().getHard().getCorrect());

        diffEasyWrongTV.setText("" + testResult.getData().getDiffcultyLevelAnalysis().getEasy().getWrong());
        diffMediumWrongTV.setText("" + testResult.getData().getDiffcultyLevelAnalysis().getMedium().getWrong());
        diffHardWrongTV.setText("" + testResult.getData().getDiffcultyLevelAnalysis().getHard().getWrong());

        diffEasySkipTV.setText("" + testResult.getData().getDiffcultyLevelAnalysis().getEasy().getSkip());
        diffMediumSkipTV.setText("" + testResult.getData().getDiffcultyLevelAnalysis().getMedium().getSkip());
        diffHardSkipTV.setText("" + testResult.getData().getDiffcultyLevelAnalysis().getHard().getSkip());

        //Time Analysis
        timeTakenMarkedTV.setText("" + testResult.getData().getTimeAnalysis().getChangeOption());
        timeTakenSkipTV.setText("" + testResult.getData().getTimeAnalysis().getChangeQuestion());
        totalTimeTV.setText("" + testResult.getData().getTimeAnalysis().getTotalTime());
    }


    private void submitTest() {
        user_id = DnaPrefs.getString(getApplicationContext(), "Login_Id");

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), test_id);
        RequestBody isSubmit = RequestBody.create(MediaType.parse("text/plain"), "0");
        Utils.showProgressDialog(ResultActivity.this);
        RestClient.submitTest(userId, testID, isSubmit, new Callback<TestResult>() {
            @Override
            public void onResponse(Call<TestResult> call, Response<TestResult> response) {
                TestResult testResult = response.body();
                Utils.dismissProgressDialog();
                if (testResult != null) {
                    updateResult(testResult);
                }

            }

            @Override
            public void onFailure(Call<TestResult> call, Throwable t) {
                Utils.dismissProgressDialog();
                Log.d("DataFail", "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->");
            }
        });

    }

    public void init(List<ScoreAnalysi> scoreAnalysi) {
        TableLayout ll = findViewById(R.id.subjectTable);
        TableRow rowHead = new TableRow(this);
        TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        rowHead.setLayoutParams(lp1);
        rowHead.setBackgroundResource(R.drawable.border);


        TextView subject = new TextView(this);
        TextView rightAnswer = new TextView(this);
        TextView wrongAnswer = new TextView(this);
        TextView skipped = new TextView(this);
        TextView score = new TextView(this);
        subject.setTypeface(Typeface.DEFAULT_BOLD);
        rightAnswer.setTypeface(Typeface.DEFAULT_BOLD);
        wrongAnswer.setTypeface(Typeface.DEFAULT_BOLD);
        skipped.setTypeface(Typeface.DEFAULT_BOLD);
        score.setTypeface(Typeface.DEFAULT_BOLD);

        setBorder(subject);
        setBorder(rightAnswer);
        setBorder(wrongAnswer);
        setBorder(skipped);
        setBorder(score);

        subject.setPadding(10, 5, 5, 5);
        subject.setGravity(Gravity.CENTER);
        rightAnswer.setPadding(10, 5, 5, 5);
        rightAnswer.setGravity(Gravity.CENTER);
        skipped.setPadding(10, 5, 5, 5);
        skipped.setGravity(Gravity.CENTER);
        rightAnswer.setPadding(10, 5, 5, 5);
        rightAnswer.setGravity(Gravity.CENTER);
        score.setPadding(10, 5, 5, 5);
        score.setGravity(Gravity.CENTER);


        subject.setText("Subject");
        rightAnswer.setText("(++)");
        rightAnswer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);

        wrongAnswer.setText("(--)");
        wrongAnswer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close, 0, 0, 0);

        skipped.setText("Not Attempted");
        score.setText("Score");
        rowHead.addView(subject);
        rowHead.addView(rightAnswer);
        rowHead.addView(wrongAnswer);
        rowHead.addView(skipped);
        rowHead.addView(score);
        ll.addView(rowHead, 0);

        for (int i = 0; i < scoreAnalysi.size(); i++) {

            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            row.setBackgroundResource(R.drawable.border);

            TextView subjectTv = new TextView(this);
            TextView rightAnswerTv = new TextView(this);
            TextView wrongAnswerTv = new TextView(this);
            TextView skippedTv = new TextView(this);
            TextView scoreTv = new TextView(this);
            subjectTv.setGravity(Gravity.CENTER);
            rightAnswerTv.setGravity(Gravity.CENTER);
            wrongAnswerTv.setGravity(Gravity.CENTER);
            skippedTv.setGravity(Gravity.CENTER);
            scoreTv.setGravity(Gravity.CENTER);


            setBorder(subjectTv);
            setBorder(rightAnswerTv);
            setBorder(wrongAnswerTv);
            setBorder(skippedTv);
            setBorder(scoreTv);


            subjectTv.setPadding(15, 5, 15, 5);
            rightAnswerTv.setPadding(10, 5, 5, 5);
            skippedTv.setPadding(10, 5, 5, 5);
            rightAnswerTv.setPadding(10, 5, 5, 5);
            scoreTv.setPadding(10, 5, 5, 5);


            subjectTv.setText("" + scoreAnalysi.get(i).getCategoryName());
            rightAnswerTv.setText("" + scoreAnalysi.get(i).getCorrect());
            wrongAnswerTv.setText("" + scoreAnalysi.get(i).getWrong());
            skippedTv.setText("" + scoreAnalysi.get(i).getSkip());
            scoreTv.setText("" + scoreAnalysi.get(i).getScore());
            row.addView(subjectTv);
            row.addView(rightAnswerTv);
            row.addView(wrongAnswerTv);
            row.addView(skippedTv);
            row.addView(scoreTv);
            ll.addView(row, i + 1);
        }
    }

    private void setBorder(TextView tv) {
        ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setColor(Color.BLACK);

        tv.setBackground(border);
    }


}
