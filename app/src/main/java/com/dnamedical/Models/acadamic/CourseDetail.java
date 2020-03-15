package com.dnamedical.Models.acadamic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseDetail {

@SerializedName("cat_id")
@Expose
private String catId;
@SerializedName("cat_name")
@Expose
private String catName;

public String getCatId() {
return catId;
}

public void setCatId(String catId) {
this.catId = catId;
}

public String getCatName() {
return catName;
}

public void setCatName(String catName) {
this.catName = catName;
}

}