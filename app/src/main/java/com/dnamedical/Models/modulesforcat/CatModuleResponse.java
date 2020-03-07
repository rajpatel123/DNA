package com.dnamedical.Models.modulesforcat;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatModuleResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("Modules")
@Expose
private List<Module> modules = null;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Module> getModules() {
return modules;
}

public void setModules(List<Module> modules) {
this.modules = modules;
}

}