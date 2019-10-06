package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeAnalysis implements Parcelable {

    @SerializedName("change_question")
    @Expose
    private String changeQuestion;
    @SerializedName("change_option")
    @Expose
    private String changeOption;
    @SerializedName("total_time")
    @Expose
    private Integer totalTime;

    protected TimeAnalysis(Parcel in) {
        changeQuestion = in.readString();
        changeOption = in.readString();
        if (in.readByte() == 0) {
            totalTime = null;
        } else {
            totalTime = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(changeQuestion);
        dest.writeString(changeOption);
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

    public String getChangeQuestion() {
        return changeQuestion;
    }

    public void setChangeQuestion(String changeQuestion) {
        this.changeQuestion = changeQuestion;
    }

    public String getChangeOption() {
        return changeOption;
    }

    public void setChangeOption(String changeOption) {
        this.changeOption = changeOption;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

}