package com.dnamedical.Models.maincat;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

@SerializedName("cat_id")
@Expose
private String catId;

@SerializedName("cat_name")
@Expose
private String catName;

    public String getIns_logo() {
        return ins_logo;
    }

    public void setIns_logo(String ins_logo) {
        this.ins_logo = ins_logo;
    }

    @SerializedName("ins_logo")
@Expose
private String ins_logo;
@SerializedName("sub_cat")
@Expose
private List<SubCat> subCat = null;

private String type;

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

public List<SubCat> getSubCat() {
return subCat;
}

public void setSubCat(List<SubCat> subCat) {
this.subCat = subCat;
}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
