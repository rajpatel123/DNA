package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuessAnalysis implements Parcelable {

@SerializedName("correctToWrong")
@Expose
private Integer correctToWrong;
@SerializedName("wrongToCorrect")
@Expose
private Integer wrongToCorrect;
@SerializedName("wrongToWrong")
@Expose
private Integer wrongToWrong;
@SerializedName("totalSwitch")
@Expose
private Integer totalSwitch;

    protected GuessAnalysis(Parcel in) {
        if (in.readByte() == 0) {
            correctToWrong = null;
        } else {
            correctToWrong = in.readInt();
        }
        if (in.readByte() == 0) {
            wrongToCorrect = null;
        } else {
            wrongToCorrect = in.readInt();
        }
        if (in.readByte() == 0) {
            wrongToWrong = null;
        } else {
            wrongToWrong = in.readInt();
        }
        if (in.readByte() == 0) {
            totalSwitch = null;
        } else {
            totalSwitch = in.readInt();
        }
    }

    public static final Creator<GuessAnalysis> CREATOR = new Creator<GuessAnalysis>() {
        @Override
        public GuessAnalysis createFromParcel(Parcel in) {
            return new GuessAnalysis(in);
        }

        @Override
        public GuessAnalysis[] newArray(int size) {
            return new GuessAnalysis[size];
        }
    };

    public Integer getCorrectToWrong() {
return correctToWrong;
}

public void setCorrectToWrong(Integer correctToWrong) {
this.correctToWrong = correctToWrong;
}

public Integer getWrongToCorrect() {
return wrongToCorrect;
}

public void setWrongToCorrect(Integer wrongToCorrect) {
this.wrongToCorrect = wrongToCorrect;
}

public Integer getWrongToWrong() {
return wrongToWrong;
}

public void setWrongToWrong(Integer wrongToWrong) {
this.wrongToWrong = wrongToWrong;
}

public Integer getTotalSwitch() {
return totalSwitch;
}

public void setTotalSwitch(Integer totalSwitch) {
this.totalSwitch = totalSwitch;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (correctToWrong == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(correctToWrong);
        }
        if (wrongToCorrect == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(wrongToCorrect);
        }
        if (wrongToWrong == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(wrongToWrong);
        }
        if (totalSwitch == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalSwitch);
        }
    }
}