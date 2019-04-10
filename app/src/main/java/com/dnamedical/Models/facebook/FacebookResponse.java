package com.dnamedical.Models.facebook;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.dnamedical.Models.login.LoginDetail;

public class FacebookResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("login details")
@Expose
private List<FacebookDetail> facebookDetails = null;

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

public List<FacebookDetail> getFacebookDetails() {
return facebookDetails;
}

public void setFacebookDetails(List<FacebookDetail> facebookDetails) {
    this.facebookDetails=facebookDetails;
}

}