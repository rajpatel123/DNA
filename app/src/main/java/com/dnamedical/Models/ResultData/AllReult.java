package com.dnamedical.Models.ResultData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllReult {
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("image")
    @Expose
    private String image;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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
}
