package com.dnamedical.Models.updateAddress;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressDetail {

@SerializedName("a_id")
@Expose
private String aId;
@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("name")
@Expose
private String name;
@SerializedName("mobile")
@Expose
private String mobile;
@SerializedName("email")
@Expose
private String email;
@SerializedName("address_line1")
@Expose
private String addressLine1;
@SerializedName("address_line2")
@Expose
private String addressLine2;
@SerializedName("city")
@Expose
private String city;
@SerializedName("state")
@Expose
private String state;
@SerializedName("pin_code")
@Expose
private String pinCode;

public String getAId() {
return aId;
}

public void setAId(String aId) {
this.aId = aId;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getAddressLine1() {
return addressLine1;
}

public void setAddressLine1(String addressLine1) {
this.addressLine1 = addressLine1;
}

public String getAddressLine2() {
return addressLine2;
}

public void setAddressLine2(String addressLine2) {
this.addressLine2 = addressLine2;
}

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

public String getPinCode() {
return pinCode;
}

public void setPinCode(String pinCode) {
this.pinCode = pinCode;
}

}
