package com.dnamedical.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDetail {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("username")
@Expose
private String username;
@SerializedName("email_id")
@Expose
private String emailId;
@SerializedName("image")
@Expose
private String image;

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

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

}

