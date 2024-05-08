package com.dnamedical.Models.verify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpModel {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("user")
@Expose
private User user;
@SerializedName("isNew")
@Expose
private Boolean isNew;

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

public User getUser() {
return user;
}

public void setUser(User user) {
this.user = user;
}

public Boolean getIsNew() {
return isNew;
}

public void setIsNew(Boolean isNew) {
this.isNew = isNew;
}

}