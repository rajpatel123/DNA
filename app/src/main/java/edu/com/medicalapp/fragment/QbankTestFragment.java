package edu.com.medicalapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import edu.com.medicalapp.Activities.QbankTestActivity;
import edu.com.medicalapp.Models.QbankTest.QbankTest;
import edu.com.medicalapp.Models.ReviewResult.ReviewResult;
import edu.com.medicalapp.Models.qbank.Detail;
import edu.com.medicalapp.Models.qbank.QbankResponse;
import edu.com.medicalapp.R;

public class QbankTestFragment extends Fragment {
    LinearLayout answerList;
    TextView questionTestList;
    QbankTestActivity qbankTestActivity;
    int fragNum;
    QbankTest question;


    public static Fragment init(QbankTest qbankTest, int position) {
        QbankTestFragment qbankTestFragment=new QbankTestFragment();
        Bundle args=new Bundle();
        args.putInt("val", position);
        args.putParcelable("question",qbankTest);
        qbankTestFragment.setArguments(args);
        return qbankTestFragment;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      qbankTestActivity=(QbankTestActivity)getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
        question = getArguments() != null ? getArguments().getParcelable("question") : null;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qbanktest, container, false);
        answerList=view.findViewById(R.id.questionList);

         for(int i=0;i<5;i++)
         {
             switch (i)

             {
                 case 0:
                     View answer = inflater.inflate(R.layout.review_question_list, container, false);
                     questionTestList=answer.findViewById(R.id.text_question);
                     questionTestList.setText((fragNum+1)+". "+question.getQuestion());
                     answerList.addView(answer);
                     break;

                 case 1:

                     View answer1 = inflater.inflate(R.layout.qbank_item_test, container, false);
                     questionTestList=answer1.findViewById(R.id.qbank_answer);
                     questionTestList.setText("A." +question.getAnswer1());
                     answerList.addView(answer1);
                     break;
                 case 2:
                     View answer2 = inflater.inflate(R.layout.qbank_item_test, container, false);
                     questionTestList=answer2.findViewById(R.id.qbank_answer);
                     questionTestList.setText("B." +question.getAnswer2());
                     answerList.addView(answer2);
                     break;

                 case 3:

                     View answer3 = inflater.inflate(R.layout.qbank_item_test, container, false);
                     questionTestList=answer3.findViewById(R.id.qbank_answer);
                     questionTestList.setText("C." +question.getAnswer3());
                     answerList.addView(answer3);
                     break;
                 case 4:
                     View answer4 = inflater.inflate(R.layout.qbank_item_test, container, false);
                     questionTestList=answer4.findViewById(R.id.qbank_answer);
                     questionTestList.setText("D." +question.getAnswer4());
                     answerList.addView(answer4);
                     break;



             }

         }

































        return view;
    }
}
