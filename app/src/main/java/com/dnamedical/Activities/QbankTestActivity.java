package com.dnamedical.Activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Models.newqbankmodule.MCQQuestionList;
import com.dnamedical.Models.newqbankmodule.ModulesMcq;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class QbankTestActivity extends AppCompatActivity {

    private TextView tv;
    boolean isLast = false;

    public static String quest_id;
    String module_id;

    public String user_id;
    public String user_answer = null;
    public MCQQuestionList qbankTestResponse;
    ImageView imageViewCancel;
    int progress = 100;
    LinearLayout linearBottom;
    public Button nextBtn;
    public Button skipBtn;

    LinearLayout answerList;
    RelativeLayout skipLayout;
    QbankTestActivity qbankTestActivity;
    List<ModulesMcq> questionDetail;
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    public ProgressBar progressBar, progressbarForImage;
    LinearLayout questionList, questionListDescription, questionmarking;
    TextView qustion, aTV, aTVPer, bTV, bTVPer, cTV, cTVPer, dTV, dTVPer, rTV, barChart;
    ImageView imgA, imgB, imgC, imgD;
    ProgressBar progressBarChart;
    WebView webView;
    LayoutInflater inflater;
    private int questionNo;
    private ImageView bookmark, question_image;
    private String chapter_Id;
    private String chap_ID;
    private ModulesMcq questionDetails;
    private SeekBar timerProgressBar;
    CountDownTimer countDownTimer;
    private TextView qbank_answer1, qbank_answer2, qbank_answer3, qbank_answer4;
    private ImageView image1, image2, image3, image4;
    private int questionTime = 10;
    private List<TextView> tvList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_test);
        //inalalze View
        initViews();
        inflater = LayoutInflater.from(this);


        if (getIntent().hasExtra("qmodule_id")) {
            module_id = getIntent().getStringExtra("qmodule_id");
            user_id = DnaPrefs.getString(QbankTestActivity.this, Constants.LOGIN_ID);
            chap_ID = getIntent().getStringExtra("chap_ID");
            questionNo = getIntent().getIntExtra("questionStartId", 0);


        }
        qbankgetTest();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextBtn.getText().toString().equalsIgnoreCase("Complete")) {
                    showDialog(user_id, module_id, "1", DnaPrefs.getString(QbankTestActivity.this, "subject_id"), chap_ID);

                } else {
                    showHideBottomLayout(false);
                    answerList.setVisibility(View.VISIBLE);
                    questionListDescription.setVisibility(View.GONE);
                    qustion.setVisibility(View.GONE);


                    if (questionNo != questionDetail.size()) {
                        questionNo++;
                    }
                    solveQBank(questionNo);


                }
            }
        });


        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_answer="0";
                updateAnswer(questionDetails, questionDetails.getCorrectAnswer(), questionDetails.getId(), isLast);
                            }
        });


    }


    private void initViews() {

        timerProgressBar = findViewById(R.id.progressTimer);
        imageViewCancel = findViewById(R.id.btnCancel);
        linearBottom = findViewById(R.id.linearBottom);
        questionmarking = findViewById(R.id.layoutDots);
        nextBtn = findViewById(R.id.nextBtn);
        skipBtn = findViewById(R.id.skipBtn);
        question_image = findViewById(R.id.question_image);
        answerList = findViewById(R.id.questionList);
        skipLayout = findViewById(R.id.skipLayout);
        bookmark = findViewById(R.id.bookmark);
        progressBarChart = findViewById(R.id.progress_bar_chart);
        questionListDescription = findViewById(R.id.questionListDescription);
        qbankTestActivity = this;
        progressBar = findViewById(R.id.progressBar);
        progressbarForImage = findViewById(R.id.progressbarForImage);

        qustion = findViewById(R.id.qtext);


        timerProgressBar.setMax(10);
        timerProgressBar.setPadding(0, 0, 0, 0);
        imgA = findViewById(R.id.imga);
        imgB = findViewById(R.id.imgb);
        imgC = findViewById(R.id.imgc);
        imgD = findViewById(R.id.imgd);


        aTV = findViewById(R.id.optionA);
        aTVPer = findViewById(R.id.optionAPer);

        bTV = findViewById(R.id.optionB);
        bTVPer = findViewById(R.id.optionBPer);

        cTV = findViewById(R.id.optionC);
        cTVPer = findViewById(R.id.optionCPer);

        dTV = findViewById(R.id.optionD);
        dTVPer = findViewById(R.id.optionDPer);
        barChart = findViewById(R.id.bar_chart_percentage);
        rTV = findViewById(R.id.reference);

        webView = findViewById(R.id.webView);


        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(module_id)
                        && !TextUtils.isEmpty(quest_id)) {
                    RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
                    RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), module_id);
                    RequestBody q_id = RequestBody.create(MediaType.parse("text/plain"), "" + quest_id);
                    RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "qbank");
                    RequestBody remove_bookmark = null;
                    if (questionDetail.get(questionNo).isBookmarked()) {
                        remove_bookmark = RequestBody.create(MediaType.parse("text/plain"), "1");
                        questionDetail.get(questionNo).setBookmarked(false);
                        Log.d("BookMark", "" + 1);
                        Utils.setTintForImage(QbankTestActivity.this,bookmark,R.color.dark_gray);


                    } else {
                        remove_bookmark = RequestBody.create(MediaType.parse("text/plain"), "0");
                        questionDetail.get(questionNo).setBookmarked(true);
                        Utils.setTintForImage(QbankTestActivity.this,bookmark,R.color.colorPrimary);

                        Log.d("BookMark", "" + 0);


                    }

                   // Utils.showProgressDialog(QbankTestActivity.this);
                    RestClient.bookMarkQuestion(userId, testID, q_id, remove_bookmark, type, new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                          //  Utils.dismissProgressDialog();
                            if (response != null && response.code() == 200) {

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            //Utils.dismissProgressDialog();
                        }
                    });
                }

            }
        });


        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(user_id, module_id, "0", DnaPrefs.getString(QbankTestActivity.this, "subject_id"), chap_ID);
            }
        });

        startTimer();


    }

    private void startTimer() {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            // will update the "progress" propriety of seekbar until it reaches progress
            ObjectAnimator animation = ObjectAnimator.ofInt(timerProgressBar, "progress", 0);
            animation.setDuration(25 * 1000); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        } else
            timerProgressBar.setProgress(progress); // no animation on Gingerbread or lower

