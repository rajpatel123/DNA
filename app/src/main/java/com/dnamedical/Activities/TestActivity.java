package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Models.test.testp.QustionDetails;
import com.dnamedical.Models.test.testresult.TestResult;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.fragment.QuestionFragment;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends FragmentActivity implements PopupMenu.OnMenuItemClickListener {
    MyAdapter mAdapter;
    ViewPager mPager;
    TextView quesionCounter;
    TextView timer;
    public long tempTime;
    public Map<String, String> correctAnswerList = new HashMap<>();
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;

    Handler handler;

    public int Seconds, Minutes, MilliSeconds;
    CountDownTimer countDownTimer;
    private QustionDetails qustionDetails;

    boolean isSwitching;


    public CheckBox guessCheck;
    private ImageView guessImage;
    private Button button, menuButton;
    private Button skip;
    long testCompleteTime = 0;

    String user_id;
    String test_id;
    public String question_id;
    public String answer;
    public String isGuess;

    public Button nextBtn;
    static int currentPosition;
    boolean timeUp;
    private String testName;
    long testDuration = 0;
    Button item_star;
    private RelativeLayout relative;
    private ImageButton iv_popupMenu;
    private long timeSpend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);
        guessImage = findViewById(R.id.image_guess);
        guessCheck = findViewById(R.id.guessCheck);
        relative = findViewById(R.id.relative);
        handler = new Handler();

        guessImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GuessOpen();
            }
        });

        user_id = DnaPrefs.getString(getApplicationContext(), "Login_Id");

        quesionCounter = findViewById(R.id.counter);
        timer = findViewById(R.id.timer);
        String duration = getIntent().getStringExtra("duration");
        testName = getIntent().getStringExtra("testName");
        test_id = getIntent().getStringExtra("id");
        testDuration = 15 * 60 * 1000;
        resettimer();
        startTimer();
        nextBtn = findViewById(R.id.skip_button);
        nextBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                timeSpend = System.currentTimeMillis() - tempTime;
                if (guessCheck.isChecked()) {
                    isGuess = "true";
                } else {
                    isGuess = "false";
                }

                pauseTimer();

                if ((currentPosition + 1) == qustionDetails.getData().getQuestionList().size()) {
                    submitTest();
                    Toast.makeText(TestActivity.this, "Time for Switch Question ==", Toast.LENGTH_LONG).show();
                    pauseTimer();
                } else {
                    if (!nextBtn.getText().toString().trim().equalsIgnoreCase("SKIP")) {
                        submitQuestionAnswer();
                    }

                }

                submitTimeLogTest("switch_question", "" + Seconds);
                Toast.makeText(TestActivity.this, "Time for Switch Question ==" + Seconds, Toast.LENGTH_LONG).show();


                updateQuestionsFragment();
            }
        });


        countDownTimer = new CountDownTimer(testDuration, 1000) {

            public void onTick(long millis) {
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                timer.setText(hms);
                testCompleteTime = TimeUnit.MILLISECONDS.toMinutes(testDuration - millis);
            }

            public void onFinish() {
                timer.setText("Time up!");
                timeUp = true;
                submitAlertDiolog();
            }

        };
        countDownTimer.start();
        iv_popupMenu = findViewById(R.id.context_Menu);

        iv_popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });
    }


    private void submitTest() {
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), test_id);
        RequestBody isSubmit = RequestBody.create(MediaType.parse("text/plain"), "0");
        Utils.showProgressDialog(TestActivity.this);
        RestClient.submitTest(userId, testID, isSubmit, new Callback<TestResult>() {
            @Override
            public void onResponse(Call<TestResult> call, Response<TestResult> response) {
                TestResult testResult = response.body();
                Utils.dismissProgressDialog();
                if (testResult != null) {
                    Intent intent = new Intent(TestActivity.this, ResultActivity.class);
                    intent.putExtra(Constants.RESULT, testResult);
                    startActivity(intent);
                    Log.d("DataSuccess", "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<TestResult> call, Throwable t) {
                Utils.dismissProgressDialog();
                Log.d("DataFail", "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);
            }
        });

    }

    public void submitTimeLogTest(String type, String time) {
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody timeSpendBody = RequestBody.create(MediaType.parse("text/plain"), "" + time);
        RequestBody testEvent = RequestBody.create(MediaType.parse("text/plain"), "test");
        RequestBody subEvent = RequestBody.create(MediaType.parse("text/plain"), type);
        RequestBody product_id = RequestBody.create(MediaType.parse("text/plain"), test_id);
        RestClient.submit_timeLog(userId, timeSpendBody, testEvent, subEvent, product_id, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Time Log -==>  " + type, "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("DataFail", "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);

            }
        });

    }

    private void submitQuestionAnswer() {
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), test_id);
        RequestBody qID = RequestBody.create(MediaType.parse("text/plain"), question_id);
        RequestBody answerID = RequestBody.create(MediaType.parse("text/plain"), answer);
        RequestBody guesStatus = RequestBody.create(MediaType.parse("text/plain"), isGuess);
        RestClient.submitQuestionTestAnswer(userId, testID, qID, answerID, guesStatus, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("DataSuccess", "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("DataFail", "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);

            }
        });

    }

    public void submitAnswer() {
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), test_id);
        RequestBody qID = RequestBody.create(MediaType.parse("text/plain"), question_id);
        RequestBody answerID = RequestBody.create(MediaType.parse("text/plain"), answer);
        RestClient.submitTestAnswer(userId, testID, qID, answerID, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("DataSuccess", "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("DataFail", "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);

            }
        });

    }

    private void updateQuestionsFragment() {
        quesionCounter.setText((currentPosition + 1) + " of " + qustionDetails.getData().getQuestionList().size());
        mPager.setCurrentItem(currentPosition + 1);
        if ((currentPosition + 1) == qustionDetails.getData().getQuestionList().size()) {
            nextBtn.setText("SUBMIT");
        } else {
            nextBtn.setText("SKIP");
        }

        resettimer();
        startTimer();
    }


    private void GuessOpen() {

        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.guess_alert_dialog, null);
        dialogBuilder.setView(dialogView);

        final android.app.AlertDialog dialog = dialogBuilder.create();
        Button btn_yes = dialogView.findViewById(R.id.button_guess);
        btn_yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(TestActivity.this);
        popup.inflate(R.menu.pop_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reviewTest:
                Intent intent = new Intent(TestActivity.this, AttemptingQuestionActivity.class);
                startActivity(intent);
                return true;
            case R.id.submitTest:
                submitAlertDiolog();
                return true;
            case R.id.discardTest:
                discardAlertDialog();
                return true;
            default:
                return false;
        }
    }

    private void submitAlertDiolog() {
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.submit_alert_diolog, null);
        dialogBuilder.setView(dialogView);

        final android.app.AlertDialog dialog = dialogBuilder.create();
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

                if (countDownTimer != null)
                    countDownTimer.cancel();
                submitTest();
                dialog.dismiss();

            }
        });

        dialog.show();


    }


    private void discardAlertDialog() {

        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.discard_alert_diolog, null);
        dialogBuilder.setView(dialogView);

        final android.app.AlertDialog dialog = dialogBuilder.create();
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
                dialog.dismiss();
                finish();
                Toast.makeText(TestActivity.this, "Open", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTest();
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        QustionDetails qustionDetails = null;
        TextView quesionCounter;

        public MyAdapter(FragmentManager fragmentManager, QustionDetails qustionDetails, TextView quesionCounter) {
            super(fragmentManager);
            this.qustionDetails = qustionDetails;
            this.quesionCounter = quesionCounter;
        }


        @Override
        public int getCount() {
            if (qustionDetails.getData() != null
                    && qustionDetails.getData().getQuestionList() != null
                    && qustionDetails.getData().getQuestionList().size() > 0)
                return qustionDetails.getData().getQuestionList().size();
            return 0;
        }

        @Override
        public Fragment getItem(int position) {
            quesionCounter.setText((position) + " of " + qustionDetails.getData().getQuestionList().size());
            return QuestionFragment.init(qustionDetails.getData().getQuestionList().get(position), position, qustionDetails.getData().getQuestionList().size());
        }
    }

    private void getTest() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.getQuestion(user_id, getIntent().getStringExtra("id"), new Callback<QustionDetails>() {
                @Override
                public void onResponse(Call<QustionDetails> call, Response<QustionDetails> response) {
                    Utils.dismissProgressDialog();

                    if (response.code() == 200) {
                        qustionDetails = response.body();
                        if (qustionDetails.getData() != null && qustionDetails.getData().getQuestionList().size() > 0) {
                            mAdapter = new MyAdapter(getSupportFragmentManager(), qustionDetails, quesionCounter);
                            mPager = findViewById(R.id.pager);
                            mPager.addOnPageChangeListener(pageChangeListener);
                            mPager.setAdapter(mAdapter);
                            relative.setVisibility(View.VISIBLE);
                        } else {
                            relative.setVisibility(View.GONE);
                            Toast.makeText(TestActivity.this, "No question here", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }
                }

                @Override
                public void onFailure(Call<QustionDetails> call, Throwable t) {
                    Utils.dismissProgressDialog();
                }
            });
        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();
        }

    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int newPosition) {
            currentPosition = newPosition;
            quesionCounter.setText((newPosition + 1) + " of " + qustionDetails.getData().getQuestionList().size());
        }

        @Override
        public void onPageScrolled(int newPosition, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };

