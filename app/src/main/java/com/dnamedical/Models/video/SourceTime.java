package com.dnamedical.Models.video;

import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SourceTime {

    @SerializedName("topic_name")
    @Expose
    private String topicName;
    @SerializedName("source_time")
    @Expose
    private String sourceTime;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getSourceTime() {
        return sourceTime;
    }

    public void setSourceTime(String sourceTime) {
        this.sourceTime = sourceTime;
    }

}