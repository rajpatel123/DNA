package com.dnamedical.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.dnamedical.Models.qbank.QBank;
import com.dnamedical.R;
import com.dnamedical.fragment.QbankAllFragment;
import com.dnamedical.fragment.QbankCompletedFragment;
import com.dnamedical.fragment.QbankFreeFragment;
import com.dnamedical.fragment.QbankPausedFragment;
import com.dnamedical.fragment.QbankUnattemptedFragment;

public class QbankSubActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public String qbankcat_id;
    public String qbankcat_name;
    TextView toolbarName;
    public List<QBank> qBankAll = new ArrayList<>();
    public List<QBank> qBankPaused = new ArrayList<>();
    public List<QBank> qBankCompleted = new ArrayList<>();
    public List<QBank> qBankUnAttempted = new ArrayList<>();
    public List<QBank> qBankUnFree = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_sub);
        toolbarName = findViewById(R.id.qbank_subcategory_name);

        if (getIntent().hasExtra("cat_id")) {
            qbankcat_id = getIntent().getStringExtra("cat_id");
            qbankcat_name = getIntent().getStringExtra("cat_name");
        }

        toolbarName.setText(qbankcat_name);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager = (ViewPager) findViewById(R.id.qbank_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.qbank_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabOne.setText("All");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabTwo.setText("Paused");
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabThree.setText("Completed");
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabFour.setText("Unattempted");
        tabLayout.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabFive.setText("Free");
        tabLayout.getTabAt(4).setCustomView(tabFive);

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new QbankAllFragment(), "All");
        viewPagerAdapter.addFrag(new QbankPausedFragment(), "Paused");
        viewPagerAdapter.addFrag(new QbankCompletedFragment(), "Completed");
        viewPagerAdapter.addFrag(new QbankUnattemptedFragment(), "Unattempted");
        viewPagerAdapter.addFrag(new QbankFreeFragment(), "Free");
        viewPager.setAdapter(viewPagerAdapter);


    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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
