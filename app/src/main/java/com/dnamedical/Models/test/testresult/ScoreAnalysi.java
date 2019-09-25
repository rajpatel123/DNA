package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScoreAnalysi implements Parcelable {
    @SerializedName("wrong")
    @Expose
    private Integer wrong;
    @SerializedName("correct")
    @Expose
    private Integer correct;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("score")
    @Expose
    private String score;

    protected ScoreAnalysi(Parcel in) {
        if (in.readByte() == 0) {
            wrong = null;
        } else {
            wrong = in.readInt();
        }
        if (in.readByte() == 0) {
            correct = null;
        } else {
            correct = in.readInt();
        }
        categoryName = in.readString();
        if (in.readByte() == 0) {
            skip = null;
        } else {
            skip = in.readInt();
        }
        score = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (wrong == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(wrong);
        }
        if (correct == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(correct);
        }
        dest.writeString(categoryName);
        if (skip == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(skip);
        }
        dest.writeString(score);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Integer getWrong() {
        return wrong;
    }

    public void setWrong(Integer wrong) {
        this.wrong = wrong;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}