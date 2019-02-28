package edu.com.medicalapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.com.medicalapp.Activities.ReviewresulActivity;
import edu.com.medicalapp.Models.ReviewResult.ReviewDetail;
import edu.com.medicalapp.R;

public class ReviewResultFragment extends Fragment {


    LinearLayout answerList;
    TextView questionList;
    int fragNum;
    TextView textView1, textView2, textView3, textView4;
    ReviewDetail question;
    ReviewresulActivity activity;

    public static Fragment init(ReviewDetail question, int position) {
        ReviewResultFragment reviewResultFragment = new ReviewResultFragment();

        Bundle args = new Bundle();
        args.putInt("val", position);
        args.putParcelable("question", question);
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
        questionList = view.findViewById(R.id.text_question);
        questionList.setText("Q" + (fragNum + 1) + ". " + question.getQuestion());


        for (int i = 0; i < 5; i++) {

            switch (i) {
                case 0:

                    
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
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
