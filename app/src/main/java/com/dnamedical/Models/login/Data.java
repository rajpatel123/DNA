package com.dnamedical.Models.login;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

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
@SerializedName("mobile_no")
@Expose
private String mobileNo;
@SerializedName("image")
@Expose
private String image;
@SerializedName("course_type")
@Expose
private Object courseType;
@SerializedName("board_name")
@Expose
private Object boardName;
@SerializedName("password")
@Expose
private String password;
@SerializedName("fb_id")
@Expose
private String fbId;
@SerializedName("state")
@Expose
private String state;
@SerializedName("college")
@Expose
private String college;
@SerializedName("status")
@Expose
private String status;
@SerializedName("isreal")
@Expose
private String isreal;
@SerializedName("mverify_code")
@Expose
private String mverifyCode;
@SerializedName("otp_status")
@Expose
private String otpStatus;
@SerializedName("realdate")
@Expose
private String realdate;
@SerializedName("joining_address")
@Expose
private String joiningAddress;
@SerializedName("joining_city")
@Expose
private String joiningCity;
@SerializedName("demo_link")
@Expose
private String demoLink;
@SerializedName("location")
@Expose
private Object location;
@SerializedName("joining_country")
@Expose
private String joiningCountry;
@SerializedName("platform")
@Expose
private String platform;
@SerializedName("device_id")
@Expose
private Object deviceId;
@SerializedName("fcm_token")
@Expose
private String fcmToken;
@SerializedName("institute_id")
@Expose
private String instituteId;
@SerializedName("academic_year_id")
@Expose
private Object academicYearId;
@SerializedName("device_count")
@Expose
private String deviceCount;
@SerializedName("mobile_verified")
@Expose
private String mobileVerified;
@SerializedName("email_verified")
@Expose
private String emailVerified;
@SerializedName("created_on")
@Expose
private String createdOn;
@SerializedName("isrealuser")
@Expose
private String isrealuser;

@SerializedName("expire")
@Expose
private String expire;

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getIs_ambassador() {
        return is_ambassador;
    }

    public void setIs_ambassador(String is_ambassador) {
        this.is_ambassador = is_ambassador;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    @SerializedName("is_ambassador")
@Expose
private String is_ambassador;

@SerializedName("referral_code")
@Expose
private String referral_code;

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

public String getMobileNo() {
return mobileNo;
}

public void setMobileNo(String mobileNo) {
this.mobileNo = mobileNo;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public Object getCourseType() {
return courseType;
}

public void setCourseType(Object courseType) {
this.courseType = courseType;
}

public Object getBoardName() {
return boardName;
}

public void setBoardName(Object boardName) {
this.boardName = boardName;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getFbId() {
return fbId;
}

public void setFbId(String fbId) {
this.fbId = fbId;
}

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

public String getCollege() {
return college;
}

public void setCollege(String college) {
this.college = college;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getIsreal() {
return isreal;
}

public void setIsreal(String isreal) {
this.isreal = isreal;
}

public String getMverifyCode() {
return mverifyCode;
}

public void setMverifyCode(String mverifyCode) {
this.mverifyCode = mverifyCode;
}

public String getOtpStatus() {
return otpStatus;
}

public void setOtpStatus(String otpStatus) {
this.otpStatus = otpStatus;
}

public String getRealdate() {
return realdate;
}

public void setRealdate(String realdate) {
this.realdate = realdate;
}

public String getJoiningAddress() {
return joiningAddress;
}

public void setJoiningAddress(String joiningAddress) {
this.joiningAddress = joiningAddress;
}

public String getJoiningCity() {
return joiningCity;
}

public void setJoiningCity(String joiningCity) {
this.joiningCity = joiningCity;
}

public String getDemoLink() {
return demoLink;
}

public void setDemoLink(String demoLink) {
this.demoLink = demoLink;
}

public Object getLocation() {
return location;
}

public void setLocation(Object location) {
this.location = location;
}

public String getJoiningCountry() {
return joiningCountry;
}

public void setJoiningCountry(String joiningCountry) {
this.joiningCountry = joiningCountry;
}

public String getPlatform() {
return platform;
}

public void setPlatform(String platform) {
this.platform = platform;
}

public Object getDeviceId() {
return deviceId;
}

public void setDeviceId(Object deviceId) {
this.deviceId = deviceId;
}

public String getFcmToken() {
return fcmToken;
}

public void setFcmToken(String fcmToken) {
this.fcmToken = fcmToken;
}

public String getInstituteId() {
return instituteId;
}

public void setInstituteId(String instituteId) {
this.instituteId = instituteId;
}

public Object getAcademicYearId() {
return academicYearId;
}

public void setAcademicYearId(Object academicYearId) {
this.academicYearId = academicYearId;
}

public String getDeviceCount() {
return deviceCount;
}

public void setDeviceCount(String deviceCount) {
this.deviceCount = deviceCount;
}

public String getMobileVerified() {
return mobileVerified;
}

public void setMobileVerified(String mobileVerified) {
this.mobileVerified = mobileVerified;
}

public String getEmailVerified() {
return emailVerified;
}

public void setEmailVerified(String emailVerified) {
this.emailVerified = emailVerified;
}

public String getCreatedOn() {
return createdOn;
}

public void setCreatedOn(String createdOn) {
this.createdOn = createdOn;
}

public String getIsrealuser() {
return isrealuser;
}

public void setIsrealuser(String isrealuser) {
this.isrealuser = isrealuser;
}

}
