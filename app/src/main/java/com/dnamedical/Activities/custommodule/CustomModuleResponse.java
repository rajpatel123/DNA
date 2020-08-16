package com.dnamedical.Activities.custommodule;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomModuleResponse {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("details")
@Expose
private ModuleDetails details;

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

public ModuleDetails getDetails() {
return details;
}

public void setDetails(ModuleDetails details) {
this.details = details;
}

}

