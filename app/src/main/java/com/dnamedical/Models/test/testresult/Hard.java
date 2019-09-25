package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hard implements Parcelable {
    @SerializedName("correct")
    @Expose
    private Integer correct;
    @SerializedName("wrong")
    @Expose
    private Integer wrong;
    @SerializedName("skip")
    @Expose
    private Integer skip;

    protected Hard(Parcel in) {
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
        if (in.readByte() == 0) {
            skip = null;
        } else {
            skip = in.readInt();
        }
    }

    public static final Creator<Hard> CREATOR = new Creator<Hard>() {
        @Override
        public Hard createFromParcel(Parcel in) {
            return new Hard(in);
        }

        @Override
        public Hard[] newArray(int size) {
            return new Hard[size];
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

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
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
        if (skip == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(skip);
        }
    }
}

