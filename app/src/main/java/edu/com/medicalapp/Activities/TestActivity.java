package edu.com.medicalapp.Activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.com.medicalapp.Models.QustionDetails;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.fragment.TruitonListFragment;
import edu.com.medicalapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends FragmentActivity {
        static final int ITEMS = 10;
        MyAdapter mAdapter;
        ViewPager mPager;
    TextView quesionCounter;
    TextView timer;
    CountDownTimer countDownTimer;
    private QustionDetails qustionDetails;
    private Button button;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_pager);

        quesionCounter = findViewById(R.id.counter);
        timer = findViewById(R.id.timer);
        button = (Button) findViewById(R.id.first);
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    mPager.setCurrentItem(0);
                }
            });
            button = (Button) findViewById(R.id.next);
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (qustionDetails.getDetail() != null && qustionDetails.getDetail().size() > 0)
                       if (mPager.getCurrentItem()<qustionDetails.getDetail().size()) {
                           mPager.setCurrentItem(qustionDetails.getDetail().size() - mPager.getCurrentItem() + 1);
                           quesionCounter.setText((qustionDetails.getDetail().size() - mPager.getCurrentItem() + 1) + " of " + qustionDetails.getDetail().size());

                       }
                }
            });


        countDownTimer = new CountDownTimer(1 * 60000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("" + new SimpleDateFormat("mm:ss:SS").format(new Date(millisUntilFinished)));
            }

            public void onFinish() {
                timer.setText("Time up!");
            }
        }.start();


        }


    @Override
    protected void onResume() {
        super.onResume();
        getTest();
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        QustionDetails qustionDetails = null;
        TextView quesionCounter;

        public MyAdapter(FragmentManager fragmentManager, QustionDetails qustionDetails, TextView quesionCounter) {
                super(fragmentManager);
            this.qustionDetails = qustionDetails;
            this.quesionCounter = quesionCounter;
            }



        @Override
            public int getCount() {
            if (qustionDetails.getDetail() != null && qustionDetails.getDetail().size() > 0)
                return qustionDetails.getDetail().size();
        return 0;
        }

            @Override
            public Fragment getItem(int position) {
                quesionCounter.setText((position) + " of " + qustionDetails.getDetail().size());
                return TruitonListFragment.init(qustionDetails.getDetail().get(position),position);
            }
        }

    private void getTest() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.getQuestion(getIntent().getStringExtra("id"), new Callback<QustionDetails>() {
                @Override
                public void onResponse(Call<QustionDetails> call, Response<QustionDetails> response) {
                    Utils.dismissProgressDialog();

                    if (response.code() == 200) {
                        qustionDetails = response.body();
                        mAdapter = new MyAdapter(getSupportFragmentManager(), qustionDetails, quesionCounter);
                        mPager = (ViewPager) findViewById(R.id.pager);
                        mPager.setAdapter(mAdapter);
                    }
                }

                @Override
                public void onFailure(Call<QustionDetails> call, Throwable t) {
                    Utils.dismissProgressDialog();
                }
            });
        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();
        }

    }


    }