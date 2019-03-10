package edu.com.medicalapp.Models.QbankSubCat;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCat {
    @SerializedName("module_id")
    @Expose
    private String moduleId;
    @SerializedName("module_name")
    @Expose
    private String moduleName;
    @SerializedName("paid_status")
    @Expose
    private String paidStatus;
    @SerializedName("totalmcq")
    @Expose
    private String totalmcq;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("copleted_status")
    @Expose
    private String copletedStatus;
    @SerializedName("paused_status")
    @Expose
    private String pausedStatus;
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

    public String getTotalmcq() {
        return totalmcq;
    }

    public void setTotalmcq(String totalmcq) {
        this.totalmcq = totalmcq;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}