package com.dnamedical.Activities;

import android.content.Intent;
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

import com.dnamedical.R;
import com.dnamedical.fragment.BuynowFragment;
import com.dnamedical.fragment.CompleteFragment;
import com.dnamedical.fragment.FreeFragment;
import com.dnamedical.fragment.PausedFragment;
import com.dnamedical.fragment.UnattemptedFragment;

public class VideoActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView subcategory;
    public static String subCatId;
    public static int discountonfullpurchase;
    DisplayDataInterface displayDataInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_vedio);
        subcategory=findViewById(R.id.subcategory_name);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        Intent intent=getIntent();
        if(intent.hasExtra("SubCategoryName"))
        {
            String subcategoryname=intent.getStringExtra("SubCategoryName");
            subcategory.setText(subcategoryname);
        }

        if (intent.hasExtra("subCatId")) {
            subCatId = intent.getStringExtra("subCatId");
        }
        if (intent.hasExtra("discountonfullpurchase")) {
            discountonfullpurchase = intent.getIntExtra("discountonfullpurchase",0);
        }



    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabOne.setText("Buy Now");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabTwo.setText("Free");
        tabLayout.getTabAt(1).setCustomView(tabTwo);

/*
        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabThree.setText("Unattempted");
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabFour.setText("Completed");
        tabLayout.getTabAt(3).setCustomView(tabFour);*/

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabFive.setText("Paused");
        tabLayout.getTabAt(2).setCustomView(tabFive);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BuynowFragment(), "Buy Now");
        adapter.addFrag(new FreeFragment(), "Free");

  /*    adapter.addFrag(new UnattemptedFragment(), "Unattempted");
        adapter.addFrag(new CompleteFragment(), "Completed");*/

        adapter.addFrag(new PausedFragment(), "Paused");
        viewPager.setAdapter(adapter);
    }

    public void setListener(DisplayDataInterface displayDataInterface) {
        this.displayDataInterface = displayDataInterface;
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




    public interface DisplayDataInterface{
        public void showVideos();
    }

}
