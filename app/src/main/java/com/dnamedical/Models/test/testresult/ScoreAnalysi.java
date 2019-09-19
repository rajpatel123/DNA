package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScoreAnalysi implements Parcelable {

@SerializedName("wrong")
@Expose
private int wrong;
@SerializedName("correct")
@Expose
private int correct;
@SerializedName("category_name")
@Expose
private String categoryName;
@SerializedName("skip")
@Expose
private int skip;
@SerializedName("score")
@Expose
private int score;


    protected ScoreAnalysi(Parcel in) {
        wrong = in.readInt();
        correct = in.readInt();
        categoryName = in.readString();
        skip = in.readInt();
        score = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(wrong);
        dest.writeInt(correct);
        dest.writeString(categoryName);
        dest.writeInt(skip);
        dest.writeInt(score);
    }

    public static final Creator<ScoreAnalysi> CREATOR = new Creator<ScoreAnalysi>() {
        @Override
        public ScoreAnalysi createFromParcel(Parcel in) {
            return new ScoreAnalysi(in);
        }

        @Override
        public ScoreAnalysi[] newArray(int size) {
            return new ScoreAnalysi[size];
        }
    };

    public int getWrong() {
return wrong;
}

public void setWrong(int wrong) {
this.wrong = wrong;
}

public int getCorrect() {
return correct;
}

public void setCorrect(int correct) {
this.correct = correct;
}

public String getCategoryName() {
return categoryName;
}

public void setCategoryName(String categoryName) {
this.categoryName = categoryName;
}

public int getSkip() {
return skip;
}

public void setSkip(int skip) {
this.skip = skip;
}

public int getScore() {
return score;
}

public void setScore(int score) {
this.score = score;
}

    @Override
    public int describeContents() {
        return 0;
    }

}
