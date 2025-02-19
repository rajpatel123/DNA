package com.dnamedeg.Models.verify;

import com.dnamedeg.Models.registration.UpdateDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class VerifyOtpModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Update_detail")
    @Expose
    private List<UpdateDetail> updateDetail;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UpdateDetail> getUpdateDetail() {
        return updateDetail;
    }

    public void setUpdateDetail(List<UpdateDetail> updateDetail) {
        this.updateDetail = updateDetail;
    }

}
