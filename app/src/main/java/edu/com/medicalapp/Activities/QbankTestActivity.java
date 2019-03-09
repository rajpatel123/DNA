package edu.com.medicalapp.Activities;

import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.com.medicalapp.Models.QbankTest.QbankTest;
import edu.com.medicalapp.Models.QbankTest.QbankTestResponse;
import edu.com.medicalapp.Models.ReviewResult.ReviewResult;
import edu.com.medicalapp.Models.qbank.QbankResponse;
import edu.com.medicalapp.R;
import edu.com.medicalapp.fragment.QbankTestFragment;
import edu.com.medicalapp.fragment.ReviewResultFragment;

public class QbankTestActivity extends AppCompatActivity {

    MyAdapter mAdapter;
    ViewPager mPager;
    TextView quesionCounter;
    static int currentPosition;
    //List<QbankTestResponse> qbankResponse=new ArrayList<>();
    List<QbankTest> qbankTests = new ArrayList<>();
    ImageView imageViewCancel;
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_test);
        imageViewCancel = findViewById(R.id.btnCancel);
        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setProgress(100);

        mCountDownTimer = new CountDownTimer(20000, 500) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress" + i + millisUntilFinished);
                i++;
                mProgressBar.setProgress((int) i);

            }
            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(100);
            }
        };
        mCountDownTimer.start();

        for (int i = 0; i <= 100; i++) {
            QbankTest qbankTest = new QbankTest();
            qbankTest.setQuestion("Select One Programming Language-------");
            qbankTest.setAnswer1("ANDROID");
            qbankTest.setAnswer2("JAVA");
            qbankTest.setAnswer3("SQL");
            qbankTest.setAnswer4("None Of These");
            qbankTests.add(qbankTest);
        }
        mAdapter = new MyAdapter(getSupportFragmentManager(), qbankTests, quesionCounter);
        mPager = (ViewPager) findViewById(R.id.pager2);
        mPager.addOnPageChangeListener(pageChangeListener);
        mPager.setAdapter(mAdapter);

    }


    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int newPosition) {
            currentPosition = newPosition;
            //quesionCounter.setText((newPosition + 1) + " of " + reviewResult.getDetail().size());

        }

        @Override
        public void onPageScrolled(int newPosition, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    };


    public static class MyAdapter extends FragmentPagerAdapter {
        List<QbankTest> qbankTest;
        TextView quesionCounter;

        public MyAdapter(FragmentManager fragmentManager, List<QbankTest> qbankTest, TextView quesionCounter) {
            super(fragmentManager);
            this.qbankTest = qbankTest;
            this.quesionCounter = quesionCounter;
        }


        @Override
        public int getCount() {
            if (qbankTest != null && qbankTest.size() > 0)
                return qbankTest.size();
            else {
                return 0;
            }
        }

        @Override
        public Fragment getItem(int position) {
            //quesionCounter.setText((position) + " of " + reviewResult.getDetail().size());
            // return ReviewResultFragment.init(qbankResponse.getDetails()., position);
            // return ReviewResultFragment.init(reviewResult.getDetail().get(position), position);
            return QbankTestFragment.init(qbankTest.get(position), position);
        }
    }
}
