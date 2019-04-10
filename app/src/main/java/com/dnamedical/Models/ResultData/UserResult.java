package com.dnamedical.Models.ResultData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResult {

    @SerializedName("total_question")
    @Expose
    private String totalQuestion;
    @SerializedName("current_question")
    @Expose
    private String currentQuestion;
    @SerializedName("wrong_question")
    @Expose
    private String wrongQuestion;
    @SerializedName("skip_question")
    @Expose
    private String skipQuestion;
    @SerializedName("average")
    @Expose
    private String average;

    public String getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public String getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(String currentQuestion) {
        this.currentQuestion = currentQuestion;
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

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

}