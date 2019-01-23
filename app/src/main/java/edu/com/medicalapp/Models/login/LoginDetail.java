package com.videoapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDetail {

@SerializedName("id")
@Expose
private String id;
@SerializedName("user_name")
@Expose
private String userName;
@SerializedName("email_id")
@Expose
private String emailId;
@SerializedName("mobile_no")
@Expose
private String mobileNo;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}

public String getEmailId() {
return emailId;
}

public void setEmailId(String emailId) {
this.emailId = emailId;
}

public String getMobileNo() {
return mobileNo;
}

public void setMobileNo(String mobileNo) {
this.mobileNo = mobileNo;
}

}

