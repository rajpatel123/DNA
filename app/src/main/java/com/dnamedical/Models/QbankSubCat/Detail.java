package com.dnamedical.Models.QbankSubCat;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

@SerializedName("id")
@Expose
private String id;
@SerializedName("cat_id")
@Expose
private String catId;
@SerializedName("sub_cat_name")
@Expose
private String subCatName;
@SerializedName("image")
@Expose
private String image;
@SerializedName("sub_cat")
@Expose
private List<SubCat> subCat = null;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getCatId() {
return catId;
}

public void setCatId(String catId) {
this.catId = catId;
}

public String getSubCatName() {
return subCatName;
}

public void setSubCatName(String subCatName) {
this.subCatName = subCatName;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public List<SubCat> getSubCat() {
return subCat;
}

public void setSubCat(List<SubCat> subCat) {
this.subCat = subCat;
}

}