//    private void submitTest() {
//        if (Utils.isInternetConnected(this)) {
//            Utils.showProgressDialog(this);
//            if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
//                user_id = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
//            } else {
//                user_id = DnaPrefs.getString(getApplicationContext(), "Login_Id");
//            }
//
//            String test_id = getIntent().getStringExtra("id");
//            String tquestion = "" + qustionDetails.getDetail().size();
//            String canswer = "" + correctAnswerList.keySet().size();
//            String wanswer = "" + wrongAnswerList.keySet().size();
//            int sanswer =0;
//
//            StringBuilder builder = new StringBuilder();
//            for (Detail detail : qustionDetails.getDetail()) {
//                builder.append(detail.getQid() + ",");
//
//            }
//
//            if (!TextUtils.isEmpty(builder))
//                    ttQuestion = builder.substring(0, builder.toString().length() - 1).toString();
//
//            StringBuilder ccAnswer = new StringBuilder();
//
//            for (String ss : correctAnswerList.keySet()) {
//                ccAnswer.append(ss + ",");
//            }
//            if (!TextUtils.isEmpty(ccAnswer))
//                ccAnswerIds = ccAnswer.substring(0, ccAnswer.toString().length() - 1).toString();
//
//            StringBuilder wwanswer = new StringBuilder();
//            for (String ss : wrongAnswerList.keySet()) {
//                wwanswer.append(ss + ":" + wrongAnswerList.get(ss) + ",");
//            }
//
//            if (!TextUtils.isEmpty(wwanswer))
//                wwanswerIds = wwanswer.substring(0, wwanswer.toString().length() - 1).toString();
//
//
//            StringBuilder skiped = new StringBuilder();
//            for (Detail ss : qustionDetails.getDetail()) {
//               if (!correctAnswerList.containsKey(ss.getQid())&& !wrongAnswerList.containsKey(ss.getQid())){
//                   skiped.append(ss.getQid() + ",");
//                   sanswer++;
//
//               }
//            }
//            if (!TextUtils.isEmpty(skiped))
//                ssanswer = skiped.substring(0, skiped.toString().length() - 1).toString();
//
//
//            Log.d("TEstData", "  Duration  "+testCompleteTime +" userid->" + user_id + " testid->" + test_id + " tquestion->"
//                    + tquestion + " ttQuestion" + ttQuestion +
//                    " canswer->" + canswer + " ccAnswerIds->" + ccAnswerIds + " wanswer->" + wanswer + " wwanswerIds->" + wwanswerIds + " ssanswer->" + ssanswer   );
//
//            RestClient.submitTest(user_id, test_id, tquestion, ttQuestion, canswer, ccAnswerIds, wanswer, wwanswerIds, ""+sanswer, ssanswer,""+testCompleteTime, new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    Utils.dismissProgressDialog();
//
//                    if (response.code() == 200) {
//                        ResponseBody responseBody = response.body();
//                        try {
//                            String raw = responseBody.string();
//                            try {
//                                JSONObject jsonObject = new JSONObject(raw);
//                                Toast.makeText(TestActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(TestActivity.this, ResultActivity.class);
//                                intent.putExtra("average", jsonObject.getString("average"));
//                                intent.putExtra("User_Id", user_id);
//                                intent.putExtra("Test_Id", test_id);
//                                intent.putExtra("tquestion", tquestion);
//                                intent.putExtra("canswer", canswer);
//                                intent.putExtra("wanswer", wanswer);
//                                intent.putExtra("testName", testName);
//                                startActivity(intent);
//                                finish();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Utils.dismissProgressDialog();
//                }
//            });
//        } else {
//            Utils.dismissProgressDialog();
//
//            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Submit");
        builder.setMessage("Are you sure you want to submit test?");
        String positiveText = "OK";
        builder.setPositiveButton(positiveText, (dialog, which) -> {
            dialog.dismiss();
            if (countDownTimer != null)
                countDownTimer.cancel();
            //submitTest();
        });
        String negativeText = "CANCEL";
        builder.setNegativeButton(negativeText, (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void startTimer() {

        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);

    }


    public void resettimer() {
        MillisecondTime = 0L;
        StartTime = 0L;
        TimeBuff = 0L;
        UpdateTime = 0L;
        Seconds = 0;
        Minutes = 0;
        MilliSeconds = 0;
        Seconds = 0;
    }


    public void pauseTimer() {
        TimeBuff += MillisecondTime;
        handler.removeCallbacks(runnable);


    }


    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);


            handler.postDelayed(this, 0);
        }

    };


}


