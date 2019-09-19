package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data implements Parcelable {

@SerializedName("rank")
@Expose
private int rank;
@SerializedName("start_time")
@Expose
private String startTime;
@SerializedName("end_time")
@Expose
private String endTime;
@SerializedName("lowest_rank")
@Expose
private int lowestRank;
@SerializedName("your_score")
@Expose
private double yourScore;
@SerializedName("total_marks")
@Expose
private int totalMarks;
@SerializedName("percenatge")
@Expose
private int percenatge;
@SerializedName("percentile")
@Expose
private int percentile;
@SerializedName("score_analysis")
@Expose
private List<ScoreAnalysi> scoreAnalysis = null;
@SerializedName("diffculty_level_analysis")
@Expose
private DiffcultyLevelAnalysis diffcultyLevelAnalysis;
@SerializedName("guess_analysis")
@Expose
private GuessAnalysis guessAnalysis;
@SerializedName("time_analysis")
@Expose
private TimeAnalysis timeAnalysis;


    protected Data(Parcel in) {
        rank = in.readInt();
        startTime = in.readString();
        endTime = in.readString();
        lowestRank = in.readInt();
        yourScore = in.readFloat();
        totalMarks = in.readInt();
        percenatge = in.readInt();
        percentile = in.readInt();
        scoreAnalysis = in.createTypedArrayList(ScoreAnalysi.CREATOR);
        diffcultyLevelAnalysis = in.readParcelable(DiffcultyLevelAnalysis.class.getClassLoader());
        guessAnalysis = in.readParcelable(GuessAnalysis.class.getClassLoader());
        timeAnalysis = in.readParcelable(TimeAnalysis.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rank);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeInt(lowestRank);
        dest.writeDouble(yourScore);
        dest.writeInt(totalMarks);
        dest.writeInt(percenatge);
        dest.writeInt(percentile);
        dest.writeTypedList(scoreAnalysis);
        dest.writeParcelable(diffcultyLevelAnalysis, flags);
        dest.writeParcelable(guessAnalysis, flags);
        dest.writeParcelable(timeAnalysis, flags);
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public int getRank() {
return rank;
}

public void setRank(int rank) {
this.rank = rank;
}

public String getStartTime() {
return startTime;
}

public void setStartTime(String startTime) {
this.startTime = startTime;
}

public String getEndTime() {
return endTime;
}

public void setEndTime(String endTime) {
this.endTime = endTime;
}

public int getLowestRank() {
return lowestRank;
}

public void setLowestRank(int lowestRank) {
this.lowestRank = lowestRank;
}

public double getYourScore() {
return yourScore;
}

public void setYourScore(Float yourScore) {
this.yourScore = yourScore;
}

public int getTotalMarks() {
return totalMarks;
}

public void setTotalMarks(int totalMarks) {
this.totalMarks = totalMarks;
}

public int getPercenatge() {
return percenatge;
}

public void setPercenatge(int percenatge) {
this.percenatge = percenatge;
}

public int getPercentile() {
return percentile;
}

public void setPercentile(int percentile) {
this.percentile = percentile;
}

public List<ScoreAnalysi> getScoreAnalysis() {
return scoreAnalysis;
}

public void setScoreAnalysis(List<ScoreAnalysi> scoreAnalysis) {
this.scoreAnalysis = scoreAnalysis;
}

public DiffcultyLevelAnalysis getDiffcultyLevelAnalysis() {
return diffcultyLevelAnalysis;
}

public void setDiffcultyLevelAnalysis(DiffcultyLevelAnalysis diffcultyLevelAnalysis) {
this.diffcultyLevelAnalysis = diffcultyLevelAnalysis;
}

public GuessAnalysis getGuessAnalysis() {
return guessAnalysis;
}

public void setGuessAnalysis(GuessAnalysis guessAnalysis) {
this.guessAnalysis = guessAnalysis;
}

public TimeAnalysis getTimeAnalysis() {
return timeAnalysis;
}

public void setTimeAnalysis(TimeAnalysis timeAnalysis) {
this.timeAnalysis = timeAnalysis;
}

    @Override
    public int describeContents() {
        return 0;
    }

}