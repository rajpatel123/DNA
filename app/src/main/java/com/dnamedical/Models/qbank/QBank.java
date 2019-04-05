package com.dnamedical.Models.qbank;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QBank {

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

    public String getIsAttempted() {
        return isAttempted;
    }

    public void setIsAttempted(String isAttempted) {
        this.isAttempted = isAttempted;
    }

    public String getmCQ() {
        return mCQ;
    }

    public void setmCQ(String mCQ) {
        this.mCQ = mCQ;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("sub_cat_name")
    @Expose
    private String subCatName;


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


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCopletedStatus() {
        return copletedStatus;
    }

    public void setCopletedStatus(String copletedStatus) {
        this.copletedStatus = copletedStatus;
    }

    public String getPausedStatus() {
        return pausedStatus;
    }

    public void setPausedStatus(String pausedStatus) {
        this.pausedStatus = pausedStatus;
    }

    @SerializedName("copleted_status")
    @Expose
    private String copletedStatus;
    @SerializedName("paused_status")
    @Expose
    private String pausedStatus;

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


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
