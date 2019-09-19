package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Easy implements Parcelable {

@SerializedName("correct")
@Expose
private int correct;
@SerializedName("wrong")
@Expose
private int wrong;
@SerializedName("skip")
@Expose
private int skip;


    protected Easy(Parcel in) {
        correct = in.readInt();
        wrong = in.readInt();
        skip = in.readInt();
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

    public int getCorrect() {
return correct;
}

public void setCorrect(int correct) {
this.correct = correct;
}

public int getWrong() {
return wrong;
}

public void setWrong(int wrong) {
this.wrong = wrong;
}

public int getSkip() {
return skip;
}

public void setSkip(int skip) {
this.skip = skip;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(correct);
        dest.writeInt(wrong);
        dest.writeInt(skip);
    }

}