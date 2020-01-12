package com.dnamedical.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.dnamedical.views.CustomViewPager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class QbankTestActivity extends AppCompatActivity {

    private   TextView tv ;
    boolean isLast = false;

    public static String quest_id;
    String module_id;

    public String user_id;
    public String is_completed;
    public String user_answer = null;
    CustomViewPager mPager;
    TextView quesionCounter;
    static int currentPosition;
    public MCQQuestionList qbankTestResponse;
    ImageView imageViewCancel;
    public ProgressBar mProgressBar;
    public CountDownTimer mCountDownTimer;
    int progress = 100;
    LinearLayout linearBottom;
    public Button nextBtn;

    private int getQuestionNo;
    private int questionStartId;
    LinearLayout answerList;
    TextView questionTestList;
    QbankTestActivity qbankTestActivity;
    int fragNum;
    List<ModulesMcq> questionDetail;
    private CardView cardView;
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    public ProgressBar progressBar;
    LinearLayout questionList, questionListDescription,questionmarking;
    TextView qustion, aTV, aTVPer, bTV, bTVPer, cTV, cTVPer, dTV, dTVPer, rTV, barChart;
    ImageView imgA, imgB, imgC, imgD;
    ProgressBar progressBarChart;
    WebView webView;
    LayoutInflater inflater;
    private int questionNo;
    private ImageView bookmark;
    private String chapter_Id;
    private String chap_ID;
    private ModulesMcq questionDetails;
    private SeekBar timerProgressBar;
    CountDownTimer countDownTimer;
    private TextView qbank_answer1,qbank_answer2,qbank_answer3,qbank_answer4;
    private int questionTime =10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_test);
        //inalalze View
        initViews();
        inflater = LayoutInflater.from(this);
        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().hasExtra("qmodule_id")) {
            module_id = getIntent().getStringExtra("qmodule_id");
            user_id = DnaPrefs.getString(QbankTestActivity.this, Constants.LOGIN_ID);
            chap_ID = DnaPrefs.getString(QbankTestActivity.this, "chap_ID");
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

                    solveQBank(questionNo + 1);
                    questionNo++;

                }
            }
        });


    }




    private void initViews() {

        timerProgressBar = findViewById(R.id.progressTimer);
        imageViewCancel = findViewById(R.id.btnCancel);
        linearBottom = findViewById(R.id.linearBottom);
        questionmarking = findViewById(R.id.questionmarking);
        nextBtn = findViewById(R.id.nextBtn);
        answerList = findViewById(R.id.questionList);
        bookmark = findViewById(R.id.bookmark);
        progressBarChart = findViewById(R.id.progress_bar_chart);
        questionListDescription = findViewById(R.id.questionListDescription);
        qbankTestActivity = this;
        progressBar = findViewById(R.id.progressBar);

        qustion = findViewById(R.id.qtext);

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

//                if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(module_id)
//                        && quest_id>0) {
//                    RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
//                    RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), module_id);
//                    RequestBody q_id = RequestBody.create(MediaType.parse("text/plain"), ""+quest_id);
//                    RequestBody q_id = RequestBody.create(MediaType.parse("text/plain"), ""+quest_id);
//                    RequestBody remove_bookmark = null;
//                    if (qustionDetails.getData().getQuestionList().get(questionIndex).isBookMarked()) {
//                        remove_bookmark = RequestBody.create(MediaType.parse("text/plain"), "1");
//                        qustionDetails.getData().getQuestionList().get(questionIndex).setBookMarked(false);
//
//                        Log.d("BookMark", "" + 1);
//                        star.setBackgroundResource(R.drawable.star_grey);
//
//                    } else {
//                        remove_bookmark = RequestBody.create(MediaType.parse("text/plain"), "0");
//                        qustionDetails.getData().getQuestionList().get(questionIndex).setBookMarked(true);
//                        star.setBackgroundResource(R.drawable.star_colored);
//                        Log.d("BookMark", "" + 0);
//
//
//                    }
//
//                    Utils.showProgressDialog(TestV1Activity.this);
//                    RestClient.bookMarkQuestion(userId, testID, q_id, remove_bookmark, new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            Utils.dismissProgressDialog();
//                            if (response != null && response.code() == 200) {
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            Utils.dismissProgressDialog();
//                        }
//                    });
//                }

            }
        });


        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(user_id, module_id, "0", DnaPrefs.getString(QbankTestActivity.this, "subject_id"), chap_ID);
            }
        });


        countDownTimer = new CountDownTimer(10, 1000) {

            public void onTick(long millis) {
                timerProgressBar.setProgress((questionTime--));

            }

            public void onFinish() {
                timerProgressBar.setProgress((0));
                user_answer=questionDetails.getCorrectAnswer();
                submitAnswer(questionDetails);
                timerProgressBar.setMax(10);
                questionTime=10;
            }

        };


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


                                //populateText(questionmarking,questionDetail);
                                solveQBank(questionNo);
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
            if (countDownTimer!=null){
                countDownTimer.start();
            }

            questionDetails = questionDetail.get(questinNo);
            TextView questionText = new TextView(this);
            questionText.setPadding(15, 0, 5, 0);
            questionText.setText("Q " + (questinNo + 1) + ". " + questionDetails.getMcqTitle());
            questionText.setTypeface(questionText.getTypeface(), Typeface.BOLD);
            questionText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            quest_id = "" + questionDetails.getId();
            chapter_Id = "" + questionDetails.getCategoryId();
            answerList.removeAllViews();
            answerList.addView(questionText);

            for (int i = 1; i < 5; i++) {

                switch (i) {
                    case 1:

                        View answer1 = inflater.inflate(R.layout.qbank_item_test, null);
                        qbank_answer1 = answer1.findViewById(R.id.qbank_answer);
                        cardView1 = answer1.findViewById(R.id.cardView);
                        qbank_answer1.setText("A." + questionDetails.getOption1());

                        answerList.addView(answer1);
                        cardView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_answer = "1";

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateAnswer(questionDetails, cardView1, "1", questionDetails.getId(), isLast);
                                    }
                                }, 1 * 1000);

                            }
                        });
                        break;
                    case 2:
                        View answer2 = inflater.inflate(R.layout.qbank_item_test, null);
                        qbank_answer2 = answer2.findViewById(R.id.qbank_answer);
                        cardView2 = answer2.findViewById(R.id.cardView);

                        qbank_answer2.setText("B." + questionDetails.getOption2());
                        answerList.addView(answer2);
                        cardView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                user_answer = "2";
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateAnswer(questionDetails, cardView2, "2", questionDetails.getId(), isLast);
                                    }
                                }, 1 * 1000);

                            }
                        });
                        break;

                    case 3:

                        View answer3 = inflater.inflate(R.layout.qbank_item_test, null);
                        qbank_answer3 = answer3.findViewById(R.id.qbank_answer);
                        qbank_answer3.setText("C." + questionDetails.getOption3());
                        cardView3 = answer3.findViewById(R.id.cardView);

                        answerList.addView(answer3);
                        cardView3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_answer = "3";

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateAnswer(questionDetails, cardView3, "3", questionDetails.getId(), isLast);
                                    }
                                }, 1 * 1000);
                            }
                        });
                        break;
                    case 4:
                        View answer4 = inflater.inflate(R.layout.qbank_item_test, null);
                        qbank_answer4 = answer4.findViewById(R.id.qbank_answer);
                        cardView4 = answer4.findViewById(R.id.cardView);

                        qbank_answer4.setText("D." + questionDetails.getOption4());
                        answerList.addView(answer4);
                        cardView4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_answer = "4";
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateAnswer(questionDetails, cardView4, "4", questionDetails.getId(), isLast);
                                    }
                                }, 1 * 1000);


                            }
                        });
                        break;
                }
            }
        }

    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int newPosition) {
            currentPosition = newPosition;
            //quesionCounter.setText((newPosition + 1) + " of " + reviewResult.getDetail().size());

        }

        @Override
        public void onPageScrolled(int newPosition, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    };


    private void updateAnswer(ModulesMcq questionDetail, CardView cardView, String answervalue, String questionId, boolean isLast) {
        cardView1.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView2.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView3.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView4.setCardBackgroundColor(getResources().getColor(R.color.white));


        if (answervalue.equalsIgnoreCase("1")) {
            if (answervalue.equalsIgnoreCase(questionDetail.getCorrectAnswer())) {
                cardView1.setCardBackgroundColor(getResources().getColor(R.color.green));
            } else {
                cardView1.setCardBackgroundColor(getResources().getColor(R.color.red));

            }
            qbank_answer1.setTextColor(getResources().getColor(R.color.white));


        }
        if (answervalue.equalsIgnoreCase("2")) {
            if (answervalue.equalsIgnoreCase(questionDetail.getCorrectAnswer())) {
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.green));
            } else {
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.red));
            }
            qbank_answer2.setTextColor(getResources().getColor(R.color.white));

        }
        if (answervalue.equalsIgnoreCase("3")) {
            if (answervalue.equalsIgnoreCase(questionDetail.getCorrectAnswer())) {
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.green));
            } else {
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.red));
            }
            qbank_answer3.setTextColor(getResources().getColor(R.color.white));

        }
        if (answervalue.equalsIgnoreCase("4")) {
            if (answervalue.equalsIgnoreCase(questionDetail.getCorrectAnswer())) {
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.green));

            } else {
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.red));
            }
            qbank_answer4.setTextColor(getResources().getColor(R.color.white));


        }


        switch (questionDetail.getCorrectAnswer()) {
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


        submitAnswer(questionDetail);
    }

    public void submitAnswer(ModulesMcq modulesMcq) {
        Utils.showProgressDialog(qbankTestActivity);

        RequestBody mcq_id = RequestBody.create(MediaType.parse("text/plain"), modulesMcq.getId());

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);

        RequestBody givenAnswer = RequestBody.create(MediaType.parse("text/plain"), user_answer);

        RequestBody moduleID = RequestBody.create(MediaType.parse("text/plain"), module_id);


        Log.d("Data", "UserId ->" + user_id + " Questions Id ->" + modulesMcq.getId() + " Given Answer ->" + user_answer + "  Module  " + module_id);

        RestClient.submitAnswer(userId, mcq_id, moduleID, givenAnswer, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utils.dismissProgressDialog();
                showHideBottomLayout(true);
                updateUI(modulesMcq.getId(), user_answer, modulesMcq);

                try {
                    if (questionNo == questionDetail.size() - 1) {
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
                Utils.dismissProgressDialog();
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


            qustion.setText("Q " + (questionNo + 1) + ". " + modulesMcq.getMcqTitle());

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


    private void initComponent(String url) throws Exception {

        webView.setWebViewClient(new MyWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        progressBar.setVisibility(View.VISIBLE);
        webView.loadUrl(url);


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
        webView.loadUrl("http://13.234.161.7/review.php?id=" + qID);


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
        builder.setTitle("Submit");
        builder.setMessage("Are you sure you want to pause MCQ?");
        String positiveText = "YES";
        builder.setPositiveButton(positiveText, (dialog, which) -> {
            dialog.dismiss();

            //
            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
            RequestBody moduleId = RequestBody.create(MediaType.parse("text/plain"), module_id);
            RequestBody complete = RequestBody.create(MediaType.parse("text/plain"), complete_status);
            RequestBody subID = RequestBody.create(MediaType.parse("text/plain"), subject_id);
            RequestBody chapId = RequestBody.create(MediaType.parse("text/plain"), chapter_id);


            RestClient.completeMCQ(userId, moduleId, complete, subID, chapId, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Intent intent = new Intent(QbankTestActivity.this, QbankRatingActivity.class);
                    intent.putExtra("module_id", module_id);
                    intent.putExtra("userId", user_id);
                    startActivity(intent);
                    finish();

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



    private void populateText(LinearLayout ll, List<ModulesMcq> views ) {
        Display display = getWindowManager().getDefaultDisplay();
        ll.removeAllViews();
        int maxWidth = display.getWidth() - 20;

        LinearLayout.LayoutParams params;
        LinearLayout newLL = new LinearLayout(this);
        newLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        newLL.setGravity(Gravity.LEFT);
        newLL.setOrientation(LinearLayout.HORIZONTAL);

        int widthSoFar = 0;

        for (int i = 0 ; i < views.size() ; i++ ){
            LinearLayout LL = new LinearLayout(this);
            LL.setOrientation(LinearLayout.HORIZONTAL);
            LL.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
            LL.setLayoutParams(new ListView.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            //my old code
            tv = new TextView(QbankTestActivity.this);
            tv.measure(10, 10);
            //tv.setText("hgdjgjgcjsdg");
            tv.setBackgroundResource(R.drawable.circleshape);
            //views[i].measure(0,0);
            params = new LinearLayout.LayoutParams(tv.getMeasuredWidth(),
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            //params.setMargins(5, 0, 5, 0);  // YOU CAN USE THIS
            //LL.addView(TV, params);
            LL.addView(tv, params);
            LL.measure(500, 200);
            widthSoFar += tv.getMeasuredWidth();// YOU MAY NEED TO ADD THE MARGINS
            if (widthSoFar >= maxWidth) {
                ll.addView(newLL);

                newLL = new LinearLayout(this);
                newLL.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
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
    }


}
