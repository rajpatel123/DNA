package com.dnamedical.Models.ResultData;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultList {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("User_Result")
@Expose
private List<UserResult> userResult = null;
@SerializedName("All_Reult")
@Expose
private List<AllReult> allReult = null;

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

public List<UserResult> getUserResult() {
return userResult;
}

public void setUserResult(List<UserResult> userResult) {
this.userResult = userResult;
}

public List<AllReult> getAllReult() {
return allReult;
}

public void setAllReult(List<AllReult> allReult) {
this.allReult = allReult;
}

}