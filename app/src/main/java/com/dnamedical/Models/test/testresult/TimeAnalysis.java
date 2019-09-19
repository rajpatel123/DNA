package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeAnalysis implements Parcelable {

    @SerializedName("change_question")
    @Expose
    private int changeQuestion;
    @SerializedName("change_option")
    @Expose
    private int changeOption;
    @SerializedName("total_time")
    @Expose
    private int totalTime;


    protected TimeAnalysis(Parcel in) {
        changeQuestion = in.readInt();
        changeOption = in.readInt();
        totalTime = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(changeQuestion);
        dest.writeInt(changeOption);
        dest.writeInt(totalTime);
    }


    public int getChangeQuestion() {
        return changeQuestion;
    }

    public void setChangeQuestion(int changeQuestion) {
        this.changeQuestion = changeQuestion;
    }

    public int getChangeOption() {
        return changeOption;
    }

    public void setChangeOption(int changeOption) {
        this.changeOption = changeOption;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
