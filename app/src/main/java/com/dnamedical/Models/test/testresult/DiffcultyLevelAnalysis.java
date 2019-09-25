package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiffcultyLevelAnalysis implements Parcelable {
    @SerializedName("easy")
    @Expose
    private Easy easy;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("hard")
    @Expose
    private Hard hard;

    protected DiffcultyLevelAnalysis(Parcel in) {
        easy = in.readParcelable(Easy.class.getClassLoader());
        medium = in.readParcelable(Medium.class.getClassLoader());
        hard = in.readParcelable(Hard.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(easy, flags);
        dest.writeParcelable(medium, flags);
        dest.writeParcelable(hard, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DiffcultyLevelAnalysis> CREATOR = new Creator<DiffcultyLevelAnalysis>() {
        @Override
        public DiffcultyLevelAnalysis createFromParcel(Parcel in) {
            return new DiffcultyLevelAnalysis(in);
        }

        @Override
        public DiffcultyLevelAnalysis[] newArray(int size) {
            return new DiffcultyLevelAnalysis[size];
        }
    };

    public Easy getEasy() {
        return easy;
    }

    public void setEasy(Easy easy) {
        this.easy = easy;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Hard getHard() {
        return hard;
    }

    public void setHard(Hard hard) {
        this.hard = hard;
    }

}