package com.dnamedical.Models.Enter_Mobile;





import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnterMobileresponce {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("mobile")
@Expose
private String mobile;

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

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

}