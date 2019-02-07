package edu.com.medicalapp.Activities;

import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import edu.com.medicalapp.R;

public class TestActivity extends FragmentActivity {
        static final int ITEMS = 10;
        MyAdapter mAdapter;
        ViewPager mPager;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_pager);


            Button button = (Button) findViewById(R.id.first);
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    mPager.setCurrentItem(0);
                }
            });
            button = (Button) findViewById(R.id.last);
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    mPager.setCurrentItem(questionList.size() - 1);
                }
            });

            for (int i=0;i<20;i++){

                List<Answer> answerList = new ArrayList<>();

                for (int j=0;j<4;j++){
                    Answer answer = new Answer();
                    answer.setqId(i+1);
                    answer.setAnswer("Test anserr"+1);
                    answer.setAnswerImage("");
                    answer.setOptionName("");
                    answerList.add(answer);
                }

                Question question = new Question();
                question.setqId(i+1);
                question.setQuestionText("What is the right answer ?");
                question.setGetQuestionImage("");
                question.setAnswerList(answerList);

                questionList.add(question);
            }


        }


    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new MyAdapter(getSupportFragmentManager(),questionList);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        List<Question> questionList = new ArrayList<>();
            public MyAdapter(FragmentManager fragmentManager,List<Question> questionList) {
                super(fragmentManager);
                this.questionList = questionList;
            }



        @Override
            public int getCount() {
                return questionList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return TruitonListFragment.init(questionList.get(position),position);
            }
        }



    }