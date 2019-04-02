package edu.com.medicalapp.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.com.medicalapp.Activities.QbankTestActivity;
import edu.com.medicalapp.Models.QbankSubTest.Detail;
import edu.com.medicalapp.Models.answer.SubmitAnswer;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class QbankTestFragment extends Fragment {
    LinearLayout answerList;
    TextView questionTestList;
    QbankTestActivity qbankTestActivity;
    int fragNum;
    Detail questionDetail;
    private CardView cardView;
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    public ProgressBar progressBar;
    boolean isLast;
    LinearLayout questionList, questionListDescription;
    TextView qustion, aTV, aTVPer, bTV, bTVPer, cTV, cTVPer, dTV, dTVPer, rTV,barChart;
    ImageView imgA, imgB, imgC, imgD;
    ProgressBar progressBarChart;
    WebView webView;


    public static Fragment init(Detail qbankTest, int position) {
        QbankTestFragment qbankTestFragment = new QbankTestFragment();
        Bundle args = new Bundle();
        args.putInt("val", position);
        args.putParcelable("question", qbankTest);
        qbankTestFragment.setArguments(args);
        return qbankTestFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        qbankTestActivity = (QbankTestActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
        questionDetail = getArguments() != null ? getArguments().getParcelable("question") : null;

        qbankTestActivity.setFragment(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qbanktest, container, false);
        answerList = view.findViewById(R.id.questionList);
        progressBarChart=view.findViewById(R.id.progress_bar_chart);
        questionListDescription = view.findViewById(R.id.questionListDescription);

        progressBar = view.findViewById(R.id.progressBar);

        qustion = view.findViewById(R.id.qtext);

        imgA = view.findViewById(R.id.imga);
        imgB = view.findViewById(R.id.imgb);
        imgC = view.findViewById(R.id.imgc);
        imgD = view.findViewById(R.id.imgd);

        aTV = view.findViewById(R.id.optionA);
        aTVPer = view.findViewById(R.id.optionAPer);

        bTV = view.findViewById(R.id.optionB);
        bTVPer = view.findViewById(R.id.optionBPer);

        cTV = view.findViewById(R.id.optionC);
        cTVPer = view.findViewById(R.id.optionCPer);

        dTV = view.findViewById(R.id.optionD);
        dTVPer = view.findViewById(R.id.optionDPer);
        barChart=view.findViewById(R.id.bar_chart_percentage);
        rTV = view.findViewById(R.id.reference);

        webView = view.findViewById(R.id.webView);

        View answer = inflater.inflate(R.layout.review_question_list, container, false);
        questionTestList = answer.findViewById(R.id.text_question);
        questionTestList.setText("Q" + (fragNum ) + ". " + questionDetail.getQname());
        qbankTestActivity.quest_id = questionDetail.getId();
        answerList.addView(answer);


        if (qbankTestActivity.qbankTestResponse.getDetails()
                .get(qbankTestActivity.qbankTestResponse.getDetails().size() - 1)
                .getId().equalsIgnoreCase(questionDetail.getId())) {
            qbankTestActivity.is_completed = "1";
        }
        for (int i = 1; i < 5; i++) {
            switch (i) {

                case 1:

                    View answer1 = inflater.inflate(R.layout.qbank_item_test, container, false);
                    questionTestList = answer1.findViewById(R.id.qbank_answer);
                    cardView1 = answer1.findViewById(R.id.cardView);
                    questionTestList.setText("A." + questionDetail.getOptionA());
                    qbankTestActivity.user_answer = questionDetail.getOptionA();
                    answerList.addView(answer1);
                    cardView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateAnswer(cardView1, questionDetail.getOptionA());
                        }
                    });
                    break;
                case 2:
                    View answer2 = inflater.inflate(R.layout.qbank_item_test, container, false);
                    questionTestList = answer2.findViewById(R.id.qbank_answer);
                    cardView2 = answer2.findViewById(R.id.cardView);
                    qbankTestActivity.user_answer = questionDetail.getOptionB();

                    questionTestList.setText("B." + questionDetail.getOptionB());
                    answerList.addView(answer2);
                    cardView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateAnswer(cardView2, questionDetail.getOptionB());

                        }
                    });
                    break;

                case 3:

                    View answer3 = inflater.inflate(R.layout.qbank_item_test, container, false);
                    questionTestList = answer3.findViewById(R.id.qbank_answer);
                    questionTestList.setText("C." + questionDetail.getOptionC());
                    cardView3 = answer3.findViewById(R.id.cardView);
                    qbankTestActivity.user_answer = questionDetail.getOptionC();

                    answerList.addView(answer3);
                    cardView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateAnswer(cardView3, questionDetail.getOptionC());

                        }
                    });
                    break;
                case 4:
                    View answer4 = inflater.inflate(R.layout.qbank_item_test, container, false);
                    questionTestList = answer4.findViewById(R.id.qbank_answer);
                    cardView4 = answer4.findViewById(R.id.cardView);
                    qbankTestActivity.user_answer = questionDetail.getOptionD();

                    questionTestList.setText("D." + questionDetail.getOptionD());
                    answerList.addView(answer4);
                    cardView4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateAnswer(cardView4, questionDetail.getOptionD());

                        }
                    });
                    break;
            }
        }
        return view;
    }

    private void updateAnswer(CardView cardView, String answervalue) {
        cardView1.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        cardView2.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        cardView3.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        cardView4.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));


        if (answervalue.equalsIgnoreCase(questionDetail.getOptionA())) {
            if (answervalue.equalsIgnoreCase(questionDetail.getAnswer())) {
                cardView1.setCardBackgroundColor(getContext().getResources().getColor(R.color.green));
            } else {
                cardView1.setCardBackgroundColor(getContext().getResources().getColor(R.color.red));

            }


        }
        if (answervalue.equalsIgnoreCase(questionDetail.getOptionB())) {
            if (answervalue.equalsIgnoreCase(questionDetail.getAnswer())) {
                cardView2.setCardBackgroundColor(getContext().getResources().getColor(R.color.green));
            } else {
                cardView2.setCardBackgroundColor(getContext().getResources().getColor(R.color.red));

            }

        }
        if (answervalue.equalsIgnoreCase(questionDetail.getOptionC())) {
            if (answervalue.equalsIgnoreCase(questionDetail.getAnswer())) {
                cardView3.setCardBackgroundColor(getContext().getResources().getColor(R.color.green));

            } else {
                cardView3.setCardBackgroundColor(getContext().getResources().getColor(R.color.red));

            }
        }
        if (answervalue.equalsIgnoreCase(questionDetail.getOptionD())) {
            if (answervalue.equalsIgnoreCase(questionDetail.getAnswer())) {
                cardView4.setCardBackgroundColor(getContext().getResources().getColor(R.color.green));

            } else {
                cardView4.setCardBackgroundColor(getContext().getResources().getColor(R.color.red));
            }
        }
        submitAnswer();

    }

    public void submitAnswer() {
        qbankTestActivity.mCountDownTimer.cancel();
        qbankTestActivity.mProgressBar.setVisibility(View.GONE);
        Utils.showProgressDialog(qbankTestActivity);
        RestClient.submitAnswer(qbankTestActivity.quest_id, qbankTestActivity.user_id, qbankTestActivity.is_completed, qbankTestActivity.user_answer, new Callback<SubmitAnswer>() {
            @Override
            public void onResponse(Call<SubmitAnswer> call, Response<SubmitAnswer> response) {
                Utils.dismissProgressDialog();
                qbankTestActivity.showHideBottomLayout(true);
                updateUI(response.body());
                try {
                    if (qbankTestActivity.is_completed.equalsIgnoreCase("1")) {
                        qbankTestActivity.nextBtn.setText("Complete");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    private void updateUI(SubmitAnswer body) {
        if (body != null) {
            qustion.setText(body.getDetails().get(0).getQname());
            aTV.setText("A." + body.getDetails().get(0).getOptionA());
            aTVPer.setText("[" + body.getDetails().get(0).getOptionAperc() + "]");
            if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionA())) {
                imgA.setImageResource(R.drawable.qbank_right_answer);
                aTV.setTextColor(Color.GREEN);
            }
            if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionA())) {
                if (!body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getAnswer())) {
                    imgA.setImageResource(R.drawable.qbank_wrong_test_answer);
                    aTV.setTextColor(Color.RED);
                }
            }

            bTV.setText("B." + body.getDetails().get(0).getOptionB());
            bTVPer.setText("[" + body.getDetails().get(0).getOptionBperc() + "]");
            if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionB())) {
                imgB.setImageResource(R.drawable.qbank_right_answer);
                bTV.setTextColor(Color.GREEN);
            }
            if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionB())) {
                if (!body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getAnswer())) {
                    imgB.setImageResource(R.drawable.qbank_wrong_test_answer);
                    bTV.setTextColor(Color.RED);
                }
            }

            cTV.setText("C." + body.getDetails().get(0).getOptionC());
            cTVPer.setText("[" + body.getDetails().get(0).getOptionCperc() + "]");
            if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionC())) {
                imgC.setImageResource(R.drawable.qbank_right_answer);
                cTV.setTextColor(Color.GREEN);
            }
            if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionC())) {
                if (!body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getAnswer())) {
                    imgC.setImageResource(R.drawable.qbank_wrong_test_answer);
                    cTV.setTextColor(Color.RED);
                }
            }
            dTV.setText("D." + body.getDetails().get(0).getOptionD());
            dTVPer.setText("[" + body.getDetails().get(0).getOptionDperc() + "]");
            if (body.getDetails().get(0).getAnswer().equalsIgnoreCase(body.getDetails().get(0).getOptionD())) {
                imgD.setImageResource(R.drawable.qbank_right_answer);
                dTV.setTextColor(Color.GREEN);
            }
            if (body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getOptionD())) {
                if (!body.getDetails().get(0).getUseranswer().equalsIgnoreCase(body.getDetails().get(0).getAnswer())) {
                    imgD.setImageResource(R.drawable.qbank_wrong_test_answer);
                    dTV.setTextColor(Color.RED);
                }
            }


            rTV.setText(body.getDetails().get(0).getRefrence());
            barChart.setText(body.getDetails().get(0).getGotrightperc()+"of the people got this right");
            try {
                initComponent(body.getDetails().get(0).getDescriptionUrl());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private void initComponent(String url) throws Exception {

        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        progressBar.setVisibility(View.VISIBLE);
        webView.loadUrl(url);


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

}




