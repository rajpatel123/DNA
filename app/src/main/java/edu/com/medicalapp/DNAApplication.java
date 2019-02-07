package edu.com.medicalapp;

import android.app.Application;

import edu.com.medicalapp.Models.test.TestQuestionData;
import edu.com.medicalapp.fragment.AllTestFragment;

public class DNAApplication extends Application {

    static DNAApplication dnaApplication=null;
    public static TestQuestionData getTestQuestionData() {
        return testQuestionData;
    }

    public static void setTestQuestionData(TestQuestionData testQuestionData) {
        DNAApplication.testQuestionData = testQuestionData;
    }

    public static TestQuestionData testQuestionData;

    @Override
    public void onCreate() {
        super.onCreate();


    }

    public static DNAApplication getInstance() {
        if (dnaApplication==null){
            dnaApplication= new DNAApplication();
        }
        return dnaApplication;
    }
}
