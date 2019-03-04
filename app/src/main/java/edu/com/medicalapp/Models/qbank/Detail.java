package edu.com.medicalapp.Models.qbank;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

@SerializedName("cat_id")
@Expose
private String catId;
@SerializedName("cat_name")
@Expose
private String catName;
@SerializedName("cat_image")
@Expose
private String catImage;

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

public String getCatImage() {
return catImage;
}

public void setCatImage(String catImage) {
this.catImage = catImage;
}

}
