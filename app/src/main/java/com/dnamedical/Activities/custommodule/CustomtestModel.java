package com.dnamedical.Activities.custommodule;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomtestModel {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("details")
@Expose
private List<CustomtestDetail> details = null;

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

public List<CustomtestDetail> getDetails() {
return details;
}

public void setDetails(List<CustomtestDetail> details) {
this.details = details;
}

}

