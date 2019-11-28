package com.dnamedical.Models.test;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RankResultRemarks {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("remarks")
@Expose
private String remarks;
@SerializedName("result_date")
@Expose
private String resultDate;

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

public String getRemarks() {
return remarks;
}

public void setRemarks(String remarks) {
this.remarks = remarks;
}

public String getResultDate() {
return resultDate;
}

public void setResultDate(String resultDate) {
this.resultDate = resultDate;
}

}