package com.dnamedeg.Activities.custommodule;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dnamedeg.Activities.ResultActivity;
import com.dnamedeg.Adapters.AnswerListAdapter;
import com.dnamedeg.Models.test.RankResultRemarks;
import com.dnamedeg.Models.test.testp.Question;
import com.dnamedeg.Models.test.testp.QustionDetails;
import com.dnamedeg.Models.test.testresult.TestResult;
import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.DnaPrefs;
import com.dnamedeg.utils.PicassoImageGetter;
import com.dnamedeg.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomtestV1TestV1Activity extends FragmentActivity implements PopupMenu.OnMenuItemClickListener, AnswerListAdapter.onQuesttionClick {
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
    private QustionDetails qustionDetails;
    int count = 0,attemptedWithoutAnswer=0,attemptedWithAnswer,notVisited;



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
    public String isGuess="false";
    ProgressBar progressBar;
    public Button nextBtn, prevBtn;
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
    private ImageView image1, image2, image3, image4;
    private long resultDate;
    private boolean isSubmitVisible = false;
    private boolean isDailyTest;
    private long endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_test_v1);
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
                submitAlertDiolog("");
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
                    RequestBody type = RequestBody.create(MediaType.parse("text/plain"),"test");
                    RequestBody remove_bookmark = null;
                    if (qustionDetails.getData().getQuestionList().get(questionIndex).isBookMarked()) {
                        remove_bookmark = RequestBody.create(MediaType.parse("text/plain"), "1");
                        qustionDetails.getData().getQuestionList().get(questionIndex).setBookMarked(false);

                        Log.d("BookMark", "" + 1);
                        star.setBackgroundResource(R.drawable.star_grey);

                    } else {
                        remove_bookmark = RequestBody.create(MediaType.parse("text/plain"), "0");
                        qustionDetails.getData().getQuestionList().get(questionIndex).setBookMarked(true);
                        star.setBackgroundResource(R.drawable.star_colored);
                        Log.d("BookMark", "" + 0);


                    }

                    Utils.showProgressDialog(CustomtestV1TestV1Activity.this);
                    RestClient.bookMarkQuestion(userId, testID, q_id, remove_bookmark,type, new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Utils.dismissProgressDialog();
                            if (response != null && response.code() == 200) {

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

        user_id = DnaPrefs.getString(CustomtestV1TestV1Activity.this, Constants.LOGIN_ID);

        quesionCounter = findViewById(R.id.counter);
        questionpannel = findViewById(R.id.questionpannel);
        answerSheet = findViewById(R.id.answerSheet);
        closeSheet = findViewById(R.id.closeSheet);
        quesionCounter = findViewById(R.id.counter);
        timer = findViewById(R.id.timer);


        Intent intent = getIntent();
        String duration = intent.getStringExtra("duration");
        testName = intent.getStringExtra("testName");
        resultDate = intent.getLongExtra("resultDate", 0);
        endDate = intent.getLongExtra("endDate", 0);
        test_id = intent.getStringExtra("id");
        isDailyTest = intent.getBooleanExtra(Constants.ISDAILY_TEST, false);

        if (!TextUtils.isEmpty(duration) && TextUtils.isDigitsOnly(duration)) {
            if (isDailyTest || endDate*1000 <System.currentTimeMillis()) {
                testDuration = Long.parseLong(duration) * 1000;
            } else {

                testDuration = (endDate * 1000) - System.currentTimeMillis();

            }
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
                    submitQuestionAnswer();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            submitTest();

                        }
                    }, 2 * 1000);
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

        guessCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (guessCheck.isChecked()) {
                    isGuess = "true";
                    questionArrayList.get(questionIndex).setGues(true);
                } else {
                    questionArrayList.get(questionIndex).setGues(false);
                    isGuess = "false";
                }
            }
        });
        closeSheet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void onNextQuestion() {
        timeSpend = System.currentTimeMillis() - tempTime;

        questionArrayList.get(questionIndex).setVisited(true);

        pauseTimer();
        submitTimeLogTest("switch_question", "" + 1);


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

        endTest();

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), test_id);
        RequestBody isSubmit = RequestBody.create(MediaType.parse("text/plain"), "1");
        Utils.showProgressDialog(CustomtestV1TestV1Activity.this);
        RestClient.submitTest(userId, testID, isSubmit, new Callback<TestResult>() {
            @Override
            public void onResponse(Call<TestResult> call, Response<TestResult> response) {
                TestResult testResult = response.body();
                Utils.dismissProgressDialog();

                if (testResult != null) {
                    if (isDailyTest) {
                        Intent intent = new Intent(CustomtestV1TestV1Activity.this, ResultActivity.class);
                        intent.putExtra(Constants.RESULT, testResult);
                        intent.putExtra("testid", test_id);
                        intent.putExtra(Constants.ISDAILY_TEST, isDailyTest);
                        intent.putExtra("resultDate", resultDate);

                        startActivity(intent);
                        Log.d("SubmitTest", " Successuser_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);
                        finish();
                    } else {

                        if (resultDate * 1000 < System.currentTimeMillis()) {
                            Intent intent = new Intent(CustomtestV1TestV1Activity.this, ResultActivity.class);
                            intent.putExtra(Constants.RESULT, testResult);
                            intent.putExtra("testid", test_id);
                            intent.putExtra(Constants.ISDAILY_TEST, isDailyTest);
                            intent.putExtra("resultDate", resultDate);

                            startActivity(intent);
                            Log.d("SubmitTest", " Successuser_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);
                            finish();
                        }else{
                            getResultRemarks(testResult);
                        }

                    }




                }else{
                    Toast.makeText(CustomtestV1TestV1Activity.this,"Please answer atleast 1 question",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<TestResult> call, Throwable t) {
                Utils.dismissProgressDialog();
                Toast.makeText(CustomtestV1TestV1Activity.this,"Please answer atleast 1 question",Toast.LENGTH_LONG).show();
                Log.d("SubmitTest", "Faileduser_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);
            }
        });

    }

    private void getResultRemarks(TestResult testResult) {
        if (!Utils.isInternetConnected(this)) {
            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        Utils.showProgressDialog(CustomtestV1TestV1Activity.this);
        RestClient.getResultRemark(test_id, new Callback<RankResultRemarks>() {
            @Override
            public void onResponse(Call<RankResultRemarks> call, Response<RankResultRemarks> response) {
                RankResultRemarks remarks = response.body();
                Utils.dismissProgressDialog();

                if (remarks != null) {
                    displayRemark(remarks, testResult);
                }

            }

            @Override
            public void onFailure(Call<RankResultRemarks> call, Throwable t) {
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


        PicassoImageGetter imageGetter = new PicassoImageGetter(questionTxt, this);
        answerList.removeAllViews();
        Question question = questionArrayList.get(questionIndex);


        question_id = question.getId();
        guessCheck.setChecked(question.isGues());

        if (question.isBookMarked()) {
            star.setBackgroundResource(R.drawable.star_colored);
        } else {
            star.setBackgroundResource(R.drawable.star_grey);
        }

        if (!TextUtils.isEmpty(question.getTitle_image())) {
            if (Utils.isInternetConnected(CustomtestV1TestV1Activity.this)) {
                imageQuestion.setVisibility(View.VISIBLE);
                Glide.with(this).load(question.getTitle_image())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                }
                                imageQuestion.setVisibility(View.GONE);
                                //Toast.makeText(TestV1Activity.this, "Unable to load image", Toast.LENGTH_LONG).show();

                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                if(progressBar!=null){
                                    progressBar.setVisibility(View.GONE);
                                }
                                return false;
                            }
                        })
                        .into(imageQuestion);
//                        , new com.squareup.picasso.Callback() {
//                            @Override
//                            public void onSuccess() {
//                                if (progressBar != null) {
//                                    progressBar.setVisibility(View.GONE);
//                                }
//                            }
//
//                            @Override
//                            public void onError() {
//                                if (progressBar != null) {
//                                    progressBar.setVisibility(View.GONE);
//                                }
//                                imageQuestion.setVisibility(View.GONE);
//                                //Toast.makeText(TestV1Activity.this, "Unable to load image", Toast.LENGTH_LONG).show();
//
//
//                            }
//                        });
            }

        } else {
            imageQuestion.setVisibility(View.GONE);
        }

        Spannable html;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            html = (Spannable) Html.fromHtml("Q" + (questionIndex + 1) + ". " + question.getTitle(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
        } else {
            html = (Spannable) Html.fromHtml("Q" + (questionIndex + 1) + ". " + question.getTitle(), imageGetter, null);
        }
        questionTxt.setText(html);


        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    View answerView = inflater.inflate(R.layout.item_answer, null, false);
                    answer1 = answerView.findViewById(R.id.answer);
                    image1 = answerView.findViewById(R.id.image);
                    TextView tag1 =  answerView.findViewById(R.id.tag);
                    tag1.setText("A");
                    cardView1 = answerView.findViewById(R.id.cardView);


                    Spannable html1;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        html1 = (Spannable) Html.fromHtml("" + question.getOption1(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
                    } else {
                        html1 = (Spannable) Html.fromHtml("" + question.getOption1(), imageGetter, null);
                    }
                    answer1.setText(html1);



                    if (!TextUtils.isEmpty(question.getOption_1_image())){
                        Glide.with(this).load(question.getOption_1_image())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        image1.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                })
                                .into(image1);
//                        new com.squareup.picasso.Callback() {
//                                    @Override
//                                    public void onSuccess() {
//
//                                    }
//
//                                    @Override
//                                    public void onError() {
//
//                                        image1.setVisibility(View.GONE);
//                                        //Toast.makeText(TestV1Activity.this, "Unable to load image", Toast.LENGTH_LONG).show();
//
//
//                                    }
//                                });
                    }else{
                        image1.setVisibility(View.GONE);

                    }


                    answerList.addView(answerView);

                    if (!TextUtils.isEmpty(question.getSelectedOption()) && question.getOption1().equalsIgnoreCase(question.getSelectedOption())) {
                        updateAnswer(cardView1, answer1);
                    }
                    cardView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            answer = "1";
                            updateAnswer(cardView1, answer1);

                            if (!TextUtils.isEmpty(question.getSelectedOption())) {
                                updateToServerAnswerSelection();
                            } else {
                                updateMarkingOptionTime();
                            }
                            updateAnswer(cardView1, answer1);
                            question.setSelectedOption(question.getOption1());

                        }
                    });
                    break;
                case 1:
                    View answerView1 = inflater.inflate(R.layout.item_answer,
                            null, false);
                    answer2 = answerView1.findViewById(R.id.answer);
                    image2 = answerView1.findViewById(R.id.image);
                    TextView tag2 =  answerView1.findViewById(R.id.tag);
                    tag2.setText("B");
                    cardView2 = answerView1.findViewById(R.id.cardView);

                    Spannable html2;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        html2 = (Spannable) Html.fromHtml("" + question.getOption2(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
                    } else {
                        html2 = (Spannable) Html.fromHtml("" + question.getOption2(), imageGetter, null);
                    }

                    answer2.setText(html2);
                    answerList.addView(answerView1);
                    if (!TextUtils.isEmpty(question.getSelectedOption()) && question.getOption2().equalsIgnoreCase(question.getSelectedOption())) {
                        updateAnswer(cardView2, answer2);
                    }


                    if (!TextUtils.isEmpty(question.getOption_2_image())){
                        Glide.with(this).load(question.getOption_2_image())
                                .addListener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        image2.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                })
                                .into(image2);

                    }else{
                        image2.setVisibility(View.GONE);

                    }
                    cardView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            answer = "2";
                            if (!TextUtils.isEmpty(question.getSelectedOption())) {
                                updateToServerAnswerSelection();
                            } else {
                                updateMarkingOptionTime();
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
                    image3 = answerView2.findViewById(R.id.image);
                    TextView tag3 =  answerView2.findViewById(R.id.tag);
                    tag3.setText("C");
                    cardView3 = answerView2.findViewById(R.id.cardView);

                    Spannable html3;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        html3 = (Spannable) Html.fromHtml("" + question.getOption3(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
                    } else {
                        html3 = (Spannable) Html.fromHtml("" + question.getOption3(), imageGetter, null);
                    }

                    answer3.setText(html3);

                    answerList.addView(answerView2);

                    if (!TextUtils.isEmpty(question.getOption_3_image())){
                        Glide.with(this).load(question.getOption_3_image())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        image3.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                })
                                .into(image3);

                    }else{
                        image3.setVisibility(View.GONE);

                    }
                    if (!TextUtils.isEmpty(question.getSelectedOption()) && question.getOption3().equalsIgnoreCase(question.getSelectedOption())) {
                        updateAnswer(cardView3, answer3);
                    }
                    cardView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            answer = "3";
                            if (!TextUtils.isEmpty(question.getSelectedOption())) {
                                updateToServerAnswerSelection();
                            } else {
                                updateMarkingOptionTime();
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
                    image4 = answerView4.findViewById(R.id.image);
                    TextView tag4 =  answerView4.findViewById(R.id.tag);
                    tag4.setText("D");
                    cardView4 = answerView4.findViewById(R.id.cardView);

                    Spannable html4;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        html4 = (Spannable) Html.fromHtml("" + question.getOption4(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
                    } else {
                        html4 = (Spannable) Html.fromHtml("" + question.getOption4(), imageGetter, null);
                    }

                    answer4.setText(html4);
                    answerList.addView(answerView4);
                    if (!TextUtils.isEmpty(question.getSelectedOption()) && question.getOption4().equalsIgnoreCase(question.getSelectedOption())) {
                        updateAnswer(cardView4, answer4);
                    }


                    if (!TextUtils.isEmpty(question.getOption_4_image())){
                        Glide.with(this).load(question.getOption_4_image())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        image4.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                })
                                .into(image4);

                    }else{
                        image4.setVisibility(View.GONE);

                    }
                    cardView4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            answer = "4";
                            if (!TextUtils.isEmpty(question.getSelectedOption())) {
                                updateToServerAnswerSelection();
                            } else {
                                updateMarkingOptionTime();
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
        popup.setOnMenuItemClickListener(CustomtestV1TestV1Activity.this);
        popup.inflate(R.menu.pop_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
       if(item.getItemId()==R.id.reviewTest) {
           questionpannel.setVisibility(View.GONE);
           answerSheet.setVisibility(View.VISIBLE);
           closeSheet.setVisibility(View.VISIBLE);
           loadSheet();

           return true;
       } else if (item.getItemId()==R.id.submitTest){

                submitAlertDiolog("");
                return true;
       } else if (item.getItemId()==R.id.closeSheet){
                questionpannel.setVisibility(View.VISIBLE);
                answerSheet.setVisibility(View.GONE);
                closeSheet.setVisibility(View.GONE);
                return true;
       } else if (item.getItemId()== R.id.discardTest) {
           discardAlertDialog();
           return true;
       }else{
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

    private void submitAlertDiolog(String message) {

        if (isSubmitVisible) {
            return;
        }

        isSubmitVisible = true;
        int count = getUnAttemptedCount();
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.submit_alert_diolog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        TextView totalQuestionTv = dialogView.findViewById(R.id.totalQuestionTv);
        TextView attemptedTV = dialogView.findViewById(R.id.attemptedTV);
        TextView unAttemptedTV = dialogView.findViewById(R.id.unAttemptedTV);
        TextView markForReviewWithAnswer = dialogView.findViewById(R.id.markForReviewWithAnswer);
        TextView markForReviewWithoutAnswer = dialogView.findViewById(R.id.markForReviewWithoutAnswer);
        TextView notVisited = dialogView.findViewById(R.id.notVisited);

        if (qustionDetails != null && qustionDetails.getData() != null
                && qustionDetails.getData().getQuestionList() != null && qustionDetails.getData().getQuestionList().size() > 0) {

            totalQuestionTv.setText(""+qustionDetails.getData().getQuestionList().size());
            int markReviewWithAnsewer=0;
            int markReviewWithoutAnsewer=0;
            int attempted=0;
            int unattempted=0;
            int notAttempted=0;
            for (Question question : qustionDetails.getData().getQuestionList()) {
                if(!TextUtils.isEmpty(question.getSelectedOption()) && question.isGues()){
                    markReviewWithAnsewer++;
                }else if (!TextUtils.isEmpty(question.getSelectedOption())) {
                    attempted++;
                }else if (TextUtils.isEmpty(question.getSelectedOption()) && question.isGues()){
                    markReviewWithoutAnsewer++;
                }else if(question.isVisited() && TextUtils.isEmpty(question.getSelectedOption()) && !question.isGues()){
                    unattempted++;
                }else{
                    notAttempted++;
                }
            }
            attemptedTV.setText(""+attempted);
            unAttemptedTV.setText(""+unattempted);
            markForReviewWithAnswer.setText(""+markReviewWithAnsewer);
            markForReviewWithoutAnswer.setText(""+markReviewWithoutAnsewer);
            notVisited.setText(""+notAttempted);


        }


        final android.app.AlertDialog dialog = dialogBuilder.create();
        Button btn_yes = dialogView.findViewById(R.id.btn_done);



        TextView text_cancel = dialogView.findViewById(R.id.text_cancel);
        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isSubmitVisible = false;

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSubmitVisible = false;


                submitTest();
                dialog.dismiss();

            }
        });

        if (!isFinishing() && !dialog.isShowing())
            dialog.show();


    }

    private void displayRemark(RankResultRemarks resultRemarks, TestResult testResult) {

        int count = getUnAttemptedCount();
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_remarks, null);
        dialogBuilder.setView(dialogView);

        final android.app.AlertDialog dialog = dialogBuilder.create();
        Button btn_yes = dialogView.findViewById(R.id.ok);
        TextView messagetv = dialogView.findViewById(R.id.message);
        messagetv.setText("" + resultRemarks.getRemarks());

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resultRemarks == null) {
                    return;
                }
                if (Long.parseLong(resultRemarks.getResultDate()) * 1000 > System.currentTimeMillis()) {
                    finish();
                    dialog.dismiss();
                } else {
                    Intent intent = new Intent(CustomtestV1TestV1Activity.this, ResultActivity.class);
                    intent.putExtra(Constants.RESULT, testResult);
                    intent.putExtra("testid", test_id);
                    intent.putExtra(Constants.ISDAILY_TEST, isDailyTest);
                    intent.putExtra("resultDate", resultDate);

                    startActivity(intent);
                    Log.d("SubmitTest", " Successuser_id-->" + user_id + "TestId-->" + test_id + "Question_id-->" + question_id + "Answer-->" + answer + " Guess-->" + isGuess);
                    finish();
                }


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
                            Toast.makeText(CustomtestV1TestV1Activity.this, "No question here", Toast.LENGTH_LONG).show();
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
        } else {
            //super.onBackPressed();
            submitAlertDiolog("Test will be submitted!, want to submit?");
        }

    }


    private int getUnAttemptedCount() {
        if (qustionDetails != null && qustionDetails.getData() != null
                && qustionDetails.getData().getQuestionList() != null && qustionDetails.getData().getQuestionList().size() > 0) {
            for (Question question : qustionDetails.getData().getQuestionList()) {
                if(!TextUtils.isEmpty(question.getSelectedOption()) && question.isGues()){

                }else if (!TextUtils.isEmpty(question.getSelectedOption())) {

                }else if (question.isGues()){

                }else if(question.isVisited() && TextUtils.isEmpty(question.getSelectedOption()) && !question.isGues()){

                }else{

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
        submitTimeLogTest("selecting_option", "" + 1);
        //Toast.makeText(this, "Time for Select Answer ==  time" + Seconds, Toast.LENGTH_LONG).show();

        submitAnswer();
        resettimer();
        startTimer();
    }

    private void updateMarkingOptionTime() {
        pauseTimer();
        submitTimeLogTest("selecting_option", "" + 1);
        //  Toast.makeText(activity, "Time for Select Answer ==  time" + activity.Seconds, Toast.LENGTH_LONG).show();

        //  submitAnswer();
        resettimer();
        startTimer();
    }


    private void updateAnswer(CardView cardView, TextView answer) {
        cardView1.setBackgroundResource(R.drawable.answer_selecter);
        cardView2.setBackgroundResource(R.drawable.answer_selecter);
        cardView3.setBackgroundResource(R.drawable.answer_selecter);
        cardView4.setBackgroundResource(R.drawable.answer_selecter);
        if (questionIndex == (questionArrayList.size() - 1)) {
            nextBtn.setText("SUBMIT");
        } else {
            nextBtn.setText("NEXT");
        }

        answer1.setTextColor(getResources().getColor(R.color.black));
        answer2.setTextColor(getResources().getColor(R.color.black));
        answer3.setTextColor(getResources().getColor(R.color.black));
        answer4.setTextColor(getResources().getColor(R.color.black));
        //answer.setTextColor(getResources().getColor(R.color.white));

        cardView.setBackgroundResource(R.drawable.answer_selecter_selected);//;setCardBackgroundColor(getResources().getColor(R.color.test_fragment_card_bacckground));
    }


    private void endTest() {
        if (!Utils.isInternetConnected(this)) {
            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(test_id)) {
            return;
        }
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), test_id);
        RequestBody time = RequestBody.create(MediaType.parse("text/plain"), "" + (System.currentTimeMillis() / 1000));
        RestClient.endTest(userId, testID, time, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("data", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}