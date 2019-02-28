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
@SerializedName("reviewDetail")
@Expose
private List<ReviewDetail> reviewDetail = null;

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

public List<ReviewDetail> getReviewDetail() {
return reviewDetail;
}

public void setReviewDetail(List<ReviewDetail> reviewDetail) {
this.reviewDetail = reviewDetail;
}

}