package com.dnamedical.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Activities.TestActivity;
import com.dnamedical.Models.test.testp.Question;
import com.dnamedical.R;
import com.squareup.picasso.Picasso;

public class QuestionFragment extends Fragment {
    int fragNum;
    Question question;
    private TextView questionTxt;
    LinearLayout answerList;
    TestActivity activity;
    CardView cardView1, cardView2, cardView3, cardView4;
    ImageView imageQuestion;
    private int totalQuestions;

    public static QuestionFragment init(Question question, int position, int size) {
        QuestionFragment truitonList = new QuestionFragment();

        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", position);
        args.putInt("totalQuestions", size);
        args.putParcelable("question", question);
        truitonList.setArguments(args);

        return truitonList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (TestActivity) getActivity();
    }

    /**
     * Retrieving this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
        totalQuestions = getArguments() != null ? getArguments().getInt("totalQuestions") : 1;
        question = getArguments() != null ? getArguments().getParcelable("question") : null;

    }

    /**
     * The Fragment's UI is a simple text view showing its instance number and
     * an associated list.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_pager_list,
                container, false);
        answerList = layoutView.findViewById(R.id.answerList);
        questionTxt = layoutView.findViewById(R.id.questionTxt);
        imageQuestion = layoutView.findViewById(R.id.question_image);
        if (!TextUtils.isEmpty(question.getTitle_image())) {
            imageQuestion.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(question.getTitle_image()).into(imageQuestion);
        }
        questionTxt.setText("Q" + (fragNum + 1) + ". " + question.getTitle());
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    View answerView = inflater.inflate(R.layout.item_answer,
                            container, false);
                    TextView answer1 = answerView.findViewById(R.id.answer);
                    cardView1 = answerView.findViewById(R.id.cardView);
                    answer1.setText(question.getOption1());
                    answerList.addView(answerView);
                    answer1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateAnswer(cardView1);
                        }
                    });
                    break;
                case 1:
                    View answerView1 = inflater.inflate(R.layout.item_answer,
                            container, false);
                    TextView answer2 = answerView1.findViewById(R.id.answer);
                    cardView2 = answerView1.findViewById(R.id.cardView);
                    answer2.setText(question.getOption2());
                    answerList.addView(answerView1);

                    answer2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateAnswer(cardView2);
                        }
                    });
                    break;
                case 2:
                    View answerView2 = inflater.inflate(R.layout.item_answer,
                            container, false);
                    TextView answer3 = answerView2.findViewById(R.id.answer);
                    cardView3 = answerView2.findViewById(R.id.cardView);
                    answer3.setText(question.getOption3());

                    answerList.addView(answerView2);
                    answer3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateAnswer(cardView3);
                        }
                    });
                    break;
                case 3:
                    View answerView4 = inflater.inflate(R.layout.item_answer,
                            container, false);
                    TextView answer4 = answerView4.findViewById(R.id.answer);
                    cardView4 = answerView4.findViewById(R.id.cardView);
                    answer4.setText(question.getOption4());
                    answerList.addView(answerView4);

                    answer4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateAnswer(cardView4);
                        }
                    });
                    break;
            }
        }
        return layoutView;
    }


    private void updateAnswer(CardView cardView) {
        cardView1.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        cardView2.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        cardView3.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        cardView4.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        if ((fragNum+1)==totalQuestions){
            activity.nextBtn.setText("SUBMIT");
        }else{
            activity.nextBtn.setText("NEXT");

        }
        cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.test_fragment_card_bacckground));
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}