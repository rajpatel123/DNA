package com.dnamedical.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Activities.TestActivity;
import com.dnamedical.Models.Detail;
import com.dnamedical.R;

public class TruitonListFragment extends Fragment {
    int fragNum;
    Detail question;
    private TextView questionTxt;
    LinearLayout answerList;
    TestActivity activity;
    CardView cardView1, cardView2, cardView3, cardView4;

    public static TruitonListFragment init(Detail question, int position) {
        TruitonListFragment truitonList = new TruitonListFragment();

        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", position);
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
        questionTxt.setText("Q" + (fragNum + 1) + ". " + question.getQuestion());
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    View answerView = inflater.inflate(R.layout.item_answer,
                            container, false);
                    TextView answer1 = answerView.findViewById(R.id.answer);
                    cardView1 = answerView.findViewById(R.id.cardView);
                    answer1.setText(question.getAnswer1());
                    updatePreviousSelection(question.getQid(),cardView1);
                    answerList.addView(answerView);
                    answer1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (activity != null) {
                                //activity.hideShowSkip(true);
                            }
                            answer1.setText(question.getAnswer1());
                            if (answer1.getText().toString().equalsIgnoreCase(question.getCurrectAnswer())) {
                                activity.correctAnswerList.put(question.getQid(), answer1.getText().toString());
                                if (activity.wrongAnswerList.containsKey(question.getQid()))
                                    activity.wrongAnswerList.remove(question.getQid());

                            } else {
                                activity.wrongAnswerList.put(question.getQid(), "a");
                                if (activity.correctAnswerList.containsKey(question.getQid())) {
                                    activity.correctAnswerList.remove(question.getQid());
                                }

                            }
                            updateAnswer(cardView1);
                        }
                    });
                    break;
                case 1:
                    View answerView1 = inflater.inflate(R.layout.item_answer,
                            container, false);
                    TextView answer2 = answerView1.findViewById(R.id.answer);
                    cardView2 = answerView1.findViewById(R.id.cardView);
                    answer2.setText(question.getAnswer2());
                    answerList.addView(answerView1);
                    updatePreviousSelection(question.getQid(),cardView2);

                    answer2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (activity != null) {
                                //activity.hideShowSkip(true);
                            }
                            answer2.setText(question.getAnswer2());
                            if (answer2.getText().toString().equalsIgnoreCase(question.getCurrectAnswer())) {
                                activity.correctAnswerList.put(question.getQid(), answer2.getText().toString());
                                if (activity.wrongAnswerList.containsKey(question.getQid()))
                                    activity.wrongAnswerList.remove(question.getQid());


                            } else {
                                activity.wrongAnswerList.put(question.getQid(), "b");
                                if (activity.correctAnswerList.containsKey(question.getQid())) {
                                    activity.correctAnswerList.remove(question.getQid());
                                }

                            }
                            updateAnswer(cardView2);
                        }
                    });
                    break;
                case 2:
                    View answerView2 = inflater.inflate(R.layout.item_answer,
                            container, false);
                    TextView answer3 = answerView2.findViewById(R.id.answer);
                    cardView3 = answerView2.findViewById(R.id.cardView);
                    answer3.setText(question.getAnswer3());
                    updatePreviousSelection(question.getQid(),cardView3);

                    answerList.addView(answerView2);
                    answer3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (activity != null) {
                               // activity.hideShowSkip(true);
                            }
                            answer3.setText(question.getAnswer3());
                            if (answer3.getText().toString().equalsIgnoreCase(question.getCurrectAnswer())) {
                                activity.correctAnswerList.put(question.getQid(), answer3.getText().toString());
                                if (activity.wrongAnswerList.containsKey(question.getQid()))
                                    activity.wrongAnswerList.remove(question.getQid());

                            } else {
                                activity.wrongAnswerList.put(question.getQid(), "c");
                                if (activity.correctAnswerList.containsKey(question.getQid())) {
                                    activity.correctAnswerList.remove(question.getQid());
                                }

                            }
                            updateAnswer(cardView3);
                        }
                    });
                    break;
                case 3:
                    View answerView4 = inflater.inflate(R.layout.item_answer,
                            container, false);
                    TextView answer4 = answerView4.findViewById(R.id.answer);
                    cardView4 = answerView4.findViewById(R.id.cardView);
                    answer4.setText(question.getAnswer4());
                    answerList.addView(answerView4);
                    updatePreviousSelection(question.getQid(),cardView4);

                    answer4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (activity != null) {
                                //activity.hideShowSkip(true);
                            }
                            answer4.setText(question.getAnswer4());
                            if (answer4.getText().toString().equalsIgnoreCase(question.getCurrectAnswer())) {
                                activity.correctAnswerList.put(question.getQid(), answer4.getText().toString());
                                if (activity.wrongAnswerList.containsKey(question.getQid()))
                                    activity.wrongAnswerList.remove(question.getQid());

                            } else {
                                activity.wrongAnswerList.put(question.getQid(), "d");
                                if (activity.correctAnswerList.containsKey(question.getQid())) {
                                    activity.correctAnswerList.remove(question.getQid());
                                }

                            }
                            updateAnswer(cardView4);
                        }
                    });
                    break;
            }
        }
        return layoutView;
    }

    private void updatePreviousSelection(String qid, CardView cardView1) {

        if (activity.correctAnswerList.keySet().contains(qid) || activity.wrongAnswerList.keySet().contains(qid)){
            //activity.hideShowSkip(true);
            updateAnswer(cardView1);
        }else{
            //activity.hideShowSkip(false);

        }
    }

    private void updateAnswer(CardView cardView) {
        cardView1.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        cardView2.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        cardView3.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        cardView4.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.test_fragment_card_bacckground));
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}