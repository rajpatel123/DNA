package com.dnamedical.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Activities.ReviewresulActivity;
import com.dnamedical.BuildConfig;
import com.dnamedical.Models.LogoutResponse;
import com.dnamedical.Models.ReportErrorResponse;
import com.dnamedical.Models.testReviewlistnew.QuestionList;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//import lecho.lib.hellocharts.model.PieChartData;
//import lecho.lib.hellocharts.model.SliceValue;
//import lecho.lib.hellocharts.view.PieChartView;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class ReviewResultFragment extends Fragment {
    ImageView question_image;
    TextView optionATag;
    TextView optionBTag;
    TextView optionCTag;
    TextView optionDTag;
    ImageView refImage;
    ImageView explanation_image;


    TextView optionA;
    TextView optionB;
    TextView optionC;
    TextView optionD;
    TextView percentage;
    TextView explannnation;
    TextView refText;
    TextView mcqId;


    LinearLayout answerList;
    LinearLayout referenceLL;
    TextView refHead;
    TextView questionTxt, qno;
    int fragNum;
    QuestionList question;
    ReviewresulActivity activity;
    CardView explanationCard;
    TextView errorSentTV, mcqIds;
    WebView webView, webView1, webView2, webView3, webView4, qwebView;
    ProgressBar progressBar;

    String comment, issue_tryp;

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
        qno = view.findViewById(R.id.qno);
        explanationCard = view.findViewById(R.id.explanationCard);
        optionATag = view.findViewById(R.id.A);
        optionBTag = view.findViewById(R.id.B);
        optionCTag = view.findViewById(R.id.C);
        optionDTag = view.findViewById(R.id.D);
        referenceLL = view.findViewById(R.id.referenceLL);
        refText = view.findViewById(R.id.refText);
        refHead = view.findViewById(R.id.refHead);
        refImage = view.findViewById(R.id.refImage);
        progressBar = view.findViewById(R.id.progressBar);
        webView = view.findViewById(R.id.dataWebView);
        qwebView = view.findViewById(R.id.qwebview);
        webView1 = view.findViewById(R.id.webView1);
        webView2 = view.findViewById(R.id.webView2);
        webView3 = view.findViewById(R.id.webView3);
        webView4 = view.findViewById(R.id.webView4);
        //explanation_image = view.findViewById(R.id.explanation_image);

        optionA = view.findViewById(R.id.optionA);
        optionB = view.findViewById(R.id.optionB);
        optionC = view.findViewById(R.id.optionC);
        optionD = view.findViewById(R.id.optionD);
        errorSentTV = view.findViewById(R.id.errorSend);
        mcqIds = view.findViewById(R.id.mcqId);
        percentage = view.findViewById(R.id.percentage);
        //  explannnation = view.findViewById(R.id.explannnation);
        refText = view.findViewById(R.id.refText);
        mcqId = view.findViewById(R.id.mcqId);


        if (question.getRefernce()!=null && !TextUtils.isEmpty(question.getRefernce().getTitle())){
            referenceLL.setVisibility(View.VISIBLE);
            refHead.setVisibility(View.VISIBLE);
            refText.setText(question.getRefernce().getTitle());
        }else{
            referenceLL.setVisibility(GONE);
            refHead.setVisibility(GONE);

        }
        PieChartView pieChartView = view.findViewById(R.id.chart);
        if (question != null) {
//            optionA.setText("A. " + question.getOption1() + " [" + question.getOption1Percenatge() + "%]");
//            optionB.setText("B. " + question.getOption2() + " [" + question.getOption2Percenatge() + "%]");
//            optionC.setText("C. " + question.getOption3() + " [" + question.getOption3Percenatge() + "%]");
//            optionD.setText("D. " + question.getOption4() + " [" + question.getOption4Percenatge() + "%]");
//
            mcqId.setText("MCQ ID : " + question.getQuestion_id());

            if (!TextUtils.isEmpty(question.getTitle())) {
                if (question.getTitle().contains("html")) {
                    qwebView.setVisibility(View.VISIBLE);
                    questionTxt.setVisibility(GONE);
                    qno.setVisibility(View.VISIBLE);
                    qno.setText("Q " + (fragNum + 1) + ".");
                    qwebView.loadUrl(BuildConfig.API_SERVER_IP + "reviewOption.php?id=" + question.getId() + "&Qid=5");
                } else {
                    qwebView.setVisibility(GONE);
                    qno.setVisibility(GONE);
                    questionTxt.setVisibility(View.VISIBLE);
                    Spanned sp = Html.fromHtml("Q " + (fragNum + 1) + ". " + question.getTitle());
                    questionTxt.setText(sp);
                }

            }


            if (!TextUtils.isEmpty(question.getOption1())) {
                if (question.getOption1().contains("html")) {
                    webView1.setVisibility(View.VISIBLE);
                    optionA.setVisibility(GONE);
                    webView1.loadUrl(BuildConfig.API_SERVER_IP + "reviewOption.php?id=" + question.getId() + "&option1=1");
                } else {
                    webView1.setVisibility(GONE);
                    optionA.setVisibility(View.VISIBLE);
                    Spanned optionAtxt = Html.fromHtml("" + question.getOption1());
                    optionA.setText(optionAtxt);
                }

            }

            if (!TextUtils.isEmpty(question.getOption2())) {
                if (question.getOption2().contains("html")) {
                    webView2.setVisibility(View.VISIBLE);
                    optionB.setVisibility(GONE);
                    webView2.loadUrl(BuildConfig.API_SERVER_IP + "reviewOption.php?id=" + question.getId() + "&option2=2");
                } else {
                    webView2.setVisibility(GONE);
                    optionB.setVisibility(View.VISIBLE);
                    Spanned optionAtxt = Html.fromHtml("" + question.getOption2());
                    optionB.setText(optionAtxt);
                }

            }


            if (!TextUtils.isEmpty(question.getOption3())) {
                if (question.getOption3().contains("html")) {
                    webView3.setVisibility(View.VISIBLE);
                    optionC.setVisibility(GONE);
                    webView3.loadUrl(BuildConfig.API_SERVER_IP + "reviewOption.php?id=" + question.getId() + "&option3=3");
                } else {
                    webView3.setVisibility(GONE);
                    optionC.setVisibility(View.VISIBLE);
                    Spanned optionAtxt = Html.fromHtml("" + question.getOption3());
                    optionC.setText(optionAtxt);
                }

            }


            if (!TextUtils.isEmpty(question.getOption4())) {
                if (question.getOption4().contains("html")) {
                    webView4.setVisibility(View.VISIBLE);
                    optionD.setVisibility(GONE);
                    webView4.loadUrl(BuildConfig.API_SERVER_IP + "reviewOption.php?id=" + question.getId() + "&option4=4");
                } else {
                    webView4.setVisibility(GONE);
                    optionD.setVisibility(View.VISIBLE);
                    Spanned optionAtxt = Html.fromHtml("" + question.getOption4());
                    optionD.setText(optionAtxt);
                }

            }


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
                            optionATag.setBackgroundResource(R.drawable.circle_shape_border_green);

                            // optionAImg.setImageResource(R.drawable.right_answer_icon);
                        } else {
                            optionA.setTextColor(ContextCompat.getColor(activity, R.color.black));
                            optionATag.setBackgroundResource(R.drawable.circle_shape_border_red);


                        }

                        //  optionAImg.setVisibility(View.VISIBLE);
                        //  setMenuVisibility(optionAImg);

                        break;
                    case "2":
                        if (question.getCorrectAnswer().equalsIgnoreCase("2")) {
                            optionB.setTextColor(ContextCompat.getColor(activity, R.color.green));
                            optionBTag.setBackgroundResource(R.drawable.circle_shape_border_green);

                        } else {
                            optionB.setTextColor(ContextCompat.getColor(activity, R.color.black));
                            optionBTag.setBackgroundResource(R.drawable.circle_shape_border_red);


                        }
                        // optionBImg.setVisibility(View.VISIBLE);
                        // setMenuVisibility(optionBImg);

                        break;
                    case "3":
                        if (question.getCorrectAnswer().equalsIgnoreCase("3")) {
                            optionC.setTextColor(ContextCompat.getColor(activity, R.color.green));
                            optionCTag.setBackgroundResource(R.drawable.circle_shape_border_green);

                        } else {
                            optionC.setTextColor(ContextCompat.getColor(activity, R.color.black));
                            optionCTag.setBackgroundResource(R.drawable.circle_shape_border_red);

                        }
                        // optionCImg.setVisibility(View.VISIBLE);
                        // setMenuVisibility(optionCImg);

                        break;
                    case "4":
                        if (question.getCorrectAnswer().equalsIgnoreCase("4")) {
                            optionD.setTextColor(ContextCompat.getColor(activity, R.color.green));
                            optionDTag.setBackgroundResource(R.drawable.circle_shape_border_green);

                        } else {
                            optionD.setTextColor(ContextCompat.getColor(activity, R.color.black));
                            optionDTag.setBackgroundResource(R.drawable.circle_shape_border_red);

                        }

                        // optionDImg.setVisibility(View.VISIBLE);
                        // setMenuVisibility(optionDImg);

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

           Picasso.with(activity).load(R.drawable.dnalogo).into(refImage);
