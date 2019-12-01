package com.dnamedical;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;

import com.dnamedical.Models.test.TestQuestionData;
import com.dnamedical.Models.testReviewlistnew.QuestionList;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class DNAApplication extends MultiDexApplication {

    static DNAApplication dnaApplication = null;

    List<QuestionList> questionList = new ArrayList<>();
    public static TestQuestionData getTestQuestionData() {
        return testQuestionData;
    }

    public static void setTestQuestionData(TestQuestionData testQuestionData) {
        DNAApplication.testQuestionData = testQuestionData;
    }

    public static TestQuestionData testQuestionData;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        printHashKey();
    }



    public void printHashKey() {
        Exception exception = null;
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            exception = e;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            exception = e;
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }

        if (exception != null) {
            String additionalDetail = null;
        }
    }

    public static DNAApplication getInstance() {
        if (dnaApplication == null) {
            dnaApplication = new DNAApplication();
        }
        return dnaApplication;
    }

    public void setReviewList(List<QuestionList> questionList) {
        this.questionList = questionList;
    }


    public List<QuestionList> getQuestionList(){
        return questionList;
    }
}
