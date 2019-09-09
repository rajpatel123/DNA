package com.dnamedical.Models.test.testp;
import com.dnamedical.Models.test.testp.QuestionData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QustionDetails {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private QuestionData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public QuestionData getData() {
        return data;
    }

    public void setData(QuestionData data) {
        this.data = data;
    }

}