package com.dnamedical.Models.get_Mobile_number;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("Mobile")
@Expose
private String mobile;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

}