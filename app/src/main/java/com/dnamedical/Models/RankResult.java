package com.dnamedical.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RankResult {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("rank")
@Expose
private String rank;
@SerializedName("total_students")
@Expose
private Integer totalStudents;

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

public String getRank() {
return rank;
}

public void setRank(String rank) {
this.rank = rank;
}

public Integer getTotalStudents() {
return totalStudents;
}

public void setTotalStudents(Integer totalStudents) {
this.totalStudents = totalStudents;
}

}