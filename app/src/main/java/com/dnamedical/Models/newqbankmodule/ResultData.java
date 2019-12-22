package com.dnamedical.Models.newqbankmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultData {

    @SerializedName("total_attemptedmcq")
    @Expose
    private Integer totalAttemptedmcq;
    @SerializedName("total_mcq")
    @Expose
    private Integer totalMcq;
    @SerializedName("wrong")
    @Expose
    private String wrong;
    @SerializedName("currect")
    @Expose
    private String currect;
    @SerializedName("skipped")
    @Expose
    private String skipped;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("percentage")
    @Expose
    private Integer percentage;

    public Integer getTotalAttemptedmcq() {
        return totalAttemptedmcq;
    }

    public void setTotalAttemptedmcq(Integer totalAttemptedmcq) {
        this.totalAttemptedmcq = totalAttemptedmcq;
    }

    public Integer getTotalMcq() {
        return totalMcq;
    }

    public void setTotalMcq(Integer totalMcq) {
        this.totalMcq = totalMcq;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public String getCurrect() {
        return currect;
    }

    public void setCurrect(String currect) {
        this.currect = currect;
    }

    public String getSkipped() {
        return skipped;
    }

    public void setSkipped(String skipped) {
        this.skipped = skipped;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

}