//            refText.setText(question.getRefernce().getTitle());
            optionA.setTextColor(ContextCompat.getColor(activity, R.color.black));
            optionB.setTextColor(ContextCompat.getColor(activity, R.color.black));
            optionC.setTextColor(ContextCompat.getColor(activity, R.color.black));
            optionD.setTextColor(ContextCompat.getColor(activity, R.color.black));


            switch (question.getCorrectAnswer()) {
                case "1":
                    optionA.setTextColor(ContextCompat.getColor(activity, R.color.green));
                    optionATag.setBackgroundResource(R.drawable.circle_shape_border_green);

                    //optionAImg.setImageResource(R.drawable.right_answer_icon);
                    //optionAImg.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    optionB.setTextColor(ContextCompat.getColor(activity, R.color.green));
                    // optionBImg.setImageResource(R.drawable.right_answer_icon);
                    // optionBImg.setVisibility(View.VISIBLE);
                    optionBTag.setBackgroundResource(R.drawable.circle_shape_border_green);

                    break;
                case "3":
                    optionC.setTextColor(ContextCompat.getColor(activity, R.color.green));
                    optionCTag.setBackgroundResource(R.drawable.circle_shape_border_green);

                    //optionCImg.setImageResource(R.drawable.right_answer_icon);
                    // optionCImg.setVisibility(View.VISIBLE);

                    break;
                case "4":
                    optionD.setTextColor(ContextCompat.getColor(activity, R.color.green));
                    optionDTag.setBackgroundResource(R.drawable.circle_shape_border_green);

                    // optionDImg.setImageResource(R.drawable.right_answer_icon);
                    //optionDImg.setVisibility(View.VISIBLE);

                    break;
            }

        }


        errorSentTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repostError();
            }
        });

        return view;
    }

    private void setMenuVisibility(ImageView optionImg) {
//        optionAImg.setVisibility(View.INVISIBLE);
//        optionBImg.setVisibility(View.INVISIBLE);
//        optionCImg.setVisibility(View.INVISIBLE);
//        optionDImg.setVisibility(View.INVISIBLE);

        // optionImg.setVisibility(View.VISIBLE);
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

       // progressBar.setVisibility(View.VISIBLE);

        if (Constants.ISTEST) {
            webView.loadUrl(BuildConfig.API_SERVER_IP+"/review.php?id=" + qID);
        } else {
            webView.loadUrl(BuildConfig.API_SERVER_IP+"/reviewqbank.php?id=" + qID);
        }


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


    private void repostError() {

        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.report_error, null);
        dialogBuilder.setView(dialogView);

        final android.app.AlertDialog dialog = dialogBuilder.create();
        Button btn_yes = dialogView.findViewById(R.id.button_guess);
        EditText comment = dialogView.findViewById(R.id.comment);
        RadioGroup rdg = dialogView.findViewById(R.id.rdg);


        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(comment.getText().toString())) {
                    Toast.makeText(getActivity(), "Please explain issue", Toast.LENGTH_LONG).show();
                    return;
                }

                RadioButton rbtn = dialogView.findViewById(rdg.getCheckedRadioButtonId());
                switch (rbtn.getText().toString()) {
                    case "Factual Error":
                        issue_tryp = "WRONG";
                        break;
                    case "Question is confusing":
                        issue_tryp = "LESS_EXPLANATION";

                        break;
                    case "Inappropriate Explanation":
                        issue_tryp = "IN_ADEQUATE_EXPLANATION";
                        break;

                }

                postErrorData(comment.getText().toString(),issue_tryp);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void postErrorData(String comment, String issue_tryp) {

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), DnaPrefs.getString(getActivity(), Constants.LOGIN_ID));
        RequestBody testID = RequestBody.create(MediaType.parse("text/plain"), DnaPrefs.getString(getActivity(), Constants.TEST_ID));
        RequestBody comments = RequestBody.create(MediaType.parse("text/plain"), comment);
        RequestBody issueType = RequestBody.create(MediaType.parse("text/plain"), issue_tryp);
        RequestBody module;

        if (DnaPrefs.getString(getActivity(), Constants.MODULE).equalsIgnoreCase("Test")){
            module = RequestBody.create(MediaType.parse("text/plain"), "TEST");
        }else{
            module = RequestBody.create(MediaType.parse("text/plain"), "QBANK");
        }
        RequestBody questionId = RequestBody.create(MediaType.parse("text/plain"), question.getQuestion_id());


        if (Utils.isInternetConnected(getActivity())){
            RestClient.reportError(user_id, testID, questionId, comments, module, issueType, new Callback<ReportErrorResponse>() {
                @Override
                public void onResponse(Call<ReportErrorResponse> call, Response<ReportErrorResponse> response) {
                  if (response!=null && response.code()==200 && response.body().getStatus()){
                      Toast.makeText(getActivity(), "Feedback captured, you will get response on your registered email and mobile number", Toast.LENGTH_LONG).show();

                  }
                }

                @Override
                public void onFailure(Call<ReportErrorResponse> call, Throwable t) {

                }
            });
        }


    }

}
