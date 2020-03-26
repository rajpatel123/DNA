package com.dnamedical.Models.subs;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plan {

    @SerializedName("plan_id")
    @Expose
    private String planId;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("plan_price")
    @Expose
    private String planPrice;


    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    @SerializedName("expiry_date")
    @Expose
    private String expiry_date;


    public String getPlan_expiry() {
        return plan_expiry;
    }

    public void setPlan_expiry(String plan_expiry) {
        this.plan_expiry = plan_expiry;
    }

    @SerializedName("plan_expiry")
    @Expose
    private String plan_expiry;
    @SerializedName("plan_discount")
    @Expose
    private String planDiscount;
    @SerializedName("plan_status")
    @Expose
    private String planStatus;

    @SerializedName("plan_months")
    @Expose
    private String planMonths;


    @SerializedName("coupan_code")
    @Expose
    private String coupan_code;

    public String getCoupan_code() {
        return coupan_code;
    }

    public void setCoupan_code(String coupan_code) {
        this.coupan_code = coupan_code;
    }

    public String getCoupan_value() {
        return coupan_value;
    }

    public void setCoupan_value(String coupan_value) {
        this.coupan_value = coupan_value;
    }

    @SerializedName("coupan_value")
    @Expose
    private String coupan_value;
    @SerializedName("valid_till")
    @Expose
    private long validTill;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(String planPrice) {
        this.planPrice = planPrice;
    }

    public String getPlanDiscount() {
        return planDiscount;
    }

    public void setPlanDiscount(String planDiscount) {
        this.planDiscount = planDiscount;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlanMonths() {
        return planMonths;
    }

    public void setPlanMonths(String planMonths) {
        this.planMonths = planMonths;
    }

    public long getValidTill() {
        return validTill;
    }

    public void setValidTill(long validTill) {
        this.validTill = validTill;
    }

}

