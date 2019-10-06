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
private Integer skip;

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
        if (in.readByte() == 0) {
            skip = null;
        } else {
            skip = in.readInt();
        }
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
    public void writeToParcel(Parcel parcel, int i) {
        if (correct == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(correct);
        }
        if (wrong == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(wrong);
        }
        if (skip == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(skip);
        }
    }
}