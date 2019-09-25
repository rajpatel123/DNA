package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium implements Parcelable {
    @SerializedName("correct")
    @Expose
    private Integer correct;
    @SerializedName("wrong")
    @Expose
    private Integer wrong;
    @SerializedName("skip")
    @Expose
    private String skip;

    protected Medium(Parcel in) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Medium> CREATOR = new Creator<Medium>() {
        @Override
        public Medium createFromParcel(Parcel in) {
            return new Medium(in);
        }

        @Override
        public Medium[] newArray(int size) {
            return new Medium[size];
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

}