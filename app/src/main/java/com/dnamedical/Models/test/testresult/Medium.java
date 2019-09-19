package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium implements Parcelable {

@SerializedName("correct")
@Expose
private int correct;
@SerializedName("wrong")
@Expose
private int wrong;
@SerializedName("skip")
@Expose
private int skip;


    protected Medium(Parcel in) {
        correct = in.readInt();
        wrong = in.readInt();
        skip = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(correct);
        dest.writeInt(wrong);
        dest.writeInt(skip);
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

}
