package com.dnamedical.Models.video;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SourceTime implements Parcelable {

    @SerializedName("topic_name")
    @Expose
    private String topicName;
    @SerializedName("source_time")
    @Expose
    private String sourceTime;

    protected SourceTime(Parcel in) {
        topicName = in.readString();
        sourceTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(topicName);
        dest.writeString(sourceTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SourceTime> CREATOR = new Creator<SourceTime>() {
        @Override
        public SourceTime createFromParcel(Parcel in) {
            return new SourceTime(in);
        }

        @Override
        public SourceTime[] newArray(int size) {
            return new SourceTime[size];
        }
    };

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