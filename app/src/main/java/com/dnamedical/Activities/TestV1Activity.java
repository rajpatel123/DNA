package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Adapters.AnswerListAdapter;
import com.dnamedical.Models.test.testp.Question;
import com.dnamedical.Models.test.testp.QustionDetails;
import com.dnamedical.Models.test.testresult.TestResult;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestV1Activity extends FragmentActivity implements PopupMenu.OnMenuItemClickListener, AnswerListAdapter.onQuesttionClick {
    TextView quesionCounter;
    TextView timer;
    RelativeLayout questionpannel, answerSheet;
    Button closeSheet;
    public long tempTime;
    public Map<String, String> correctAnswerList = new HashMap<>();
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    Handler handler;
    LayoutInflater inflater;
    public int Seconds, Minutes, MilliSeconds;
    CountDownTimer countDownTimer;
    private QustionDetails qustionDetails;

    boolean isSwitching;


    public CheckBox guessCheck;
    private ImageView guessImage;
    private Button button, menuButton;
    private Button skip;
    private Button submit;
    public ImageView star;
    long testCompleteTime = 0;

    String user_id;
    String test_id;
    public String question_id;
    public String answer;
    public String isGuess;
    ProgressBar progressBar;
    public Button nextBtn, prevBtn;
    static int currentPosition;
    boolean timeUp;
    private String testName;
    long testDuration = 0;
    Button item_star;
    private TextView questionTxt;
    LinearLayout answerList;
    ImageView imageQuestion;
    CardView cardView1, cardView2, cardView3, cardView4;

    public boolean isBookmarkedRemoved;
    private RelativeLayout relative;
    private ImageButton iv_popupMenu;
    private long timeSpend;
    private RecyclerView answersheetRecyclerView;
    private int questionIndex = 0;
    private TextView answer1, answer2, answer3, answer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_v1);
        guessImage = findViewById(R.id.image_guess);
        guessCheck = findViewById(R.id.guessCheck);
        relative = findViewById(R.id.relative);
        submit = findViewById(R.id.submit);
        star = findViewById(R.id.star);
        answerList = findViewById(R.id.answerList);
        questionTxt = findViewById(R.id.questionTxt);
        imageQuestion = findViewById(R.id.question_image);
        progressBar = findViewById(R.id.progressBar);
        inflater = LayoutInflater.from(this);
        answersheetRecyclerView = findViewById(R.id.answersheetRecycler);
        handler = new Handler();

        guessImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GuessOpen();
            }
        });
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAlertDiolog();
            }
        });

        star.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(test_id)
                        && !TextUtils.isEmpty(question_id)) {
                    RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
                    RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), test_id);
                    RequestBody q_id = RequestBody.create(MediaType.parse("text/plain"), question_id);
                    RequestBody remove_bookmark = null;
                    if (qustionDetails.getData().getQuestionList().get(currentPosition).isBookMarked()) {
                        remove_bookmark = RequestBody.create(MediaType.parse("text/plain"), "1");
                        isBookmarkedRemoved = true;
                    } else {
                        isBookmarkedRemoved = false;
                        remove_bookmark = RequestBody.create(MediaType.parse("text/plain"), "0");
                    }

                    Utils.showProgressDialog(TestV1Activity.this);
                    RestClient.bookMarkQuestion(userId, testID, q_id, remove_bookmark, new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Utils.dismissProgressDialog();
                            if (response != null && response.code() == 200) {
                                if (isBookmarkedRemoved) {
                                    // DrawableCompat.setTint(star.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                                    qustionDetails.getData().getQuestionList().get(currentPosition).setBookMarked(false);

                                } else {
                                    //DrawableCompat.setTint(star.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                                    qustionDetails.getData().getQuestionList().get(currentPosition).setBookMarked(true);

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Utils.dismissProgressDialog();
                        }
                    });
                }

            }
        });

        user_id = DnaPrefs.getString(TestV1Activity.this, Constants.LOGIN_ID);

        quesionCounter = findViewById(R.id.counter);
        questionpannel = findViewById(R.id.questionpannel);
        answerSheet = findViewById(R.id.answerSheet);
        closeSheet = findViewById(R.id.closeSheet);
        quesionCounter = findViewById(R.id.counter);
        timer = findViewById(R.id.timer);
        String duration = getIntent().getStringExtra("duration");
        testName = getIntent().getStringExtra("testName");
        test_id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(duration) && TextUtils.isDigitsOnly(duration)) {
            testDuration = Integer.parseInt(duration) * 1000;
        }
        resettimer();
        startTimer();
        nextBtn = findViewById(R.id.skip_button);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionIndex < questionArrayList.size() - 1) {
                    questionIndex++;
                    onNextQuestion();
                } else {
                    pauseTimer();
                    submitTest();
                }
            }
        });

        prevBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (questionIndex > 0)
                    questionIndex--;

                onNextQuestion();
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

        questionpannel.setVisibility(View.VISIBLE);
        answerSheet.setVisibility(View.GONE);
        closeSheet.setVisibility(View.GONE);


        closeSheet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void onNextQuestion() {
        timeSpend = System.currentTimeMillis() - tempTime;
        if (guessCheck.isChecked()) {
            isGuess = "true";
            questionArrayList.get(questionIndex).setGues(true);
        } else {
            questionArrayList.get(questionIndex).setGues(false);
            isGuess = "false";
        }

        pauseTimer();
        submitTimeLogTest("switch_question", "" + Seconds);


        Log.d("Question Number", "" + questionIndex);


        if (nextBtn.getText().toString().trim().equalsIgnoreCase("NEXT")) {
            submitQuestionAnswer();
        } else {
            updateQuestionsFragment(questionIndex);
        }


        if (questionIndex == (questionArrayList.size() - 1)) {
            nextBtn.setText("SUBMIT");
        } else {
            nextBtn.setText("SKIP");
        }


    }


    private void submitTest() {
        if (!Utils.isInternetConnected(this)) {
            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), test_id);
        RequestBody isSubmit = RequestBody.create(MediaType.parse("text/plain"), "1");
        Utils.showProgressDialog(TestV1Activity.this);
        RestClient.submitTest(userId, testID, isSubmit, new Callback<TestResult>() {
            @Override
            public void onResponse(Call<TestResult> call, Response<TestResult> response) {
                TestResult testResult = response.body();
                Utils.dismissProgressDialog();
                if (testResult != null) {
                    Intent intent = new Intent(TestV1Activity.this, ResultActivity.class);
                    intent.putExtra(Constants.RESULT, testResult);
                    intent.putExtra("testid", test_id);
                    startActivity(intent);
                    Log.d("SubmitTest", " Successuser_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<TestResult> call, Throwable t) {
                Utils.dismissProgressDialog();
                Log.d("SubmitTest", "Faileduser_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);
            }
        });

    }

    public void submitTimeLogTest(String type, String time) {
        if (!Utils.isInternetConnected(this)) {
            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody timeSpendBody = RequestBody.create(MediaType.parse("text/plain"), "" + time);
        RequestBody testEvent = RequestBody.create(MediaType.parse("text/plain"), "test");
        RequestBody subEvent = RequestBody.create(MediaType.parse("text/plain"), "" + type);
        RequestBody product_id = RequestBody.create(MediaType.parse("text/plain"), "" + test_id);
        RestClient.submit_timeLog(userId, timeSpendBody, testEvent, subEvent, product_id, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Submittime", time + " user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + "Time-->" + answer + " Guess-->" + isGuess + "  Time-->" + Seconds);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Submittime", type + " user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);

            }
        });

    }

    private void submitQuestionAnswer() {
        if (!Utils.isInternetConnected(this)) {
            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(user_id)
                && !TextUtils.isEmpty(test_id)
                && !TextUtils.isEmpty(question_id)
                && !TextUtils.isEmpty(answer)) {

            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
            RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), test_id);
            RequestBody qID = RequestBody.create(MediaType.parse("text/plain"), question_id);
            RequestBody answerID = RequestBody.create(MediaType.parse("text/plain"), answer);
            RequestBody guesStatus = RequestBody.create(MediaType.parse("text/plain"), isGuess);
            RequestBody edit = RequestBody.create(MediaType.parse("text/plain"), "1");
            RestClient.submitQuestionTestAnswer(userId, testID, qID, answerID, guesStatus, edit, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("submitQuestionAnswer", "Success " + "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess + "   Time-->" + Seconds);
                    updateQuestionsFragment(questionIndex);

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("submitQuestionAnswer", "Failed " + "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);

                }
            });
        }


    }

    public void submitAnswer() {
        if (!Utils.isInternetConnected(this)) {
            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(user_id)
                && !TextUtils.isEmpty(test_id)
                && !TextUtils.isEmpty(question_id)
                && !TextUtils.isEmpty(answer)) {
            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
            RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), test_id);
            RequestBody qID = RequestBody.create(MediaType.parse("text/plain"), question_id);
            RequestBody answerID = RequestBody.create(MediaType.parse("text/plain"), answer);
            RestClient.submitTestAnswer(userId, testID, qID, answerID, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("submitAnswer", "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess + "Time-->" + Seconds);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("submitAnswer", "user_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);

                }
            });
        }


    }

    private void updateQuestionsFragment(int questionIndex) {
        answerList.removeAllViews();
        Question question = questionArrayList.get(questionIndex);
        question_id = question.getId();
        guessCheck.setChecked(question.isGues());
        if (!TextUtils.isEmpty(question.getTitle_image())) {
           if (Utils.isInternetConnected(TestV1Activity.this)){
               imageQuestion.setVisibility(View.VISIBLE);
               Picasso.with(this).load(question.getTitle_image())
                       .into(imageQuestion, new com.squareup.picasso.Callback() {
                           @Override
                           public void onSuccess() {
                               if (progressBar != null) {
                                   progressBar.setVisibility(View.GONE);
                               }
                           }

                           @Override
                           public void onError() {
                               if (progressBar != null) {
                                   progressBar.setVisibility(View.GONE);
                               }
                               imageQuestion.setVisibility(View.GONE);
                               Toast.makeText(TestV1Activity.this, "Unable to load image", Toast.LENGTH_LONG).show();


                           }
                       });
           }else{

           }

        } else {
            imageQuestion.setVisibility(View.GONE);
        }
        questionTxt.setText("Q" + (questionIndex + 1) + ". " + question.getTitle());
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    View answerView = inflater.inflate(R.layout.item_answer, null, false);
                    answer1 = answerView.findViewById(R.id.answer);
                    cardView1 = answerView.findViewById(R.id.cardView);
                    answer1.setText(question.getOption1());
                    answerList.addView(answerView);

                    if (!TextUtils.isEmpty(question.getSelectedOption()) && question.getOption1().equalsIgnoreCase(question.getSelectedOption())) {
                        updateAnswer(cardView1, answer1);
                    }
                    answer1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            answer = "1";
                            updateAnswer(cardView1, answer1);

                            if (!TextUtils.isEmpty(question.getSelectedOption())) {
                                updateToServerAnswerSelection();
                            } else {
                                updateAnswer(cardView1, answer1);
                            }
                            question.setSelectedOption(question.getOption1());

                        }
                    });
                    break;
                case 1:
                    View answerView1 = inflater.inflate(R.layout.item_answer,
                            null, false);
                    answer2 = answerView1.findViewById(R.id.answer);
                    cardView2 = answerView1.findViewById(R.id.cardView);
                    answer2.setText(question.getOption2());
                    answerList.addView(answerView1);
                    if (!TextUtils.isEmpty(question.getSelectedOption()) && question.getOption2().equalsIgnoreCase(question.getSelectedOption())) {
                        updateAnswer(cardView2, answer2);
                    }
                    answer2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            answer = "2";
                            if (!TextUtils.isEmpty(question.getSelectedOption())) {
                                updateToServerAnswerSelection();
                            }
                            updateAnswer(cardView2, answer2);
                            question.setSelectedOption(question.getOption2());

                        }
                    });
                    break;
                case 2:
                    View answerView2 = inflater.inflate(R.layout.item_answer,
                            null, false);
                    answer3 = answerView2.findViewById(R.id.answer);
                    cardView3 = answerView2.findViewById(R.id.cardView);
                    answer3.setText(question.getOption3());

                    answerList.addView(answerView2);
                    if (!TextUtils.isEmpty(question.getSelectedOption()) && question.getOption3().equalsIgnoreCase(question.getSelectedOption())) {
                        updateAnswer(cardView3, answer3);
                    }
                    answer3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            answer = "3";
                            if (!TextUtils.isEmpty(question.getSelectedOption())) {
                                updateToServerAnswerSelection();
                            }
                            question.setSelectedOption(question.getOption3());
                            updateAnswer(cardView3, answer3);

                        }
                    });
                    break;
                case 3:
                    View answerView4 = inflater.inflate(R.layout.item_answer,
                            null, false);
                    answer4 = answerView4.findViewById(R.id.answer);
                    cardView4 = answerView4.findViewById(R.id.cardView);
                    answer4.setText(question.getOption4());
                    answerList.addView(answerView4);
                    if (!TextUtils.isEmpty(question.getSelectedOption()) && question.getOption4().equalsIgnoreCase(question.getSelectedOption())) {
                        updateAnswer(cardView4, answer4);
                    }
                    answer4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            answer = "4";
                            if (!TextUtils.isEmpty(question.getSelectedOption())) {
                                updateToServerAnswerSelection();
                            }
                            question.setSelectedOption(question.getOption4());
                            updateAnswer(cardView4, answer4);

                        }
                    });
                    break;
            }
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
        popup.setOnMenuItemClickListener(TestV1Activity.this);
        popup.inflate(R.menu.pop_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reviewTest:
                questionpannel.setVisibility(View.GONE);
                answerSheet.setVisibility(View.VISIBLE);
                closeSheet.setVisibility(View.VISIBLE);
                loadSheet();

                return true;
            case R.id.submitTest:
                submitAlertDiolog();
                return true;
            case R.id.closeSheet:
                questionpannel.setVisibility(View.VISIBLE);
                answerSheet.setVisibility(View.GONE);
                closeSheet.setVisibility(View.GONE);
                return true;
            case R.id.discardTest:
                discardAlertDialog();
                return true;
            default:
                return false;
        }
    }

    private void loadSheet() {

        AnswerListAdapter answerListAdapter = new AnswerListAdapter(this);
        answerListAdapter.setData(qustionDetails.getData().getQuestionList());
        answerListAdapter.setListener(this);
        answersheetRecyclerView.setAdapter(answerListAdapter);
        Log.d("Api Response :", "Got Success from Api");
        // noInternet.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        answersheetRecyclerView.setLayoutManager(layoutManager);
        answersheetRecyclerView.setVisibility(View.VISIBLE);


    }

    private void submitAlertDiolog() {
        int count = getUnAttemptedCount();
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.submit_alert_diolog, null);
        dialogBuilder.setView(dialogView);

        final android.app.AlertDialog dialog = dialogBuilder.create();
        Button btn_yes = dialogView.findViewById(R.id.btn_done);
        if (count > 0) {
            TextView unuttempted = dialogView.findViewById(R.id.unuttempted);
            unuttempted.setText("You have " + count + " unattempted questions");
            unuttempted.setVisibility(View.VISIBLE);
        }
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
                onBackPressed();
                dialog.dismiss();

            }
        });

        if (!isFinishing() && !dialog.isShowing())
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
                finish();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (questionArrayList.size() > 0) {
        } else {
            getTest();
        }
    }

    @Override
    public void onQuesClick(int currentPosition) {
        questionIndex = currentPosition;
        onNextQuestion();
        onBackPressed();
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
                            questionArrayList.addAll(qustionDetails.getData().getQuestionList());
                            relative.setVisibility(View.VISIBLE);

                            updateQuestionsFragment(questionIndex);
                        } else {
                            relative.setVisibility(View.GONE);
                            Toast.makeText(TestV1Activity.this, "No question here", Toast.LENGTH_LONG).show();
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

            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
        }

    }


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

    @Override
    public void onBackPressed() {
        if (answerSheet.getVisibility() == View.VISIBLE) {
            questionpannel.setVisibility(View.VISIBLE);
            answerSheet.setVisibility(View.GONE);
            closeSheet.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();

    }


    private int getUnAttemptedCount() {
        int count = 0;
        if (qustionDetails != null && qustionDetails.getData() != null
                && qustionDetails.getData().getQuestionList() != null && qustionDetails.getData().getQuestionList().size() > 0) {
            for (Question question : qustionDetails.getData().getQuestionList()) {
                if (!TextUtils.isEmpty(question.getSelectedOption())) {
                    count++;
                }
            }
        }

        if (count > 0 && count != qustionDetails.getData().getQuestionList().size()) {
            return qustionDetails.getData().getQuestionList().size() - count;
        }
        return count;
    }

    private void updateToServerAnswerSelection() {
        pauseTimer();
        submitTimeLogTest("selecting_option", "" + Seconds);
        //  Toast.makeText(activity, "Time for Select Answer ==  time" + activity.Seconds, Toast.LENGTH_LONG).show();

        submitAnswer();
        resettimer();
        startTimer();
    }


    private void updateAnswer(CardView cardView, TextView answer) {
        cardView1.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView2.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView3.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView4.setCardBackgroundColor(getResources().getColor(R.color.white));
        if (questionIndex == (questionArrayList.size() - 1)) {
            nextBtn.setText("SUBMIT");
        } else {
            nextBtn.setText("NEXT");
        }

        answer1.setTextColor(getResources().getColor(R.color.black));
        answer2.setTextColor(getResources().getColor(R.color.black));
        answer3.setTextColor(getResources().getColor(R.color.black));
        answer4.setTextColor(getResources().getColor(R.color.black));
        answer.setTextColor(getResources().getColor(R.color.white));

        cardView.setCardBackgroundColor(getResources().getColor(R.color.test_fragment_card_bacckground));
    }

}