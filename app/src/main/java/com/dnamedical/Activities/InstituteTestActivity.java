package com.dnamedical.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.dnamedical.Models.test.testp.Test;
import com.dnamedical.Models.test.testp.TestDataResponse;
import com.dnamedical.R;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;

import java.util.ArrayList;
import java.util.List;

public class InstituteTestActivity extends AppCompatActivity {


    public LinearLayout tabBar;
    public TabLayout tabLayout;
    private Toolbar toolbar;
    public ViewPager pager;

    private MainActivity.ViewPagerAdapter adapter;
    String name, email;
    TestDataResponse testDataResponse;


    private List<Test> grandTests = new ArrayList<>();
    private List<Test> miniTests = new ArrayList<>();
    public boolean isDailyTest;

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

        toolbar.setTitle("" + getIntent().getStringExtra(Constants.INST_NAME));

        isDailyTest = getIntent().getBooleanExtra(Constants.ISDAILY_TEST,false);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_logo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

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





