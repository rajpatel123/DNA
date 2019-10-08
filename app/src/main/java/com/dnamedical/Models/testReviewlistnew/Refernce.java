package com.dnamedical.Models.testReviewlistnew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Refernce {

@SerializedName("image")
@Expose
private String image;
@SerializedName("title")
@Expose
private String title;

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

}