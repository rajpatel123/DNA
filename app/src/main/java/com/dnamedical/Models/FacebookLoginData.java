package com.dnamedical.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacebookLoginData {

@SerializedName("id")
@Expose
private String id;


    @SerializedName("email")
    @Expose
    private String email;

@SerializedName("name")
@Expose
private String name;
@SerializedName("picture")
@Expose
private Picture picture;


    @SerializedName("url")
    @Expose
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

public Picture getPicture() {
return picture;
}

public void setPicture(Picture picture) {
this.picture = picture;

}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
