package com.dnamedical.Models.login;

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
    @SerializedName("college")
    @Expose
    private String college;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("image")
    @Expose
    private String image;


    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getInstitute_name() {
        return institute_name;
    }

    public void setInstitute_name(String institute_name) {
        this.institute_name = institute_name;
    }

    @SerializedName("institute_id")
    @Expose
    private String institute_id;


    @SerializedName("institute_name")
    @Expose
    private String institute_name;

    public String getInstitute_logo() {
        return institute_logo;
    }

    public void setInstitute_logo(String institute_logo) {
        this.institute_logo = institute_logo;
    }

    @SerializedName("institute_logo")
    @Expose
    private String institute_logo;

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

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}