package com.dnamedical.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserUpdateResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("Update_detail")
@Expose
private List<UpdateDetail> updateDetail = null;

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

public List<UpdateDetail> getUpdateDetail() {
return updateDetail;
}

public void setUpdateDetail(List<UpdateDetail> updateDetail) {
this.updateDetail = updateDetail;
}

}
