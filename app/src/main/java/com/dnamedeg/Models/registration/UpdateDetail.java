package com.dnamedeg.Models.registration;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateDetail {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("mobile_no")
@Expose
private String mobileNo;
@SerializedName("email_id")
@Expose
private String emailId;
@SerializedName("password")
@Expose
private String password;
@SerializedName("joining_city")
@Expose
private String joiningCity;

    public String getLogin_token() {
        return login_token;
    }

    public void setLogin_token(String login_token) {
        this.login_token = login_token;
    }

    @SerializedName("login_token")
@Expose
private String login_token;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getMobileNo() {
return mobileNo;
}

public void setMobileNo(String mobileNo) {
this.mobileNo = mobileNo;
}

public String getEmailId() {
return emailId;
}

public void setEmailId(String emailId) {
this.emailId = emailId;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getJoiningCity() {
return joiningCity;
}

public void setJoiningCity(String joiningCity) {
this.joiningCity = joiningCity;
}

}

