package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeAnalysis implements Parcelable {

    @SerializedName("change_question")
    @Expose
    private Integer changeQuestion;
    @SerializedName("change_option")
    @Expose
    private Integer changeOption;
    @SerializedName("total_time")
    @Expose
    private Integer totalTime;

    protected TimeAnalysis(Parcel in) {
        if (in.readByte() == 0) {
            changeQuestion = null;
        } else {
            changeQuestion = in.readInt();
        }
        if (in.readByte() == 0) {
            changeOption = null;
        } else {
            changeOption = in.readInt();
        }
        if (in.readByte() == 0) {
            totalTime = null;
        } else {
            totalTime = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (changeQuestion == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(changeQuestion);
        }
        if (changeOption == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(changeOption);
        }
        if (totalTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalTime);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimeAnalysis> CREATOR = new Creator<TimeAnalysis>() {
        @Override
        public TimeAnalysis createFromParcel(Parcel in) {
            return new TimeAnalysis(in);
        }

        @Override
        public TimeAnalysis[] newArray(int size) {
            return new TimeAnalysis[size];
        }
    };

    public Integer getChangeQuestion() {
        return changeQuestion;
    }

    public void setChangeQuestion(Integer changeQuestion) {
        this.changeQuestion = changeQuestion;
    }

    public Integer getChangeOption() {
        return changeOption;
    }

    public void setChangeOption(Integer changeOption) {
        this.changeOption = changeOption;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }
}
