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
    @SerializedName("user_rank")
    @Expose
    private String userRank;
    @SerializedName("user_score")
    @Expose
    private String userScore;
    @SerializedName("percentile")
    @Expose
    private String percentile;
    @SerializedName("user_total_score")
    @Expose
    private Integer userTotalScore;
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

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public String getPercentile() {
        return percentile;
    }

    public void setPercentile(String percentile) {
        this.percentile = percentile;
    }

    public Integer getUserTotalScore() {
        return userTotalScore;
    }

    public void setUserTotalScore(Integer userTotalScore) {
        this.userTotalScore = userTotalScore;
    }

    public String getTotalUsersTest() {
        return totalUsersTest;
    }

    public void setTotalUsersTest(String totalUsersTest) {
        this.totalUsersTest = totalUsersTest;
    }
}