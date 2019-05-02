package com.dnamedical.Models.StateList;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

@SerializedName("ID")
@Expose
private String iD;
@SerializedName("StateName")
@Expose
private String stateName;
@SerializedName("college")
@Expose
private List<College> college = null;

public String getID() {
return iD;
}

public void setID(String iD) {
this.iD = iD;
}

public String getStateName() {
return stateName;
}

public void setStateName(String stateName) {
this.stateName = stateName;
}

public List<College> getCollege() {
return college;
}

public void setCollege(List<College> college) {
this.college = college;
}

}