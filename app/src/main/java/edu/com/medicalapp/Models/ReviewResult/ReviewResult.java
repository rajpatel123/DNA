package edu.com.medicalapp.Models.ReviewResult;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewResult {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("detail")
@Expose
private List<ReviewDetail> detail = null;

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

public List<ReviewDetail> getDetail() {
return detail;
}

public void setDetail(List<ReviewDetail> detail) {
this.detail = detail;
}

}