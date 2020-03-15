package com.dnamedical.Models.acadamic;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;

@SerializedName("details")
@Expose
private List<CourseDetail> courseDetails = null;

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

public List<CourseDetail> getCourseDetails() {
return courseDetails;
}

public void setCourseDetails(List<CourseDetail> courseDetails) {
this.courseDetails = courseDetails;
}

}
