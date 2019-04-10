package com.dnamedical.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.dnamedical.Activities.ReviewresulActivity;
import com.dnamedical.Models.ReviewResult.ReviewDetail;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;

public class ReviewResultFragment extends Fragment {


    LinearLayout answerList;
    TextView questionList;
    int fragNum;
    ReviewDetail question;
    ReviewresulActivity activity;
    ImageView questionImage;

    public static Fragment init(ReviewDetail question, int position) {
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

        answerList = view.findViewById(R.id.answerList1);
        //questionList = view.findViewById(R.id.text_question);
        //questionList.setText((fragNum + 1) + ". " + question.getQuestion());
        for (int i = 0; i < 6; i++) {
            switch (i) {

                case 0:
                    View answer = inflater.inflate(R.layout.review_question_list, container, false);
                    questionList = answer.findViewById(R.id.text_question);
                    questionList.setText((fragNum + 1) + ". " + question.getQuestion());
                    answerList.addView(answer);
                    break;

                case 1:
                    View answerView = inflater.inflate(R.layout.review_list_item,
                            container, false);
                    TextView answer1 = answerView.findViewById(R.id.answertext);
                    ImageView imageType = answerView.findViewById(R.id.review_question_image);
                    answer1.setText("A" + ". " + question.getAnswer1());
                    if (question.getCurrectAnswer().equalsIgnoreCase(question.getAnswer1())) {
                        imageType.setImageDrawable(getResources().getDrawable(R.drawable.right_answer_icon));
                        answer1.setTextColor(getResources().getColor(R.color.green));
                    }
                    if (question.getUserAnswer().equalsIgnoreCase(question.getAnswer1())) {
                        if (!question.getUserAnswer().equalsIgnoreCase(question.getCurrectAnswer())) {
                            imageType.setImageDrawable(getResources().getDrawable(R.drawable.wrong_answer_icon));
                            answer1.setTextColor(getResources().getColor(R.color.red));
                        }
                    }
                    answerList.addView(answerView);
                    break;
                case 2:
                    View answerView1 = inflater.inflate(R.layout.review_list_item,
                            container, false);
                    ImageView imageType1 = answerView1.findViewById(R.id.review_question_image);
                    TextView answer2 = answerView1.findViewById(R.id.answertext);
                    if (question.getCurrectAnswer().equalsIgnoreCase(question.getAnswer2())) {
                        imageType1.setImageDrawable(getResources().getDrawable(R.drawable.right_answer_icon));
                        answer2.setTextColor(getResources().getColor(R.color.green));
                    }
                    if (question.getUserAnswer().equalsIgnoreCase(question.getAnswer2())) {
                        if (!question.getUserAnswer().equalsIgnoreCase(question.getCurrectAnswer())) {
                            imageType1.setImageDrawable(getResources().getDrawable(R.drawable.wrong_answer_icon));
                            answer2.setTextColor(getResources().getColor(R.color.red));
                        }
                    }
                    answer2.setText("B" + ". " + question.getAnswer2());
                    answerList.addView(answerView1);
                    break;
                case 3:
                    View answerView2 = inflater.inflate(R.layout.review_list_item,
                            container, false);
                    ImageView imageType2 = answerView2.findViewById(R.id.review_question_image);
                    TextView answer3 = answerView2.findViewById(R.id.answertext);
                    answer3.setText("C" + ". " + question.getAnswer3());
                    if (question.getCurrectAnswer().equalsIgnoreCase(question.getAnswer3())) {
                        imageType2.setImageDrawable(getResources().getDrawable(R.drawable.right_answer_icon));
                        answer3.setTextColor(getResources().getColor(R.color.green));
                    }

                    if (question.getUserAnswer().equalsIgnoreCase(question.getAnswer3())) {
                        if (!question.getUserAnswer().equalsIgnoreCase(question.getCurrectAnswer())) {
                            imageType2.setImageDrawable(getResources().getDrawable(R.drawable.wrong_answer_icon));
                            answer3.setTextColor(getResources().getColor(R.color.red));
                        }
                    }
                    answerList.addView(answerView2);
                    break;
                case 4:
                    View answerView3 = inflater.inflate(R.layout.review_list_item,
                            container, false);
                    TextView answer4 = answerView3.findViewById(R.id.answertext);
                    ImageView imageType3 = answerView3.findViewById(R.id.review_question_image);
                    if (question.getCurrectAnswer().equalsIgnoreCase(question.getAnswer4())) {
                        imageType3.setImageDrawable(getResources().getDrawable(R.drawable.right_answer_icon));
                        answer4.setTextColor(getResources().getColor(R.color.green));
                    }
                    if (question.getUserAnswer().equalsIgnoreCase(question.getAnswer3())) {
                        if (!question.getUserAnswer().equalsIgnoreCase(question.getCurrectAnswer())) {
                            imageType3.setImageDrawable(getResources().getDrawable(R.drawable.wrong_answer_icon));
                            answer4.setTextColor(getResources().getColor(R.color.red));
                        }
                    }

                    answer4.setText("D" + ". " + question.getAnswer4());
                    answerList.addView(answerView3);
                    break;
                case 5:
                    View answerView4 = inflater.inflate(R.layout.review_explanation_recycler_view,
                            container, false);
                    WebView webView = answerView4.findViewById(R.id.dataWebView);
                    webView.getSettings().setJavaScriptEnabled(true);
                    Utils.showProgressDialog(getContext());
                    webView.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);

                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);

                            Utils.dismissProgressDialog();
                            webView.setVisibility(View.VISIBLE);
                            // Toast.makeText(MainActivity.this, "Page Loaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                    webView.loadUrl("http://www.dnaftb.org/");
                    answerList.addView(answerView4);
                    break;
            }
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
