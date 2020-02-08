package com.dnamedical.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.dnamedical.Models.test.testp.Test;
import com.dnamedical.R;

import java.util.ArrayList;
import java.util.List;

public class ModuleTestActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_module_test);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);

    }
}
