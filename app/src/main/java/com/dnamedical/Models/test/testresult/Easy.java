package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Easy implements Parcelable {
    @SerializedName("correct")
    @Expose
    private Integer correct;
    @SerializedName("wrong")
    @Expose
    private Integer wrong;
    @SerializedName("skip")
    @Expose
    private String skip;

    protected Easy(Parcel in) {
        if (in.readByte() == 0) {
            correct = null;
        } else {
            correct = in.readInt();
        }
        if (in.readByte() == 0) {
            wrong = null;
        } else {
            wrong = in.readInt();
        }
        skip = in.readString();
    }

    public static final Creator<Easy> CREATOR = new Creator<Easy>() {
        @Override
        public Easy createFromParcel(Parcel in) {
            return new Easy(in);
        }

        @Override
        public Easy[] newArray(int size) {
            return new Easy[size];
        }
    };

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getWrong() {
        return wrong;
    }

    public void setWrong(Integer wrong) {
        this.wrong = wrong;
    }

    public String getSkip() {
        return skip;
    }

    public void setSkip(String skip) {
        this.skip = skip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (correct == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(correct);
        }
        if (wrong == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(wrong);
        }
        dest.writeString(skip);
    }
}
