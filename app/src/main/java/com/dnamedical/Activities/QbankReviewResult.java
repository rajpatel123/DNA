package com.dnamedical.Activities;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.dnamedical.R;
import com.dnamedical.fragment.QbankReviewSheetFragment;

public class QbankReviewResult extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    static int currentPosition;

    ImageView imageBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_review_result);

        imageBack = findViewById(R.id.back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        viewPager = (ViewPager) findViewById(R.id.view);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        //setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        QbankReviewPageAdapter qbankReviewPageAdapter = new QbankReviewPageAdapter(getSupportFragmentManager());
        for (int i = 0; i < (50 / 2); i++) {
            QbankReviewSheetFragment qbankReviewSheetFragment = new QbankReviewSheetFragment();
            View view = qbankReviewSheetFragment.getView();
            TextView tabone = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
            tabone.setText(String.valueOf(i + 1) + "-" + String.valueOf(i + 5));
            qbankReviewPageAdapter.addFrag(qbankReviewSheetFragment, "" + String.valueOf(i + 1) + "-" + String.valueOf(i + 5));
            i = i + 4;
        }
        viewPager.setAdapter(qbankReviewPageAdapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(pageChangeListener);
        qbankReviewPageAdapter.getItem(viewPager.getCurrentItem());
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int newPosition) {

            currentPosition = newPosition;
            // quesionCounter.setText(((newPosition + 1) + " of " + reviewResult.getDetail().size());

        }

        @Override
        public void onPageScrolled(int newPosition, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };


    public class QbankReviewPageAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public QbankReviewPageAdapter(FragmentManager manager) {
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
}
