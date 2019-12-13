package com.dnamedical.Models.newqbankmodule;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChaptersModuleResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Free")
    @Expose
    private List<Module> free = null;
    @SerializedName("Paid")
    @Expose
    private List<Module> paid = null;
    @SerializedName("Paused")
    @Expose
    private List<Module> paused = null;
    @SerializedName("Completed")
    @Expose
    private List<Module> completed = null;
    @SerializedName("Unattempted")
    @Expose
    private List<Module> unattempted = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Module> getFree() {
        return free;
    }

    public void setFree(List<Module> free) {
        this.free = free;
    }

    public List<Module> getPaid() {
        return paid;
    }

    public void setPaid(List<Module> paid) {
        this.paid = paid;
    }

    public List<Module> getPaused() {
        return paused;
    }

    public void setPaused(List<Module> paused) {
        this.paused = paused;
    }

    public List<Module> getCompleted() {
        return completed;
    }

    public void setCompleted(List<Module> completed) {
        this.completed = completed;
    }

    public List<Module> getUnattempted() {
        return unattempted;
    }

    public void setUnattempted(List<Module> unattempted) {
        this.unattempted = unattempted;
    }

}