//        countDownTimer = new CountDownTimer(25*1000, 1000) {
//
//            public void onTick(long millis) {
//
//            }
//
//            public void onFinish() {
//                timerProgressBar.setProgress((0));
//                user_answer = questionDetails.getCorrectAnswer();
//                submitAnswer(questionDetails);
//            }
//
//        };
//
//        countDownTimer.start();


    }

    public void showHideBottomLayout(boolean show) {
        //TODO call submit answer api and visible layout on response
        if (show) {
            linearBottom.setVisibility(View.VISIBLE);
        } else {
            linearBottom.setVisibility(View.GONE);

        }

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void qbankgetTest() {
        RequestBody qmodule_id = RequestBody.create(MediaType.parse("text/plain"), module_id);
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.qbanksubTestData(userId, qmodule_id, new Callback<MCQQuestionList>() {
                @Override
                public void onResponse(Call<MCQQuestionList> call, Response<MCQQuestionList> response) {
                    Utils.dismissProgressDialog();
                    if (response.isSuccessful()) {

                        if (response.body() != null) {
                            qbankTestResponse = response.body();

                            if (qbankTestResponse.getData() != null) {
                                questionDetail = qbankTestResponse.getData().getModulesMcqs();
                                populateText(questionmarking, questionDetail);


                                //populateText(questionmarking,questionDetail);

                                if (questionNo >= questionDetail.size()) {
                                    solveQBank(questionNo - 1);
                                } else {
                                    solveQBank(questionNo);
                                }
                            }

                        }
//                        mAdapter = new MyAdapter(getSupportFragmentManager(), qbankTestResponse, quesionCounter, mProgressBar, mCountDownTimer);
//                        mPager = findViewById(R.id.pager2);
//                        mPager.addOnPageChangeListener(pageChangeListener);
//                        mPager.setAdapter(mAdapter);
//                        mPager.setCurrentItem(questionStartId);
//                        mPager.setOnTouchListener(vOnTouchListener);
//                        mPager.setHorizontalScrollBarEnabled(false);
                    }
                }

                @Override
                public void onFailure(Call<MCQQuestionList> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(QbankTestActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
        }
    }

    private void solveQBank(int questinNo) {
        if (questinNo < questionDetail.size()) {
//            timerProgressBar.setMax(10);
//            timerProgressBar.setProgress(10);
//            startTimer();

            questionDetails = questionDetail.get(questinNo);
            TextView questionText = new TextView(this);
            questionText.setPadding(15, 0, 5, 0);
            questionText.setText("Q " + (questinNo + 1) + ". " + questionDetails.getMcqTitle());
            questionText.setTypeface(questionText.getTypeface(), Typeface.BOLD);
            questionText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            quest_id = "" + questionDetails.getId();
            chapter_Id = "" + questionDetails.getCategoryId();

            ImageView iv = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 540);
            iv.setLayoutParams(lp);
            Utils.setTintForImage(QbankTestActivity.this,bookmark,R.color.dark_gray);


            answerList.removeAllViews();
            answerList.addView(questionText);
            if (!TextUtils.isEmpty(questionDetails.getTitleImage())) {
                if (Utils.isInternetConnected(QbankTestActivity.this)) {
                   // question_image.setVisibility(View.VISIBLE);
                    progressbarForImage.setVisibility(View.VISIBLE);

                    Picasso.with(this).load(questionDetails.getTitleImage())
                            .into(iv, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    if (progressbarForImage != null) {
                                        progressbarForImage.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onError() {
                                    if (progressbarForImage != null) {
                                        progressbarForImage.setVisibility(View.GONE);
                                    }
                                    question_image.setVisibility(View.GONE);
                                    //Toast.makeText(TestV1Activity.this, "Unable to load image", Toast.LENGTH_LONG).show();


                                }
                            });
                }

            }else{
                progressbarForImage.setVisibility(GONE);
            }


            skipLayout.setVisibility(View.VISIBLE);

            for (int i = 1; i < 5; i++) {

                switch (i) {
                    case 1:

                        View answer1 = inflater.inflate(R.layout.qbank_item_test, null);
                        qbank_answer1 = answer1.findViewById(R.id.qbank_answer);
                        image1 = answer1.findViewById(R.id.image);
                        cardView1 = answer1.findViewById(R.id.cardView);
                        qbank_answer1.setText("A. " + questionDetails.getOption1());

                        answerList.addView(answer1);
                        cardView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_answer = "1";

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateAnswer(questionDetails, "1", questionDetails.getId(), isLast);
                                    }
                                }, 1 * 1000);

                            }
                        });
                        break;
                    case 2:
                        View answer2 = inflater.inflate(R.layout.qbank_item_test, null);
                        qbank_answer2 = answer2.findViewById(R.id.qbank_answer);
                        image2 = answer2.findViewById(R.id.image);

                        cardView2 = answer2.findViewById(R.id.cardView);

                        qbank_answer2.setText("B. " + questionDetails.getOption2());
                        answerList.addView(answer2);
                        cardView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                user_answer = "2";
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateAnswer(questionDetails,"2", questionDetails.getId(), isLast);
                                    }
                                }, 1 * 1000);

                            }
                        });
                        break;

                    case 3:

                        View answer3 = inflater.inflate(R.layout.qbank_item_test, null);
                        qbank_answer3 = answer3.findViewById(R.id.qbank_answer);
                        image3 = answer3.findViewById(R.id.image);

                        qbank_answer3.setText("C. " + questionDetails.getOption3());
                        cardView3 = answer3.findViewById(R.id.cardView);

                        answerList.addView(answer3);
                        cardView3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_answer = "3";

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateAnswer(questionDetails, "3", questionDetails.getId(), isLast);
                                    }
                                }, 1 * 1000);
                            }
                        });
                        break;
                    case 4:
                        View answer4 = inflater.inflate(R.layout.qbank_item_test, null);
                        qbank_answer4 = answer4.findViewById(R.id.qbank_answer);
                        image4 = answer4.findViewById(R.id.image);


                        cardView4 = answer4.findViewById(R.id.cardView);

                        qbank_answer4.setText("D. " + questionDetails.getOption4());
                        answerList.addView(answer4);
                        cardView4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_answer = "4";
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateAnswer(questionDetails, "4", questionDetails.getId(), isLast);
                                    }
                                }, 1 * 1000);


                            }
                        });
                        break;
                }
            }
        }

    }


    private void updateAnswer(ModulesMcq modulesMcq, String answervalue, String questionId, boolean isLast) {
        cardView1.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView2.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView3.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView4.setCardBackgroundColor(getResources().getColor(R.color.white));


        if (answervalue.equalsIgnoreCase("1")) {
            if (answervalue.equalsIgnoreCase(modulesMcq.getCorrectAnswer())) {
                cardView1.setCardBackgroundColor(getResources().getColor(R.color.green));
            } else {
                cardView1.setCardBackgroundColor(getResources().getColor(R.color.red));

            }
            qbank_answer1.setTextColor(getResources().getColor(R.color.white));


        }
        if (answervalue.equalsIgnoreCase("2")) {
            if (answervalue.equalsIgnoreCase(modulesMcq.getCorrectAnswer())) {
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.green));
            } else {
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.red));
            }
            qbank_answer2.setTextColor(getResources().getColor(R.color.white));

        }
        if (answervalue.equalsIgnoreCase("3")) {
            if (answervalue.equalsIgnoreCase(modulesMcq.getCorrectAnswer())) {
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.green));
            } else {
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.red));
            }
            qbank_answer3.setTextColor(getResources().getColor(R.color.white));

        }
        if (answervalue.equalsIgnoreCase("4")) {
            if (answervalue.equalsIgnoreCase(modulesMcq.getCorrectAnswer())) {
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.green));

            } else {
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.red));
            }
            qbank_answer4.setTextColor(getResources().getColor(R.color.white));


        }

        if (answervalue.equalsIgnoreCase(modulesMcq.getCorrectAnswer())) {
            if (questionNo >= questionDetail.size()) {
                changeMarkingolor(tvList.get(questionNo - 1), getResources().getColor(R.color.green));
            } else {
                changeMarkingolor(tvList.get(questionNo), getResources().getColor(R.color.green));
            }
        } else {

            if (questionNo >= questionDetail.size()) {
                changeMarkingolor(tvList.get(questionNo - 1), getResources().getColor(R.color.red));
            } else {
                changeMarkingolor(tvList.get(questionNo), getResources().getColor(R.color.red));
            }
        }

        switch (modulesMcq.getCorrectAnswer()) {
            case "1":
                cardView1.setCardBackgroundColor(getResources().getColor(R.color.green));
                qbank_answer1.setTextColor(getResources().getColor(R.color.white));
                break;
            case "2":
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.green));
                qbank_answer2.setTextColor(getResources().getColor(R.color.white));


                break;
            case "3":
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.green));
                qbank_answer3.setTextColor(getResources().getColor(R.color.white));


                break;
            case "4":
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.green));
                qbank_answer4.setTextColor(getResources().getColor(R.color.white));
                break;
        }


        submitAnswer(modulesMcq);
    }

    public void submitAnswer(ModulesMcq modulesMcq) {
        // Utils.showProgressDialog(qbankTestActivity);
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//            ;
//            timerProgressBar.setProgress(0);
//        }
        RequestBody mcq_id = RequestBody.create(MediaType.parse("text/plain"), modulesMcq.getId());

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);

        RequestBody givenAnswer = RequestBody.create(MediaType.parse("text/plain"), user_answer);

        RequestBody moduleID = RequestBody.create(MediaType.parse("text/plain"), module_id);


        Log.d("Data", "UserId ->" + user_id + " Questions Id ->" + modulesMcq.getId() + " Given Answer ->" + user_answer + "  Module  " + module_id);

        RestClient.submitAnswer(userId, mcq_id, moduleID, givenAnswer, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Utils.dismissProgressDialog();
                showHideBottomLayout(true);
                updateUI(modulesMcq.getId(), user_answer, modulesMcq);

                try {
                    if (questionNo >= questionDetail.size() - 1) {
                        nextBtn.setText("Complete");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                answerList.removeAllViews();
                answerList.setVisibility(GONE);
                questionListDescription.setVisibility(View.VISIBLE);
                qustion.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                qbankTestActivity.showHideBottomLayout(false);
                // Utils.dismissProgressDialog();
            }
        });
    }

    private void updateUI(String questionId, String givenAnswer, ModulesMcq modulesMcq) {


        if (modulesMcq != null) {
//            optionA.setText("A. " + question.getOption1() + " [" + question.getOption1Percenatge() + "%]");
//            optionB.setText("B. " + question.getOption2() + " [" + question.getOption2Percenatge() + "%]");
//            optionC.setText("C. " + question.getOption3() + " [" + question.getOption3Percenatge() + "%]");
//            optionD.setText("D. " + question.getOption4() + " [" + question.getOption4Percenatge() + "%]");
//

            aTV.setText("A. " + modulesMcq.getOption1());
            bTV.setText("B. " + modulesMcq.getOption2());
            cTV.setText("C. " + modulesMcq.getOption3());
            dTV.setText("D. " + modulesMcq.getOption4());


            if (questionNo == questionDetail.size()) {
                qustion.setText("Q " + (questionNo) + ". " + modulesMcq.getMcqTitle());
            } else {
                qustion.setText("Q " + (questionNo + 1) + ". " + modulesMcq.getMcqTitle());

            }

//            if (!TextUtils.isEmpty(modulesMcq.getTitleImage())) {
//                Picasso.with(this).load(modulesMcq.getTitleImage()).into(modulesMcq);
//            }
//            List<SliceValue> pieData = new ArrayList<>();
//            pieData.add(new SliceValue(modulesMcq.getPercentage(), R.color.colorPrimary));
//            pieData.add(new SliceValue(100 - question.getPercentage(), R.color.colorPrimary));
//            PieChartData pieChartData = new PieChartData(pieData);
//            pieChartView.setPieChartData(pieChartData);
            imgA.setVisibility(GONE);
            imgB.setVisibility(GONE);
            imgC.setVisibility(GONE);
            imgD.setVisibility(GONE);

            skipLayout.setVisibility(GONE);

            if (!givenAnswer.equalsIgnoreCase("0")) {
                switch (givenAnswer) {
                    case "1":
                        if (modulesMcq.getCorrectAnswer().equalsIgnoreCase("1")) {
                            aTV.setTextColor(ContextCompat.getColor(this, R.color.green));
                            imgA.setImageResource(R.drawable.right_answer_icon);
                        } else {
                            aTV.setTextColor(ContextCompat.getColor(this, R.color.black));
                            imgA.setImageResource(R.drawable.wrong_answer_icon);

                        }

                        imgA.setVisibility(View.VISIBLE);
                        break;
                    case "2":
                        if (modulesMcq.getCorrectAnswer().equalsIgnoreCase("2")) {
                            bTV.setTextColor(ContextCompat.getColor(this, R.color.green));
                            imgB.setImageResource(R.drawable.right_answer_icon);
                        } else {
                            bTV.setTextColor(ContextCompat.getColor(this, R.color.black));
                            imgB.setImageResource(R.drawable.wrong_answer_icon);

                        }
                        imgB.setVisibility(View.VISIBLE);

                        break;
                    case "3":
                        if (modulesMcq.getCorrectAnswer().equalsIgnoreCase("3")) {
                            cTV.setTextColor(ContextCompat.getColor(this, R.color.green));
                            imgC.setImageResource(R.drawable.right_answer_icon);
                        } else {
                            cTV.setTextColor(ContextCompat.getColor(this, R.color.black));
                            imgC.setImageResource(R.drawable.wrong_answer_icon);

                        }
                        imgC.setVisibility(View.VISIBLE);

                        break;
                    case "4":
                        if (modulesMcq.getCorrectAnswer().equalsIgnoreCase("4")) {
                            dTV.setTextColor(ContextCompat.getColor(this, R.color.green));
                            imgD.setImageResource(R.drawable.right_answer_icon);
                        } else {
                            dTV.setTextColor(ContextCompat.getColor(this, R.color.black));
                            imgD.setImageResource(R.drawable.wrong_answer_icon);

                        }

                        imgD.setVisibility(View.VISIBLE);

                        break;
                }
            }


            aTV.setTextColor(ContextCompat.getColor(this, R.color.black));
            bTV.setTextColor(ContextCompat.getColor(this, R.color.black));
            cTV.setTextColor(ContextCompat.getColor(this, R.color.black));
            dTV.setTextColor(ContextCompat.getColor(this, R.color.black));

            switch (modulesMcq.getCorrectAnswer()) {
                case "1":
                    aTV.setTextColor(ContextCompat.getColor(this, R.color.green));
                    imgA.setImageResource(R.drawable.right_answer_icon);
                    imgA.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    bTV.setTextColor(ContextCompat.getColor(this, R.color.green));
                    imgB.setImageResource(R.drawable.right_answer_icon);
                    imgB.setVisibility(View.VISIBLE);

                    break;
                case "3":
                    cTV.setTextColor(ContextCompat.getColor(this, R.color.green));
                    imgC.setImageResource(R.drawable.right_answer_icon);
                    imgC.setVisibility(View.VISIBLE);

                    break;
                case "4":
                    dTV.setTextColor(ContextCompat.getColor(this, R.color.green));
                    imgD.setImageResource(R.drawable.right_answer_icon);
                    imgD.setVisibility(View.VISIBLE);

                    break;
            }
            if (Utils.isInternetConnected(this)) {
                try {
                    loadView(modulesMcq.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }


    }


    public class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(GONE);
        }
    }


    private void loadView(String qID) throws Exception {

        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);

        progressBar.setVisibility(View.VISIBLE);
        webView.loadUrl("http://13.232.100.13/reviewqbank.php?id=" + qID);


    }


    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(GONE);
        }
    }


    private void showDialog(String user_id, String module_id, String complete_status, String subject_id, String chapter_id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (complete_status.equalsIgnoreCase("0")){
            builder.setTitle("Pause");
            builder.setMessage("Do you want to pause?");
        }else{
            builder.setTitle("Submit");
            builder.setMessage("Your module will be submitted");

        }
        String positiveText = "YES";
        builder.setPositiveButton(positiveText, (dialog, which) -> {
            dialog.dismiss();
            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
            RequestBody moduleId = RequestBody.create(MediaType.parse("text/plain"), module_id);
            RequestBody complete = RequestBody.create(MediaType.parse("text/plain"), complete_status);
            RequestBody subID = RequestBody.create(MediaType.parse("text/plain"), subject_id);
            RequestBody chapId = RequestBody.create(MediaType.parse("text/plain"), chapter_id);


            RestClient.completeMCQ(userId, moduleId, complete, subID, chapId, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (complete_status.equalsIgnoreCase("0")){
                      finish();
                    }else{
                        Intent    intent = new Intent(QbankTestActivity.this, QbankRatingActivity.class);
                        intent.putExtra("module_id", module_id);
                        intent.putExtra("userId", user_id);
                        startActivity(intent);
                        finish();

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        });
        String negativeText = "CANCEL";
        builder.setNegativeButton(negativeText, (dialog, which) -> {
            dialog.dismiss();
            finish();
        });

        AlertDialog dialog = builder.create();
        if (!dialog.isShowing())
            dialog.show();
    }


    private void populateText(LinearLayout ll, List<ModulesMcq> views) {
        Display display = getWindowManager().getDefaultDisplay();
        ll.removeAllViews();
        int maxWidth = display.getWidth() - 20;

        LinearLayout.LayoutParams params;
        LinearLayout newLL = new LinearLayout(QbankTestActivity.this);
        newLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        newLL.setGravity(Gravity.LEFT);
        newLL.setOrientation(LinearLayout.HORIZONTAL);

        int widthSoFar = 0;

        for (int i = 0; i < views.size(); i++) {
            LinearLayout LL = new LinearLayout(QbankTestActivity.this);
            LL.setOrientation(LinearLayout.HORIZONTAL);
            LL.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            LL.setLayoutParams(new ListView.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));


            //my old code
            TextView TV = new TextView(QbankTestActivity.this);
            TV.setLayoutParams(new LinearLayout.LayoutParams
                    (10, 10));
// add the textview to the parentLayout
            TV.setText("" + 11);
            TV.setId((i + 1));
            TV.setTextSize(7);
            TV.setTextColor(getResources().getColor(R.color.transparent));
            TV.setBackgroundResource(R.drawable.circleshape);


            TV.measure(0, 0);

            tvList.add(TV);
            //views[i].measure(0,0);
            params = new LinearLayout.LayoutParams(TV.getMeasuredWidth(),
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(2, 0, 2, 0);

            //params.setMargins(5, 0, 5, 0);  // YOU CAN USE THIS
            //LL.addView(TV,  params);
            LL.addView(TV, params);
            LL.measure(2, 2);
            widthSoFar += TV.getMeasuredWidth() + 4;// YOU MAY NEED TO ADD THE MARGINS
            if (widthSoFar >= maxWidth) {
                ll.addView(newLL);

                newLL = new LinearLayout(QbankTestActivity.this);
                LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                param1.setMargins(0, 5, 0, 0);
                newLL.setLayoutParams(param1);
                newLL.setOrientation(LinearLayout.HORIZONTAL);
                newLL.setGravity(Gravity.LEFT);
                params = new LinearLayout.LayoutParams(LL
                        .getMeasuredWidth(), LL.getMeasuredHeight());
                newLL.addView(LL, params);
                widthSoFar = LL.getMeasuredWidth();
            } else {
                newLL.addView(LL);
            }
        }

        ll.addView(newLL);


        populateMcqState(views);
    }

    private void populateMcqState(List<ModulesMcq> modulesMcqs) {

        for (int i = 0; i < modulesMcqs.size(); i++) {


            if (Integer.parseInt(modulesMcqs.get(i).getGivenAnswer()) != 0) {
                if (modulesMcqs.get(i).getGivenAnswer().equalsIgnoreCase(modulesMcqs.get(i).getCorrectAnswer())) {
                    changeMarkingolor(tvList.get(i), getResources().getColor(R.color.green));
                } else {
                    changeMarkingolor(tvList.get(i), getResources().getColor(R.color.red));

                }
            } else {
                changeMarkingolor(tvList.get(i), getResources().getColor(R.color.dark_gray));

            }

        }
    }


    /**
     * Making notification bar transparent
     */
    private void changeMarkingolor(TextView tv, int color) {
        GradientDrawable sd = (GradientDrawable) tv.getBackground().mutate();
        sd.setColor(color);
        sd.invalidateSelf();
    }


    @Override
    public void onBackPressed() {
        showDialog(user_id, module_id, "0", DnaPrefs.getString(QbankTestActivity.this, "subject_id"), chap_ID);
    }
}
