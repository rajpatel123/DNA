package com.dnamedical.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dnamedical.Activities.ReviewresulActivity;
import com.dnamedical.Models.testReviewlistnew.QuestionList;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import static android.view.View.GONE;

public class ReviewResultFragment extends Fragment {
    ImageView question_image;
    ImageView optionAImg;
    ImageView optionBImg;
    ImageView optionCImg;
    ImageView optionDImg;
    ImageView refImage;
    ImageView explanation_image;


    TextView optionA;
    TextView optionB;
    TextView optionC;
    TextView optionD;
    TextView percentage;
    TextView explannnation;
    TextView refText;


    LinearLayout answerList;
    TextView questionTxt;
    int fragNum;
    QuestionList question;
    ReviewresulActivity activity;
    CardView explanationCard;
    WebView webView;
    ProgressBar progressBar;

    public static Fragment init(QuestionList question, int position) {
        ReviewResultFragment reviewResultFragment = new ReviewResultFragment();
        Bundle args = new Bundle();
        args.putInt("val", position);
        args.putParcelable("question", question);
        reviewResultFragment.setArguments(args);
        return reviewResultFragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ReviewresulActivity) getActivity();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
        question = getArguments() != null ? getArguments().getParcelable("question") : null;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_fragment_pager_list, container, false);
        question_image = view.findViewById(R.id.question_image);
        questionTxt = view.findViewById(R.id.questionTxt);
        explanationCard = view.findViewById(R.id.explanationCard);
        optionAImg = view.findViewById(R.id.optionAImg);
        optionBImg = view.findViewById(R.id.optionBImg);
        optionCImg = view.findViewById(R.id.optionCImg);
        optionDImg = view.findViewById(R.id.optionDImg);
        refImage = view.findViewById(R.id.refImage);
        progressBar = view.findViewById(R.id.progressBar);
        webView = view.findViewById(R.id.dataWebView);
        //explanation_image = view.findViewById(R.id.explanation_image);

        optionA = view.findViewById(R.id.optionA);
        optionB = view.findViewById(R.id.optionB);
        optionC = view.findViewById(R.id.optionC);
        optionD = view.findViewById(R.id.optionD);
        percentage = view.findViewById(R.id.percentage);
        //  explannnation = view.findViewById(R.id.explannnation);
        refText = view.findViewById(R.id.refText);
        PieChartView pieChartView = view.findViewById(R.id.chart);
        if (question != null) {
//            optionA.setText("A. " + question.getOption1() + " [" + question.getOption1Percenatge() + "%]");
//            optionB.setText("B. " + question.getOption2() + " [" + question.getOption2Percenatge() + "%]");
//            optionC.setText("C. " + question.getOption3() + " [" + question.getOption3Percenatge() + "%]");
//            optionD.setText("D. " + question.getOption4() + " [" + question.getOption4Percenatge() + "%]");
//

            optionA.setText("A. " + question.getOption1());
            optionB.setText("B. " + question.getOption2());
            optionC.setText("C. " + question.getOption3());
            optionD.setText("D. " + question.getOption4());


            questionTxt.setText("Q " + (fragNum + 1) + ". " + question.getTitle());

            if (!TextUtils.isEmpty(question.getTitleImage())) {
                Picasso.with(activity).load(question.getTitleImage()).into(question_image);
            }
            List<SliceValue> pieData = new ArrayList<>();
            pieData.add(new SliceValue(question.getPercentage(), R.color.colorPrimary));
            pieData.add(new SliceValue(100 - question.getPercentage(), R.color.colorPrimary));
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartView.setPieChartData(pieChartData);


            if (!question.getGivenAnswer().equalsIgnoreCase("0")) {
                switch (question.getGivenAnswer()) {
                    case "1":
                        if (question.getCorrectAnswer().equalsIgnoreCase("1")) {
                            optionA.setTextColor(ContextCompat.getColor(activity, R.color.green));
                            optionAImg.setImageResource(R.drawable.right_answer_icon);
                        } else {
                            optionA.setTextColor(ContextCompat.getColor(activity, R.color.black));
                            optionAImg.setImageResource(R.drawable.wrong_answer_icon);

                        }

                        optionAImg.setVisibility(View.VISIBLE);
                        break;
                    case "2":
                        if (question.getCorrectAnswer().equalsIgnoreCase("2")) {
                            optionB.setTextColor(ContextCompat.getColor(activity, R.color.green));
                            optionBImg.setImageResource(R.drawable.right_answer_icon);
                        } else {
                            optionB.setTextColor(ContextCompat.getColor(activity, R.color.black));
                            optionBImg.setImageResource(R.drawable.wrong_answer_icon);

                        }
                        optionBImg.setVisibility(View.VISIBLE);

                        break;
                    case "3":
                        if (question.getCorrectAnswer().equalsIgnoreCase("3")) {
                            optionC.setTextColor(ContextCompat.getColor(activity, R.color.green));
                            optionCImg.setImageResource(R.drawable.right_answer_icon);
                        } else {
                            optionC.setTextColor(ContextCompat.getColor(activity, R.color.black));
                            optionCImg.setImageResource(R.drawable.wrong_answer_icon);

                        }
                        optionCImg.setVisibility(View.VISIBLE);

                        break;
                    case "4":
                        if (question.getCorrectAnswer().equalsIgnoreCase("4")) {
                            optionD.setTextColor(ContextCompat.getColor(activity, R.color.green));
                            optionDImg.setImageResource(R.drawable.right_answer_icon);
                        } else {
                            optionD.setTextColor(ContextCompat.getColor(activity, R.color.black));
                            optionDImg.setImageResource(R.drawable.wrong_answer_icon);

                        }

                        optionDImg.setVisibility(View.VISIBLE);

                        break;
                }
            }

            if (!TextUtils.isEmpty(question.getExplanation())) {

                if (Utils.isInternetConnected(activity)) {
                    try {
                        explanationCard.setVisibility(View.VISIBLE);

                        loadView(question.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    explanationCard.setVisibility(GONE);

                }

            } else {
                explanationCard.setVisibility(GONE);

            }
//                PicassoImageGetter imageGetter = new PicassoImageGetter(explannnation,activity);
//                Spannable html;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                    html = (Spannable) Html.fromHtml(question.getExplanation(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
//                } else {
//                    html = (Spannable) Html.fromHtml(question.getExplanation(), imageGetter, null);
//                }
//                explannnation.setText(html);
//                explanationCard.setVisibility(View.VISIBLE);
//            } else {
//                explanationCard.setVisibility(View.GONE);
//            }


            percentage.setText(question.getPercentage() + "%     of the people got this right");

            Picasso.with(activity).load(question.getRefernce().getImage()).into(refImage);
            refText.setText(question.getRefernce().getTitle());


            switch (question.getCorrectAnswer()) {
                case "1":
                    optionA.setTextColor(ContextCompat.getColor(activity, R.color.green));
                    optionAImg.setImageResource(R.drawable.right_answer_icon);
                    optionAImg.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    optionB.setTextColor(ContextCompat.getColor(activity, R.color.green));
                    optionBImg.setImageResource(R.drawable.right_answer_icon);
                    optionBImg.setVisibility(View.VISIBLE);

                    break;
                case "3":
                    optionC.setTextColor(ContextCompat.getColor(activity, R.color.green));
                    optionCImg.setImageResource(R.drawable.right_answer_icon);
                    optionCImg.setVisibility(View.VISIBLE);

                    break;
                case "4":
                    optionD.setTextColor(ContextCompat.getColor(activity, R.color.green));
                    optionDImg.setImageResource(R.drawable.right_answer_icon);
                    optionDImg.setVisibility(View.VISIBLE);

                    break;
            }

        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void loadView(String qID) throws Exception {

        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);

        progressBar.setVisibility(View.VISIBLE);
        webView.loadUrl("http://dnamedicalapp.com/review.php?id=" + qID);


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
