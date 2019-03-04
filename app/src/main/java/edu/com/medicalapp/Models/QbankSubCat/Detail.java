package edu.com.medicalapp.Models.QbankSubCat;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

@SerializedName("subcat_id")
@Expose
private String subcatId;
@SerializedName("subcat_name")
@Expose
private String subcatName;
@SerializedName("subcat_image")
@Expose
private String subcatImage;

public String getSubcatId() {
return subcatId;
}

public void setSubcatId(String subcatId) {
this.subcatId = subcatId;
}

public String getSubcatName() {
return subcatName;
}

public void setSubcatName(String subcatName) {
this.subcatName = subcatName;
}

public String getSubcatImage() {
return subcatImage;
}

public void setSubcatImage(String subcatImage) {
this.subcatImage = subcatImage;
}

}
