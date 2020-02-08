package com.dnamedical.Models.allinstitutes;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllInstituteResponseModel {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("Institutes")
@Expose
private List<Institute> institutes = null;

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

public List<Institute> getInstitutes() {
return institutes;
}

public void setInstitutes(List<Institute> institutes) {
this.institutes = institutes;
}

}
