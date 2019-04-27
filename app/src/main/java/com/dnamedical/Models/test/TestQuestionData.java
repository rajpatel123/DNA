package com.dnamedical.Models.test;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestQuestionData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("All_Test")
    @Expose
    private List<AllTest> allTest = null;
    @SerializedName("Grand_Test")
    @Expose
    private List<GrandTest> grandTest = null;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private long time;
    @SerializedName("Mini_Test")
    @Expose
    private List<MiniTest> miniTest = null;
    @SerializedName("Subject_Test")
    @Expose
    private List<SubjectTest> subjectTest = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AllTest> getAllTest() {
        return allTest;
    }

    public void setAllTest(List<AllTest> allTest) {
        this.allTest = allTest;
    }

    public List<GrandTest> getGrandTest() {
        return grandTest;
    }

    public void setGrandTest(List<GrandTest> grandTest) {
        this.grandTest = grandTest;
    }

    public List<MiniTest> getMiniTest() {
        return miniTest;
    }

    public void setMiniTest(List<MiniTest> miniTest) {
        this.miniTest = miniTest;
    }

    public List<SubjectTest> getSubjectTest() {
        return subjectTest;
    }

    public void setSubjectTest(List<SubjectTest> subjectTest) {
        this.subjectTest = subjectTest;
    }
}