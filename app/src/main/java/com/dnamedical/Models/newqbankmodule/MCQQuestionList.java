package com.dnamedical.Models.newqbankmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MCQQuestionList {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("total mcqs")
    @Expose
    private Integer totalMcqs;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getTotalMcqs() {
        return totalMcqs;
    }

    public void setTotalMcqs(Integer totalMcqs) {
        this.totalMcqs = totalMcqs;
    }

}

