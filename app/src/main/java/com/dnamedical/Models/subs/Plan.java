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
@SerializedName("plan_discount")
@Expose
private String planDiscount;
@SerializedName("plan_status")
@Expose
private String planStatus;
@SerializedName("plan_months")
@Expose
private String planMonths;
@SerializedName("valid_till")
@Expose
private Integer validTill;

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

public Integer getValidTill() {
return validTill;
}

public void setValidTill(Integer validTill) {
this.validTill = validTill;
}

}

