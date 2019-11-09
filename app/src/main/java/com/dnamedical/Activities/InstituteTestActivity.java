package com.dnamedical.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.test.testp.Test;
import com.dnamedical.Models.test.testp.TestDataResponse;
import com.dnamedical.R;
import com.dnamedical.fragment.HomeFragment;
import com.dnamedical.fragment.OnlineFragment;
import com.dnamedical.fragment.QbankFragment;
import com.dnamedical.fragment.TestFragment;
import com.dnamedical.fragment.videoFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InstituteTestActivity extends AppCompatActivity {


    public LinearLayout tabBar;
    public TabLayout tabLayout;
    private Toolbar toolbar;
    public ViewPager pager;
    private HomeFragment dashboardHomeFragment;
    private videoFragment dashboardvideoFragment;
    private QbankFragment dashboardQbankFragment;
    private TestFragment dashboardTestFragment;
    private OnlineFragment dashboardOnlineFragment;
    private MainActivity.ViewPagerAdapter adapter;
    private TextView myDeviceTitle;
    private ImageView imgDeviceIcon;
    private TextView videoText;
    private ImageView imgVideoViewIcon;
    private TextView qbTitle;
    private ImageView imgQBIcon;
    private TextView testTitle;
    private ImageView testIcon;
    private ImageView imgOnlineIcon;
    private TextView onlineTitle;
    private NavigationView navigationView;
    private TextView tvName, tvEmail, tvSetting;
    private CircleImageView circleImageView;
    String name, image, email;
    TestDataResponse testDataResponse;


    private List<Test> grandTests = new ArrayList<>();
    private List<Test> miniTests = new ArrayList<>();

    public List<Test> getDailyTest() {
        return dailyTest;
    }

    public void setDailyTest(List<Test> dailyTest) {
        this.dailyTest = dailyTest;
    }

    private List<Test> dailyTest = new ArrayList<>();
    private List<Test> subjectTests = new ArrayList<>();
    private List<Test> allTests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_institute_test);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_logo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar:
                //your code here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public List<Test> getGrandTests() {
        return grandTests;
    }

    public void setGrandTests(List<Test> grandTests) {
        this.grandTests = grandTests;
    }

    public List<Test> getMiniTests() {
        return miniTests;
    }

    public void setMiniTests(List<Test> miniTests) {
        this.miniTests = miniTests;
    }

    public List<Test> getSubjectTests() {
        return subjectTests;
    }

    public void setSubjectTests(List<Test> subjectTests) {
        this.subjectTests = subjectTests;
    }

    public List<Test> getAllTests() {
        return allTests;
    }

    public void setAllTests(List<Test> allTests) {
        this.allTests = allTests;
    }
}





