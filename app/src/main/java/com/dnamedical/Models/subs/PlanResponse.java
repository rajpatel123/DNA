package com.dnamedical.Models.subs;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Individual Plan")
    @Expose
    private List<IndividualPlan> individualPlan = null;
    @SerializedName("Combo Pack")
    @Expose
    private List<Object> comboPack = null;

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

    public List<IndividualPlan> getIndividualPlan() {
        return individualPlan;
    }

    public void setIndividualPlan(List<IndividualPlan> individualPlan) {
        this.individualPlan = individualPlan;
    }

    public List<Object> getComboPack() {
        return comboPack;
    }

    public void setComboPack(List<Object> comboPack) {
        this.comboPack = comboPack;
    }
}
