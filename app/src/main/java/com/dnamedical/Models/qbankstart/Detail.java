package com.dnamedical.Models.qbankstart;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

@SerializedName("module_id")
@Expose
private String moduleId;
@SerializedName("module_name")
@Expose
private String moduleName;
@SerializedName("totalmcq")
@Expose
private String totalmcq;
@SerializedName("totalattempedmcq")
@Expose
private String totalattempedmcq;
@SerializedName("lastattempedquesdate")
@Expose
private String lastattempedquesdate;

public String getModuleId() {
return moduleId;
}

public void setModuleId(String moduleId) {
this.moduleId = moduleId;
}

public String getModuleName() {
return moduleName;
}

public void setModuleName(String moduleName) {
this.moduleName = moduleName;
}

public String getTotalmcq() {
return totalmcq;
}

public void setTotalmcq(String totalmcq) {
this.totalmcq = totalmcq;
}

public String getTotalattempedmcq() {
return totalattempedmcq;
}

public void setTotalattempedmcq(String totalattempedmcq) {
this.totalattempedmcq = totalattempedmcq;
}
public String getLastattempedquesdate() {
return lastattempedquesdate;
}

public void setLastattempedquesdate(String lastattempedquesdate) {
this.lastattempedquesdate = lastattempedquesdate;
}

}
