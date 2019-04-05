package com.dnamedical.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Course {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("courseName")
@Expose
private String courseName;
@SerializedName("courseDescription")
@Expose
private String courseDescription;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getCourseName() {
return courseName;
}

public void setCourseName(String courseName) {
this.courseName = courseName;
}

public String getCourseDescription() {
return courseDescription;
}

public void setCourseDescription(String courseDescription) {
this.courseDescription = courseDescription;
}

}