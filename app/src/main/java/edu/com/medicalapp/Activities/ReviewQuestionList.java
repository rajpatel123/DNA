package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.com.medicalapp.Adapters.ReviewQuestionListAdapter;
import edu.com.medicalapp.Models.ReviewResult.ReviewDetail;
import edu.com.medicalapp.Models.ReviewResult.ReviewResult;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.fragment.BuynowFragment;
import edu.com.medicalapp.fragment.CompleteFragment;
import edu.com.medicalapp.fragment.FreeFragment;
import edu.com.medicalapp.fragment.PausedFragment;
import edu.com.medicalapp.fragment.ReviewQuestionListFragment;
import edu.com.medicalapp.fragment.UnattemptedFragment;
import edu.com.medicalapp.utils.DnaPrefs;
import edu.com.medicalapp.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewQuestionList extends AppCompatActivity {


    RecyclerView recyclerView;
    private ReviewResult reviewResult;
    private ImageView imageBack;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String totalQuestion;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_question_list);
       // recyclerView = findViewById(R.id.recycler);
        imageBack = findViewById(R.id.back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().hasExtra("tquestion")) {
            totalQuestion = getIntent().getStringExtra("tquestion");
            total = Integer.parseInt(totalQuestion);

        }
        //getReviewTest();

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager = (ViewPager) findViewById(R.id.view);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {

       ReviewViewPagerAdapter adapter = new ReviewViewPagerAdapter(getSupportFragmentManager());
        for(int i=0;i<total;i++){
            adapter.addFrag(new ReviewQuestionListFragment(),"");
            //            adapter.addFragment(new TwoFragment(), "TWO");
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(i);

        }

    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        for (int i = 0; i <total; i++) {
           tabOne.setText(String.valueOf(i + 1) + "-" + String.valueOf(i + 5));
            //i = i + 4;
            tabLayout.getTabAt(i).setCustomView(tabOne);
        }
    }

    public class ReviewViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ReviewViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void getReviewTest() {
        String userId;
        String testId;
       /* if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), "Login_Id");

        }
*/
        userId = "1";
        testId = "6";


        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody test_id = RequestBody.create(MediaType.parse("text/plain"), testId);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);

            RestClient.reviewQuestionResult(user_id, test_id, new Callback<ReviewResult>() {
                @Override
                public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                    Utils.dismissProgressDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus().equals("1")) {
                                reviewResult = response.body();
                                if (reviewResult != null && reviewResult.getDetail().size() > 0) {
                                    Log.d("Api Response :", "Got Success from Api");
                                    ReviewQuestionListAdapter reviewQuestionListAdapter = new ReviewQuestionListAdapter(getApplicationContext());
                                    reviewQuestionListAdapter.setReviewDetails(reviewResult.getDetail());

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                    reviewQuestionListAdapter.setReviewClickListener(new ReviewQuestionListAdapter.ReviewOnClickListener() {
                                        @Override
                                        public void onReviewClick(int position) {
                                            Intent intent = new Intent(getApplicationContext(), ReviewresulActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.putExtra("list", reviewResult);
                                            intent.putExtra("position", position);
                                            startActivity(intent);
                                        }
                                    });
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setAdapter(reviewQuestionListAdapter);
                                } else {
                                    Toast.makeText(ReviewQuestionList.this, "No Data", Toast.LENGTH_SHORT).show();
                                }
                            }
                            //Toast.makeText(ReviewQuestionList.this, "Invalid Status", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(ReviewQuestionList.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //Toast.makeText(ReviewQuestionList.this, "Response Failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ReviewResult> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(ReviewQuestionList.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connection Failed!!", Toast.LENGTH_SHORT).show();
        }
    }
}
