package edu.com.medicalapp.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

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
    private QustionDetails qustionDetails;


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
                    if (qustionDetails.getDetail() != null && qustionDetails.getDetail().size() > 0)
                        mPager.setCurrentItem(qustionDetails.getDetail().size() - 1);
                }
            });




        }


    @Override
    protected void onResume() {
        super.onResume();
        getTest();
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        QustionDetails qustionDetails = null;

        public MyAdapter(FragmentManager fragmentManager, QustionDetails qustionDetails) {
                super(fragmentManager);
            this.qustionDetails = qustionDetails;
            }



        @Override
            public int getCount() {
            if (qustionDetails.getDetail() != null && qustionDetails.getDetail().size() > 0)
                return qustionDetails.getDetail().size();
        return 0;
        }

            @Override
            public Fragment getItem(int position) {
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
                        mAdapter = new MyAdapter(getSupportFragmentManager(), qustionDetails);
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