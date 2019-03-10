package edu.com.medicalapp.Models.qbank;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QBank {


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
    @SerializedName("paid_status")
    @Expose
    private String paidStatus;
    @SerializedName("totalmcq")
    @Expose
    private String totalmcq;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("module_name")
    @Expose
    private String moduleName;


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

    public String getTotalmcq() {
        return totalmcq;
    }

    public void setTotalmcq(String totalmcq) {
        this.totalmcq = totalmcq;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
