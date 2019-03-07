package edu.com.medicalapp.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    ReviewresulActivity.MyAdapter mAdapter;
    ViewPager mPager;
    TextView quesionCounter;
    static int currentPosition;
    List<QbankTestResponse> qbankResponse=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_test);

        for(int i=0;i<=100;i++)
        {
           QbankTest qbankTest=new QbankTest();
           qbankTest.setQuestion("Select One Programming Language-------");
           qbankTest.setAnswer1("ANDROID");
           qbankTest.setAnswer2("JAVA");
           qbankTest.setAnswer3("SQL");
           qbankTest.setAnswer4("None Of These");


        }
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
        QbankTestResponse qbankResponse;
        TextView quesionCounter;

        public MyAdapter(FragmentManager fragmentManager, QbankTestResponse qbankResponse, TextView quesionCounter) {
            super(fragmentManager);
            this.qbankResponse = qbankResponse;
            this.quesionCounter = quesionCounter;
        }


        @Override
        public int getCount() {
            if (qbankResponse!= null && qbankResponse.getDetails().size()>0  )
                return qbankResponse.getDetails().size();
            else {
                return 0;
            }
        }

        @Override
        public Fragment getItem(int position) {
            //quesionCounter.setText((position) + " of " + reviewResult.getDetail().size());
           // return ReviewResultFragment.init(qbankResponse.getDetails()., position);
           // return ReviewResultFragment.init(reviewResult.getDetail().get(position), position);
            return QbankTestFragment.init(qbankResponse.getDetails().get(position),position);
        }
    }
}
