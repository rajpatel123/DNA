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
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
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


    TextView dateTv, percentValue, testNameTv, totalUser,totalQuestion,userRank,userNumber;
    CircularSeekBar correct,wrong,skipped;
    TextView correctTXT,wrongTXT,skippedTXT;

    private List<UserResult> userResults;
    private List<ResultList> resultLists;
    private List<AllReult> allReults;
    private RecyclerView recyclerView;
    private ResultAdapter resultAdapter;
    private Button reviewButton, shareButton;
    private CircleSeekBar circleSeekBar;
    String user_id;
    String tquestion, average, canswer, wanswer, sanswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        userNumber=findViewById(R.id.user_number);
        // dateTv = findViewById(R.id.date);
        userRank=findViewById(R.id.user_rank);
        percentValue = findViewById(R.id.percentageValue);
        totalUser=findViewById(R.id.total_user);
        //  testNameTv = findViewById(R.id.testName);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        reviewButton = findViewById(R.id.review);
        correct = findViewById(R.id.correct);
        wrong = findViewById(R.id.wrong);
        skipped = findViewById(R.id.skipped);

        correctTXT = findViewById(R.id.correctText);
        wrongTXT = findViewById(R.id.wrongText);
        skippedTXT = findViewById(R.id.skippedText);

        shareButton = findViewById(R.id.btn_share);
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

        totalQuestion= findViewById(R.id.total_question);
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
                            totalQuestion.setText("Maximum marks : "+userResults.get(0).getUserTotalScore());
                            correct.setMax(Integer.parseInt(userResults.get(0).getTotalQuestion()));
                            skipped.setMax(Integer.parseInt(userResults.get(0).getTotalQuestion()));
                            wrong.setMax(Integer.parseInt(userResults.get(0).getTotalQuestion()));
                            skipped.setProgress(Float.parseFloat(userResults.get(0).getSkipQuestion()));
                            userNumber.setText("Total Score : "+userResults.get(0).getUserScore());



                            correct.setEnabled(false);
                            skipped.setEnabled(false);
                            wrong.setEnabled(false);


                            if (!(userResults.get(0).getCurrectQuestion() != null)
                                    && TextUtils.isEmpty(userResults.get(0).getCurrectQuestion())) {
                                correct.setProgress(0);
                                correctTXT.setText(0+"");

                            } else {
                                correct.setProgress(Integer.parseInt(userResults.get(0).getCurrectQuestion()));
                                correctTXT.setText(userResults.get(0).getCurrectQuestion());
                            }

                            if (!(userResults.get(0).getWrongQuestion() != null)
                                    && TextUtils.isEmpty(userResults.get(0).getWrongQuestion())) {
                                wrong.setProgress(0);
                                wrongTXT.setText(""+0);

                            } else {
                                wrong.setProgress(Integer.parseInt(userResults.get(0).getWrongQuestion()));
                                wrongTXT.setText(""+userResults.get(0).getWrongQuestion());

                            }

                            if (!(userResults.get(0).getSkipQuestion() != null)
                                    && TextUtils.isEmpty(userResults.get(0).getSkipQuestion())) {
                                skipped.setProgress(0);
                                skippedTXT.setText(""+0);


                            } else {
                                skipped.setProgress(Integer.parseInt(userResults.get(0).getSkipQuestion()));
                                skippedTXT.setText(""+userResults.get(0).getSkipQuestion());

                            }
                            totalUser.setText("Out of "+userResults.get(0).getTotalUsersTest());
                            percentValue.setText("Percentile : "+userResults.get(0).getPercentile());
                            userRank.setText("RANK\n"+userResults.get(0).getUserRank());

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
