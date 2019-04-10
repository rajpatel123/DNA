package com.dnamedical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Models.QbankSubTest.QbankTestResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.fragment.QbankTestFragment;
import com.dnamedical.utils.Utils;
import com.dnamedical.views.CustomViewPager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QbankTestActivity extends AppCompatActivity {


    /// public String quest_id="";
    public static int quest_id;
    public String user_id;
    public String is_completed;
    public String user_answer = null;
    MyAdapter mAdapter;
    CustomViewPager mPager;
    TextView quesionCounter;
    static int currentPosition;
    public QbankTestResponse qbankTestResponse;
    ImageView imageViewCancel;
    public ProgressBar mProgressBar;
    public CountDownTimer mCountDownTimer;
    int progress = 100;
    LinearLayout linearBottom;
    public Button nextBtn;
    String module_id;
    private QbankTestFragment fragment;
    private int questionStartId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_test);
        imageViewCancel = findViewById(R.id.btnCancel);
        linearBottom = findViewById(R.id.linearBottom);
        nextBtn = findViewById(R.id.nextBtn);

        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().hasExtra("qmodule_id")) {
            module_id = getIntent().getStringExtra("qmodule_id");
            user_id = getIntent().getStringExtra("userId");
            questionStartId = Integer.parseInt(getIntent().getStringExtra("questionStartId"));
        }
        qbankgetTest();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextBtn.getText().toString().equalsIgnoreCase("Complete")) {
                    Intent intent = new Intent(QbankTestActivity.this, QbankRatingActivity.class);
                    intent.putExtra("module_id", module_id);
                    intent.putExtra("userId", user_id);
                    startActivity(intent);
                    finish();
                } else {
                    mPager.setCurrentItem((currentPosition) + 1);

                }
            }
        });


        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setProgress(progress);

        mCountDownTimer = new CountDownTimer(20000, 500) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress" + progress + millisUntilFinished);
                progress--;
                mProgressBar.setProgress((int) progress);
                mCountDownTimer = new CountDownTimer(20000, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        Log.v("Log_tag", "Tick of Progress" + progress + millisUntilFinished);
                        progress--;
                        mProgressBar.setProgress((int) progress);
                    }

                    @Override
                    public void onFinish() {
                        //Do what you want
                        //qbankgetTest();
                        progress = 100;
                        mProgressBar.setProgress(0);
                        linearBottom.setVisibility(View.VISIBLE);
                        if (fragment != null)
                            fragment.submitAnswer();
                        showHideBottomLayout(true);
                        // finish();

                    }
                };


            }

            @Override
            public void onFinish() {
                //Do what you want
                //qbankgetTest();
                progress = 100;
                mProgressBar.setProgress(0);
                linearBottom.setVisibility(View.VISIBLE);
                if (fragment != null)
                    fragment.submitAnswer();
                showHideBottomLayout(true);

            }
        };


    }

    public void showHideBottomLayout(boolean show) {
        //TODO call submit answer api and visible layout on response
        if (show) {
            linearBottom.setVisibility(View.VISIBLE);
        } else {
            linearBottom.setVisibility(View.GONE);

        }

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void qbankgetTest() {
        RequestBody qmodule_id = RequestBody.create(MediaType.parse("text/plain"), module_id);
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.qbanksubTestData(qmodule_id, new Callback<QbankTestResponse>() {
                @Override
                public void onResponse(Call<QbankTestResponse> call, Response<QbankTestResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.isSuccessful()) {

                        if (response.body() != null)
                            qbankTestResponse = response.body();

                        mAdapter = new MyAdapter(getSupportFragmentManager(), qbankTestResponse, quesionCounter, mProgressBar, mCountDownTimer);
                        mPager = findViewById(R.id.pager2);
                        mPager.addOnPageChangeListener(pageChangeListener);
                        mPager.setAdapter(mAdapter);
                        mPager.setCurrentItem(questionStartId);
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
        ProgressBar mpProgressBar;
        CountDownTimer countDownTimer;
        QbankTestFragment fragment;

        public MyAdapter(FragmentManager fragmentManager, QbankTestResponse qbankTestResponse, TextView quesionCounter, ProgressBar mpProgressBar, CountDownTimer countDownTimer) {
            super(fragmentManager);
            this.qbankTestResponse = qbankTestResponse;
            this.quesionCounter = quesionCounter;
            this.mpProgressBar = mpProgressBar;
            this.countDownTimer = countDownTimer;
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
            mpProgressBar.setMax(100);
            mpProgressBar.setProgress(100);
            countDownTimer.start();
            fragment = (QbankTestFragment) QbankTestFragment.init(qbankTestResponse.getDetails().get(position), position);
            return fragment;
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

    public void setFragment(QbankTestFragment fragment) {
        this.fragment = fragment;

    }
}
