package com.dnamedical.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnamedical.DNAApplication;
import com.dnamedical.Models.testReviewlistnew.QuestionList;
import com.dnamedical.R;
import com.dnamedical.fragment.ReviewResultFragment;

import java.util.ArrayList;
import java.util.List;


public class ReviewresulActivity extends FragmentActivity {

    MyAdapter mAdapter;
    ViewPager mPager;
    TextView quesionCounter;
    TextView timer;
    Toolbar toolbar;
    int currentPosition;
    String userId;
    TextView leftTest, rightTest;
    static int itemPosition;
    Button back_button;
    private List<QuestionList> reviewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewresul);

        //  getReviewTest();
        quesionCounter = findViewById(R.id.question_number);

        Intent intent = getIntent();
        reviewResult = DNAApplication.getInstance().getQuestionList();
        itemPosition = intent.getIntExtra("position", 0);
        if (reviewResult != null) {
            mAdapter = new MyAdapter(getSupportFragmentManager(), reviewResult, quesionCounter);
            mPager = findViewById(R.id.pager2);
            mPager.addOnPageChangeListener(pageChangeListener);
            mPager.setAdapter(mAdapter);
            mPager.setCurrentItem(itemPosition);

        }


        leftTest = findViewById(R.id.left_arrow);
        leftTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition > 0) {
                    if (itemPosition < 1) {
                        quesionCounter.setText("" + (itemPosition + 1));
                    } else {
                        quesionCounter.setText("" + (currentPosition - 1));
                    }
                    //quesionCounter.setText((currentPosition - 1) + " of " + reviewResult.getDetail().size());
                    mPager.setCurrentItem(currentPosition - 1);
                }

            }
        });
        rightTest = findViewById(R.id.right_arrow);
        back_button = findViewById(R.id.back_button);
        rightTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemPosition < 1) {
                    quesionCounter.setText("" + (itemPosition + 1));
                } else {
                    quesionCounter.setText("" + (currentPosition + 1));
                }
                // quesionCounter.setText((currentPosition + 1) + " of " + reviewResult.getDetail().size());
                mPager.setCurrentItem(currentPosition + 1);

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DNAApplication.getInstance().setReviewList(new ArrayList<>());

    }

    @Override
    protected void onResume() {
        super.onResume();
        //getReviewTest();
    }


    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int newPosition) {
            currentPosition = newPosition;
            quesionCounter.setText("" + (newPosition + 1));
            // quesionCounter.setText(((newPosition + 1) + " of " + reviewResult.getDetail().size());

        }

        @Override
        public void onPageScrolled(int newPosition, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public static class MyAdapter extends FragmentPagerAdapter {
        List<QuestionList> reviewResult = null;
        TextView quesionCounter;

        public MyAdapter(FragmentManager fragmentManager, List<QuestionList> reviewResult, TextView quesionCounter) {
            super(fragmentManager);
            this.reviewResult = reviewResult;
            this.quesionCounter = quesionCounter;
        }


        @Override
        public int getCount() {
            if (reviewResult != null && reviewResult.size() > 0) {
                return reviewResult.size();

            }
            return 0;
        }

        @Override
        public Fragment getItem(int position) {

            quesionCounter.setText("" + (position));

            //quesionCounter.setText((position) + " of " + reviewResult.getDetail().size());
            return ReviewResultFragment.init(reviewResult.get(position), position);
        }
    }
}
