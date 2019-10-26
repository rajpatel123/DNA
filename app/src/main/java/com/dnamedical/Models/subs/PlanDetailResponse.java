package com.dnamedical.Models.subs;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanDetailResponse {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("Plans")
@Expose
private List<Plan> plans = null;

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

public List<Plan> getPlans() {
return plans;
}

public void setPlans(List<Plan> plans) {
this.plans = plans;
}

}