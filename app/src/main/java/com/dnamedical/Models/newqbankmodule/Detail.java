package com.dnamedical.Models.newqbankmodule;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

 public class Detail {

@SerializedName("id")
@Expose
private String id;
@SerializedName("subject_name")
@Expose
private String subjectName;
@SerializedName("subject_image")
@Expose
private String subjectImage;
@SerializedName("total_module")
@Expose
private String totalModule;
@SerializedName("total_completed")
@Expose
private Integer totalCompleted;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getSubjectName() {
return subjectName;
}

public void setSubjectName(String subjectName) {
this.subjectName = subjectName;
}

public String getSubjectImage() {
return subjectImage;
}

public void setSubjectImage(String subjectImage) {
this.subjectImage = subjectImage;
}

public String getTotalModule() {
return totalModule;
}

public void setTotalModule(String totalModule) {
this.totalModule = totalModule;
}

public Integer getTotalCompleted() {
return totalCompleted;
}

public void setTotalCompleted(Integer totalCompleted) {
this.totalCompleted = totalCompleted;
}

}

