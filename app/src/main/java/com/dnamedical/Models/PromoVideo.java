package com.dnamedical.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoVideo {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;

    public List<VideoModel> getVideoModels() {
        return videoModels;
    }

    public void setVideoModels(List<VideoModel> videoModels) {
        this.videoModels = videoModels;
    }

    @SerializedName("name")
@Expose
private List<VideoModel> videoModels = null;

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


}