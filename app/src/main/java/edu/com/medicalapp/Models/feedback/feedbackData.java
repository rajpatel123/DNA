package edu.com.medicalapp.Models.feedback;

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

}
