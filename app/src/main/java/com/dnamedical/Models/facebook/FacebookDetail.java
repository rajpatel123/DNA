package com.dnamedical.Models.facebook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacebookDetail {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("username")
@Expose
private String username;
@SerializedName("email_id")
@Expose
private String emailId;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getEmailId() {
return emailId;
}

public void setEmailId(String emailId) {
this.emailId = emailId;
}

}