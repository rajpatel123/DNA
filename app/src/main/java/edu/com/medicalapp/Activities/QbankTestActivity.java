package edu.com.medicalapp.Activities;

import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.com.medicalapp.Models.QbankSubTest.QbankTestResponse;
import edu.com.medicalapp.Models.QbankTest.QbankTest;
import edu.com.medicalapp.Models.ReviewResult.ReviewResult;
import edu.com.medicalapp.Models.qbank.QbankResponse;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.fragment.QbankTestFragment;
import edu.com.medicalapp.fragment.ReviewResultFragment;
import edu.com.medicalapp.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QbankTestActivity extends AppCompatActivity {

    MyAdapter mAdapter;
    ViewPager mPager;
    TextView quesionCounter;
    static int currentPosition;
    private QbankTestResponse qbankTestResponse;
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
                //qbankgetTest();
                i++;
                mProgressBar.setProgress(100);
            }
        };
        mCountDownTimer.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        qbankgetTest();
    }

    private void qbankgetTest() {

        String id = "1";
        RequestBody qmodule_id = RequestBody.create(MediaType.parse("text/plain"), id);


        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.qbanksubTestData(qmodule_id, new Callback<QbankTestResponse>() {
                @Override
                public void onResponse(Call<QbankTestResponse> call, Response<QbankTestResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.isSuccessful()) {

                        if (response.body() != null)
                            qbankTestResponse = response.body();

                        mAdapter = new MyAdapter(getSupportFragmentManager(), qbankTestResponse, quesionCounter);
                        mPager = (ViewPager) findViewById(R.id.pager2);
                        mPager.addOnPageChangeListener(pageChangeListener);
                        mPager.setAdapter(mAdapter);
                        mPager.setOnTouchListener(vOnTouchListener);
                        mPager.setHorizontalScrollBarEnabled(false);
                    }
                }

                @Override
                public void onFailure(Call<QbankTestResponse> call, Throwable t) {
                    Toast.makeText(QbankTestActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
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
        QbankTestResponse qbankTestResponse;
        TextView quesionCounter;

        public MyAdapter(FragmentManager fragmentManager, QbankTestResponse qbankTestResponse, TextView quesionCounter) {
            super(fragmentManager);
            this.qbankTestResponse = qbankTestResponse;
            this.quesionCounter = quesionCounter;
        }


        @Override
        public int getCount() {
            if (qbankTestResponse != null && qbankTestResponse.getDetails().size() > 0) {
                return qbankTestResponse.getDetails().size();
            } else {
                return 0;
            }
        }

        @Override
        public Fragment getItem(int position) {
            //quesionCounter.setText((position) + " of " + reviewResult.getDetail().size());
            // return ReviewResultFragment.init(qbankResponse.getDetails()., position);
            // return ReviewResultFragment.init(reviewResult.getDetail().get(position), position);
            return QbankTestFragment.init(qbankTestResponse.getDetails().get(position), position);
        }
    }

    View.OnTouchListener vOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mPager.getCurrentItem() == 0) {
                mPager.setCurrentItem(-1, false);
                return false;
            } else if (mPager.getCurrentItem() == 1) {
                mPager.setCurrentItem(1, false);
                return false;
            } else if (mPager.getCurrentItem() == 2) {
                mPager.setCurrentItem(2, false);
                return false;
            }
            return true;
        }
    };
}
