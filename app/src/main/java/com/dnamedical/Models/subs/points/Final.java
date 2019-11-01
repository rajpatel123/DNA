package com.dnamedical.Models.subs.points;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Final {

@SerializedName("planName")
@Expose
private String planName;
@SerializedName("allPoints")
@Expose
private List<String> allPoints = null;

public String getPlanName() {
return planName;
}

public void setPlanName(String planName) {
this.planName = planName;
}

public List<String> getAllPoints() {
return allPoints;
}

public void setAllPoints(List<String> allPoints) {
this.allPoints = allPoints;
}

}