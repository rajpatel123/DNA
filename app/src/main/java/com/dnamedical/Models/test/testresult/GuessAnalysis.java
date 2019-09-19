package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuessAnalysis  implements Parcelable {

@SerializedName("correctToWrong")
@Expose
private int correctToWrong;
@SerializedName("wrongToCorrect")
@Expose
private int wrongToCorrect;
@SerializedName("wrongToWrong")
@Expose
private int wrongToWrong;
@SerializedName("totalSwitch")
@Expose
private int totalSwitch;


    protected GuessAnalysis(Parcel in) {
        correctToWrong = in.readInt();
        wrongToCorrect = in.readInt();
        wrongToWrong = in.readInt();
        totalSwitch = in.readInt();
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

    public int getCorrectToWrong() {
return correctToWrong;
}

public void setCorrectToWrong(int correctToWrong) {
this.correctToWrong = correctToWrong;
}

public int getWrongToCorrect() {
return wrongToCorrect;
}

public void setWrongToCorrect(int wrongToCorrect) {
this.wrongToCorrect = wrongToCorrect;
}

public int getWrongToWrong() {
return wrongToWrong;
}

public void setWrongToWrong(int wrongToWrong) {
this.wrongToWrong = wrongToWrong;
}

public int getTotalSwitch() {
return totalSwitch;
}

public void setTotalSwitch(int totalSwitch) {
this.totalSwitch = totalSwitch;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(correctToWrong);
        dest.writeInt(wrongToCorrect);
        dest.writeInt(wrongToWrong);
        dest.writeInt(totalSwitch);
    }

}