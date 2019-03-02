package edu.com.medicalapp.Activities;


import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import edu.com.medicalapp.Models.ReviewResult.ReviewResult;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.fragment.ReviewResultFragment;
import edu.com.medicalapp.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewresulActivity extends FragmentActivity {

    MyAdapter mAdapter;
    ViewPager mPager;
    TextView quesionCounter;
    TextView timer;
    static int currentPosition;
    String userId;
    TextView leftTest, rightTest;


    private ReviewResult reviewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewresul);


        getReviewTest();
        quesionCounter = findViewById(R.id.question_number);

        leftTest = findViewById(R.id.left_arrow);
        leftTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentPosition>0) {
                    quesionCounter.setText((currentPosition - 1) + " of " + reviewResult.getDetail().size());
                    mPager.setCurrentItem(currentPosition - 1);
                }

            }
        });
        rightTest=findViewById(R.id.right_arrow);
        rightTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quesionCounter.setText((currentPosition + 1) + " of " + reviewResult.getDetail().size());
                mPager.setCurrentItem(currentPosition + 1);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getReviewTest();


    }


    private void getReviewTest() {
        /*if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), "Login_Id");

        }*/
        String userId = "1";
        String testId = "6";

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody test_id = RequestBody.create(MediaType.parse("text/plain"), testId);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);

            RestClient.reviewQuestionResult(user_id, test_id, new Callback<ReviewResult>() {
                @Override
                public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {

                    Utils.dismissProgressDialog();
                    if (response.code() == 200) {
                        reviewResult = response.body();
                        mAdapter = new MyAdapter(getSupportFragmentManager(), reviewResult, quesionCounter);
                        mPager = (ViewPager) findViewById(R.id.pager2);
                        mPager.addOnPageChangeListener(pageChangeListener);
                        mPager.setAdapter(mAdapter);
                    }

                }

                @Override
                public void onFailure(Call<ReviewResult> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(ReviewresulActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int newPosition) {
            currentPosition = newPosition;
            quesionCounter.setText((newPosition + 1) + " of " + reviewResult.getDetail().size());

        }

        @Override
        public void onPageScrolled(int newPosition, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    };


    public static class MyAdapter extends FragmentPagerAdapter {
        ReviewResult reviewResult = null;
        TextView quesionCounter;

        public MyAdapter(FragmentManager fragmentManager, ReviewResult reviewResult, TextView quesionCounter) {
            super(fragmentManager);
            this.reviewResult = reviewResult;
            this.quesionCounter = quesionCounter;
        }


        @Override
        public int getCount() {
            if (reviewResult.getDetail() != null && reviewResult.getDetail().size() > 0)
                return reviewResult.getDetail().size();
            return 0;
        }

        @Override
        public Fragment getItem(int position) {
            quesionCounter.setText((position) + " of " + reviewResult.getDetail().size());
            return ReviewResultFragment.init(reviewResult.getDetail().get(position), position);
        }
    }
}

