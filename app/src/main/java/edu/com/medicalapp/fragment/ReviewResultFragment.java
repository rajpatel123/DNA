package edu.com.medicalapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.com.medicalapp.Activities.ReviewresulActivity;
import edu.com.medicalapp.Models.ReviewResult.ReviewDetail;
import edu.com.medicalapp.R;

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
        questionImage = view.findViewById(R.id.review_question_image);
        answerList = view.findViewById(R.id.answerList1);
        questionList = view.findViewById(R.id.text_question);
        questionList.setText((fragNum + 1) + ". " + question.getQuestion());
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    View answerView = inflater.inflate(R.layout.review_list_item,
                            container, false);
                    TextView answer1 = answerView.findViewById(R.id.answertext);
                    answer1.setText("A" + ". " + question.getAnswer1());
                   /* if (question.getCurrectAnswer().equals(question.getAnswer1())) {
                        questionImage.setImageResource(R.drawable.right_answer_icon);
                    }*/
                    answerList.addView(answerView);
                    break;
                case 1:
                    View answerView1 = inflater.inflate(R.layout.review_list_item,
                            container, false);
                    TextView answer2 = answerView1.findViewById(R.id.answertext);
                    answer2.setText("B" + ". " + question.getAnswer2());
                    /*if (question.getCurrectAnswer().equals(question.getAnswer2())) {
                        questionImage.setImageResource(R.drawable.right_answer_icon);
                    }*/
                    answerList.addView(answerView1);

                    break;
                case 2:
                    View answerView2 = inflater.inflate(R.layout.review_list_item,
                            container, false);
                    TextView answer3 = answerView2.findViewById(R.id.answertext);
                    answer3.setText("C" + ". " + question.getAnswer3());
                   /* if (question.getCurrectAnswer().equals(question.getAnswer1())) {
                        questionImage.setImageResource(R.drawable.right_answer_icon);
                    }
*/
                    answerList.addView(answerView2);
                    break;
                case 3:
                    View answerView3 = inflater.inflate(R.layout.review_list_item,
                            container, false);
                    TextView answer4 = answerView3.findViewById(R.id.answertext);
                    answer4.setText("D" + ". " + question.getAnswer4());
                    answerList.addView(answerView3);
                  /*  if (question.getCurrectAnswer().equals(question.getAnswer1())) {
                        questionImage.setImageResource(R.drawable.right_answer_icon);
                    }*/
                    break;
                case 4:
                    View answerView4 = inflater.inflate(R.layout.review_list_item,
                            container, false);
                    TextView answer5 = answerView4.findViewById(R.id.answertext);

                    answer5.setText("Explanation:" + ". " + question.getExplanation());
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
