package com.dnamedical.Models.Enter_Mobile;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailByFBResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("email")
@Expose
private String email;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

}