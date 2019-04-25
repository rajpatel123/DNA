package com.dnamedical.Models.ResultData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResult {
    @SerializedName("total_question")
    @Expose
    private String totalQuestion;
    @SerializedName("currect_question")
    @Expose
    private String currectQuestion;
    @SerializedName("wrong_question")
    @Expose
    private String wrongQuestion;
    @SerializedName("skip_question")
    @Expose
    private String skipQuestion;
    @SerializedName("percentile")
    @Expose
    private String percentile;
    @SerializedName("total_users_test")
    @Expose
    private String totalUsersTest;

    public String getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public String getCurrectQuestion() {
        return currectQuestion;
    }

    public void setCurrectQuestion(String currectQuestion) {
        this.currectQuestion = currectQuestion;
    }

    public String getWrongQuestion() {
        return wrongQuestion;
    }

    public void setWrongQuestion(String wrongQuestion) {
        this.wrongQuestion = wrongQuestion;
    }

    public String getSkipQuestion() {
        return skipQuestion;
    }

    public void setSkipQuestion(String skipQuestion) {
        this.skipQuestion = skipQuestion;
    }

    public String getPercentile() {
        return percentile;
    }

    public void setPercentile(String percentile) {
        this.percentile = percentile;
    }

    public String getTotalUsersTest() {
        return totalUsersTest;
    }

    public void setTotalUsersTest(String totalUsersTest) {
        this.totalUsersTest = totalUsersTest;
    }

}