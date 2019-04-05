package com.dnamedical.Models.QbankSubCat;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCat {
    @SerializedName("module_id")
    @Expose
    private String moduleId;
    @SerializedName("module_name")
    @Expose
    private String moduleName;
    @SerializedName("PaidStatus")
    @Expose
    private String paidStatus;
    @SerializedName("is_paused")
    @Expose
    private String isPaused;
    @SerializedName("is_completed")
    @Expose
    private String isCompleted;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("IsAttempted")
    @Expose
    private String isAttempted;
    @SerializedName("MCQ")
    @Expose
    private String mCQ;
    @SerializedName("image")
    @Expose
    private String image;

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getIsPaused() {
        return isPaused;
    }

    public void setIsPaused(String isPaused) {
        this.isPaused = isPaused;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getIsAttempted() {
        return isAttempted;
    }

    public void setIsAttempted(String isAttempted) {
        this.isAttempted = isAttempted;
    }

    public String getMCQ() {
        return mCQ;
    }

    public void setMCQ(String mCQ) {
        this.mCQ = mCQ;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}