package com.dnamedical.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Models.QbankSubTest.Detail;
import com.dnamedical.Models.QbankSubTest.QbankTestResponse;
import com.dnamedical.Models.answer.SubmitAnswer;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;
import com.dnamedical.views.CustomViewPager;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class QbankTestActivity extends AppCompatActivity {

    boolean isLast = false;

    /// public String quest_id="";
    public static int quest_id;
    public String user_id;
    public String is_completed;
    public String user_answer = null;
    CustomViewPager mPager;
    TextView quesionCounter;
    static int currentPosition;
    public QbankTestResponse qbankTestResponse;
    ImageView imageViewCancel;
    public ProgressBar mProgressBar;
    public CountDownTimer mCountDownTimer;
    int progress = 100;
    RelativeLayout linearBottom;
    public Button nextBtn;
    String module_id;
    private int questionStartId;
    LinearLayout answerList;
    TextView questionTestList;
    QbankTestActivity qbankTestActivity;
    int fragNum;
    List<Detail> questionDetail;
    private CardView cardView;
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    public ProgressBar progressBar;
    LinearLayout questionList, questionListDescription;
    TextView qustion, aTV, aTVPer, bTV, bTVPer, cTV, cTVPer, dTV, dTVPer, rTV, barChart;
    ImageView imgA, imgB, imgC, imgD;
    ProgressBar progressBarChart;
    WebView webView;
    LayoutInflater inflater;
    private static int questionNo;

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
            user_id = getIntent().getStringExtra("userId");
            questionNo = Integer.parseInt(getIntent().getStringExtra("questionStartId"));
        }
        qbankgetTest();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextBtn.getText().toString().equalsIgnoreCase("Complete")) {
                    Intent intent = new Intent(QbankTestActivity.this, QbankRatingActivity.class);
                    intent.putExtra("module_id", module_id);
                    intent.putExtra("userId", user_id);
                    startActivity(intent);
                    finish();
                } else {
                    showHideBottomLayout(false);
                    answerList.setVisibility(View.VISIBLE);
                    questionListDescription.setVisibility(View.GONE);

                    solveQBank(questionNo + 1);
                    questionNo++;

                }
            }
        });


    }

    private void initViews() {
        imageViewCancel = findViewById(R.id.btnCancel);
        linearBottom = findViewById(R.id.linearBottom);
        nextBtn = findViewById(R.id.nextBtn);
        answerList = findViewById(R.id.questionList);
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
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.qbanksubTestData(qmodule_id, new Callback<QbankTestResponse>() {
                @Override
                public void onResponse(Call<QbankTestResponse> call, Response<QbankTestResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.isSuccessful()) {

                        if (response.body() != null) {
                            qbankTestResponse = response.body();
                            questionDetail = qbankTestResponse.getDetails();

                            solveQBank(questionNo);
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
                public void onFailure(Call<QbankTestResponse> call, Throwable t) {
                    Toast.makeText(QbankTestActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
        }
    }

    private void solveQBank(int questinNo) {
        if (questinNo < questionDetail.size()) {

            if (questinNo == questionDetail.size() - 1) {
                isLast = true;
            }
            Detail questionDetails = questionDetail.get(questinNo);
            TextView questionText = new TextView(this);
            questionText.setPadding(15, 0, 5, 0);
            questionText.setText("Q " + (questinNo + 1) + ". " + questionDetails.getQname());

            answerList.addView(questionText);

            for (int i = 1; i < 5; i++) {
                switch (i) {
                    case 1:

                        View answer1 = inflater.inflate(R.layout.qbank_item_test, null);
                        questionTestList = answer1.findViewById(R.id.qbank_answer);
                        cardView1 = answer1.findViewById(R.id.cardView);
                        questionTestList.setText("A." + questionDetails.getOptionA());

                        answerList.addView(answer1);
                        cardView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_answer = questionDetails.getOptionA();
                                updateAnswer(questionDetails, cardView1, questionDetails.getOptionA(), questionDetails.getId(), isLast);
                            }
                        });
                        break;
                    case 2:
                        View answer2 = inflater.inflate(R.layout.qbank_item_test, null);
                        questionTestList = answer2.findViewById(R.id.qbank_answer);
                        cardView2 = answer2.findViewById(R.id.cardView);

                        questionTestList.setText("B." + questionDetails.getOptionB());
                        answerList.addView(answer2);
                        cardView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_answer = questionDetails.getOptionB();

                                updateAnswer(questionDetails, cardView2, questionDetails.getOptionB(), questionDetails.getId(), isLast);

                            }
                        });
                        break;

                    case 3:

                        View answer3 = inflater.inflate(R.layout.qbank_item_test, null);
                        questionTestList = answer3.findViewById(R.id.qbank_answer);
                        questionTestList.setText("C." + questionDetails.getOptionC());
                        cardView3 = answer3.findViewById(R.id.cardView);

                        answerList.addView(answer3);
                        cardView3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_answer = questionDetails.getOptionC();

                                updateAnswer(questionDetails, cardView3, questionDetails.getOptionC(), questionDetails.getId(), isLast);

                            }
                        });
                        break;
                    case 4:
                        View answer4 = inflater.inflate(R.layout.qbank_item_test, null);
                        questionTestList = answer4.findViewById(R.id.qbank_answer);
                        cardView4 = answer4.findViewById(R.id.cardView);

                        questionTestList.setText("D." + questionDetails.getOptionD());
                        answerList.addView(answer4);
                        cardView4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_answer = questionDetails.getOptionD();

                                updateAnswer(questionDetails, cardView4, questionDetails.getOptionD(), questionDetails.getId(), isLast);

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


    private void updateAnswer(Detail questionDetail, CardView cardView, String answervalue, String questionId, boolean isLast) {
        cardView1.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView2.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView3.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView4.setCardBackgroundColor(getResources().getColor(R.color.white));


        if (answervalue.equalsIgnoreCase(questionDetail.getOptionA())) {
            if (answervalue.equalsIgnoreCase(questionDetail.getAnswer())) {
                cardView1.setCardBackgroundColor(getResources().getColor(R.color.green));
            } else {
                cardView1.setCardBackgroundColor(getResources().getColor(R.color.red));

            }


        }
        if (answervalue.equalsIgnoreCase(questionDetail.getOptionB())) {
            if (answervalue.equalsIgnoreCase(questionDetail.getAnswer())) {
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.green));
            } else {
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.red));
            }
        }
        if (answervalue.equalsIgnoreCase(questionDetail.getOptionC())) {
            if (answervalue.equalsIgnoreCase(questionDetail.getAnswer())) {
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.green));
            } else {
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.red));
            }
        }
        if (answervalue.equalsIgnoreCase(questionDetail.getOptionD())) {
            if (answervalue.equalsIgnoreCase(questionDetail.getAnswer())) {
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.green));

            } else {
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.red));
            }
        }

        submitAnswer(questionId, isLast);
    }

    public void submitAnswer(String questionID, boolean isLast) {
        Utils.showProgressDialog(qbankTestActivity);
        RestClient.submitAnswer(String.valueOf(questionID), user_id, isLast ? "1" : "0", user_answer, new Callback<SubmitAnswer>() {
            @Override
            public void onResponse(Call<SubmitAnswer> call, Response<SubmitAnswer> response) {
                Utils.dismissProgressDialog();
                showHideBottomLayout(true);
                    updateUI(response.body());

                try {
                    if (isLast) {
                        nextBtn.setText("Complete");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                answerList.removeAllViews();
                answerList.setVisibility(GONE);
                questionListDescription.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<SubmitAnswer> call, Throwable t) {
                qbankTestActivity.showHideBottomLayout(false);
                Utils.dismissProgressDialog();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateUI(SubmitAnswer body) {

        aTV.setTextColor(Color.BLACK);
        bTV.setTextColor(Color.BLACK);
        cTV.setTextColor(Color.BLACK);
        dTV.setTextColor(Color.BLACK);
      /*  aTVPer.setTextColor(Color.BLACK);
        bTVPer.setTextColor(Color.BLACK);
        cTVPer.setTextColor(Color.BLACK);
        dTVPer.setTextColor(Color.BLACK);*/
        imgA.setVisibility(GONE);
        imgB.setVisibility(GONE);
        imgC.setVisibility(GONE);
        imgD.setVisibility(GONE);

        if (body != null) {
            qustion.setText(body.getDetails().get(0).getQname());
            aTV.setText("A." + body.getDetails().get(0).getOptionA());
            //aTVPer.setText("[" + body.getDetails().get(0).getOptionAperc() + "]");

            bTV.setText("B." + body.getDetails().get(0).getOptionB());
            //bTVPer.setText("[" + body.getDetails().get(0).getOptionBperc() + "]");

            cTV.setText("C." + body.getDetails().get(0).getOptionC());
            //cTVPer.setText("[" + body.getDetails().get(0).getOptionCperc() + "]");


            dTV.setText("D." + body.getDetails().get(0).getOptionD());
           // dTVPer.setText("[" + body.getDetails().get(0).getOptionDperc() + "]");


//            if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionA())) {
//                if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionA())) {
//                    imgA.setImageResource(R.drawable.qbank_right_answer);
//                    aTV.setTextColor(Color.GREEN);
//                }else if (!body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getAnswer())) {
//                    imgA.setImageResource(R.drawable.qbank_wrong_test_answer);
//                    aTV.setTextColor(Color.RED);
//                }
//            }
//
//
//
//            if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionB())) {
//                if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionB())) {
//                    imgB.setImageResource(R.drawable.qbank_right_answer);
//                    bTV.setTextColor(Color.GREEN);
//                }else if (!body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getAnswer())) {
//                    imgB.setImageResource(R.drawable.qbank_wrong_test_answer);
//                    bTV.setTextColor(Color.RED);
//                }
//            }
//
//
//
//            if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionC())) {
//
//                if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionC())) {
//                    imgC.setImageResource(R.drawable.qbank_right_answer);
//                    cTV.setTextColor(Color.GREEN);
//                }else if (!body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getAnswer())) {
//                    imgC.setImageResource(R.drawable.qbank_wrong_test_answer);
//                    cTV.setTextColor(Color.RED);
//                }
//            }
//
//
//
//            if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionD())) {
//                if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionD())) {
//                    imgD.setImageResource(R.drawable.qbank_right_answer);
//                    dTV.setTextColor(Color.GREEN);
//                }else if (!body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getAnswer())) {
//                    imgD.setImageResource(R.drawable.qbank_wrong_test_answer);
//                    dTV.setTextColor(Color.RED);
//                }
//            }

            if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionA())) {
                aTV.setTextColor(getColor(R.color.green));
               // aTVPer.setTextColor(Color.GREEN);
                imgA.setImageResource(R.drawable.right_answer_icon);
                imgA.setVisibility(View.VISIBLE);

                if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionA())) {
                    aTV.setTextColor(getColor(R.color.green));
                    //aTVPer.setTextColor(Color.GREEN);
                    imgA.setImageResource(R.drawable.right_answer_icon);
                    imgA.setVisibility(View.VISIBLE);
                } else {
                    if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionB())) {
                        bTV.setTextColor(Color.RED);
                       // bTVPer.setTextColor(Color.RED);
                        imgB.setImageResource(R.drawable.wrong_answer_icon);
                        imgB.setVisibility(View.VISIBLE);
                    }
                   if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionC())) {
                        cTV.setTextColor(Color.RED);
                        //cTVPer.setTextColor(Color.RED);
                        imgC.setImageResource(R.drawable.wrong_answer_icon);
                        imgC.setVisibility(View.VISIBLE);
                    }
                    if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionD())) {
                        dTV.setTextColor(Color.RED);
                        //dTVPer.setTextColor(Color.RED);
                        imgD.setImageResource(R.drawable.wrong_answer_icon);
                        imgD.setVisibility(View.VISIBLE);
                    }
                }
            }
            if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionB())) {
                bTV.setTextColor(getColor(R.color.green));
               // bTVPer.setTextColor(Color.GREEN);
                imgB.setImageResource(R.drawable.right_answer_icon);
                imgB.setVisibility(View.VISIBLE);

                if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionB())) {
                    bTV.setTextColor(getColor(R.color.green));
                   // bTVPer.setTextColor(Color.GREEN);
                    imgB.setImageResource(R.drawable.right_answer_icon);
                    imgB.setVisibility(View.VISIBLE);

                } else {
                    if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionA())) {
                        aTV.setTextColor(Color.RED);
                       // aTVPer.setTextColor(Color.RED);
                        imgA.setImageResource(R.drawable.wrong_answer_icon);
                        imgA.setVisibility(View.VISIBLE);

                    }
                    if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionC())) {
                        cTV.setTextColor(Color.RED);
                        //cTVPer.setTextColor(Color.RED);
                        imgC.setImageResource(R.drawable.wrong_answer_icon);
                        imgC.setVisibility(View.VISIBLE);

                    }
                    if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionD())) {
                        dTV.setTextColor(Color.RED);
                        //dTVPer.setTextColor(Color.RED);
                        imgD.setImageResource(R.drawable.wrong_answer_icon);
                        imgD.setVisibility(View.VISIBLE);

                    }
                }
            }
            if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionC())) {
                cTV.setTextColor(getColor(R.color.green));
               // cTVPer.setTextColor(Color.GREEN);
                imgC.setImageResource(R.drawable.right_answer_icon);
                imgC.setVisibility(View.VISIBLE);


                if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionC())) {
                    cTV.setTextColor(getColor(R.color.green));
                   // cTVPer.setTextColor(Color.GREEN);
                    imgC.setImageResource(R.drawable.right_answer_icon);
                    imgC.setVisibility(View.VISIBLE);

                } else {
                    if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionA())) {
                        aTV.setTextColor(Color.RED);
                        //aTVPer.setTextColor(Color.RED);
                        imgA.setImageResource(R.drawable.wrong_answer_icon);
                        imgA.setVisibility(View.VISIBLE);

                    }
                    if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionB())) {
                        bTV.setTextColor(Color.RED);
                        //bTVPer.setTextColor(Color.RED);
                        imgB.setImageResource(R.drawable.wrong_answer_icon);
                        imgB.setVisibility(View.VISIBLE);

                    }
                    if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionD())) {
                        dTV.setTextColor(Color.RED);
                       // dTVPer.setTextColor(Color.RED);
                        imgD.setImageResource(R.drawable.wrong_answer_icon);
                        imgD.setVisibility(View.VISIBLE);

                    }
                }
            }

            if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionD())) {
                dTV.setTextColor(getColor(R.color.green));
               // dTVPer.setTextColor(Color.GREEN);
                imgD.setImageResource(R.drawable.right_answer_icon);
                imgD.setVisibility(View.VISIBLE);

                if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionD())) {
                    dTV.setTextColor(getColor(R.color.green));
                   // dTVPer.setTextColor(Color.GREEN);
                    imgD.setImageResource(R.drawable.right_answer_icon);
                    imgD.setVisibility(View.VISIBLE);

                } else {
                    if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionA())) {
                        aTV.setTextColor(Color.RED);
                        //aTVPer.setTextColor(Color.RED);
                        imgA.setImageResource(R.drawable.wrong_answer_icon);
                        imgA.setVisibility(View.VISIBLE);

                    }
                    if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionC())) {
                        cTV.setTextColor(Color.RED);
                        //cTVPer.setTextColor(Color.RED);
                        imgC.setImageResource(R.drawable.wrong_answer_icon);
                        imgC.setVisibility(View.VISIBLE);

                    }
                    if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionB())) {
                        bTV.setTextColor(Color.RED);
                        //bTVPer.setTextColor(Color.RED);
                        imgB.setImageResource(R.drawable.wrong_answer_icon);
                        imgB.setVisibility(View.VISIBLE);

                    }
                }
            }
           /* if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionB())) {
                bTV.setTextColor(Color.GREEN);
                imgB.setImageResource(R.drawable.right_answer_icon);

            }
            if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionC())) {
                cTV.setTextColor(Color.GREEN);
                imgC.setImageResource(R.drawable.right_answer_icon);

            }
            if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionD())) {
                dTV.setTextColor(Color.GREEN);
                imgD.setImageResource(R.drawable.right_answer_icon);

            }*/
            rTV.setText(body.getDetails().get(0).getRefrence());
         //   barChart.setText(body.getDetails().get(0).getGotrightperc() + "of the people got this right");
            try {
                initComponent(body.getDetails().get(0).getDescriptionUrl());
            } catch (Exception e) {
                e.printStackTrace();
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
}
