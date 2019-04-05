package com.dnamedical.Models.feedback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class feedbackData {


    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("qmodule_id")
    @Expose
    private String qmodule_id;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("feedback")
    @Expose
    private String feedback;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getQmodule_id() {
        return qmodule_id;
    }

    public void setQmodule_id(String qmodule_id) {
        this.qmodule_id = qmodule_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }


}
