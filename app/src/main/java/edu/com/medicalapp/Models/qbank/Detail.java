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
    @SerializedName("totalmodules")
    @Expose
    private String totalmodules;
    @SerializedName("completedmodules")
    @Expose
    private String completedmodules;
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

    public String getTotalmodules() {
        return totalmodules;
    }

    public void setTotalmodules(String totalmodules) {
        this.totalmodules = totalmodules;
    }

    public String getCompletedmodules() {
        return completedmodules;
    }

    public void setCompletedmodules(String completedmodules) {
        this.completedmodules = completedmodules;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

}
