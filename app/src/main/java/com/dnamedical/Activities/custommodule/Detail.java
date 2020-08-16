package com.dnamedical.Activities.custommodule;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

@SerializedName("id")
@Expose
private String id;
@SerializedName("subject_name")
@Expose
private String subjectName;

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

}
