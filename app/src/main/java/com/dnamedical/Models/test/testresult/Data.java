package com.dnamedical.Models.test.testresult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data implements Parcelable {

    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("start_time")
    @Expose
    private Integer startTime;

    @SerializedName("end_time")
    @Expose
    private Integer endTime;
    @SerializedName("lowest_rank")
    @Expose
    private Integer lowestRank;
    @SerializedName("your_score")
    @Expose
    private String yourScore;
    @SerializedName("total_marks")
    @Expose
    private String totalMarks;
    @SerializedName("percenatge")
    @Expose
    private String percenatge;
    @SerializedName("percentile")
    @Expose
    private Integer percentile;
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

    @SerializedName("test_name")
    @Expose
    private String testName;


    protected Data(Parcel in) {
        if (in.readByte() == 0) {
            rank = null;
        } else {
            rank = in.readInt();
        }
        if (in.readByte() == 0) {
            startTime = null;
        } else {
            startTime = in.readInt();
        }
        if (in.readByte() == 0) {
            endTime = null;
        } else {
            endTime = in.readInt();
        }
        if (in.readByte() == 0) {
            lowestRank = null;
        } else {
            lowestRank = in.readInt();
        }
        yourScore = in.readString();
        totalMarks = in.readString();
        percenatge = in.readString();
        if (in.readByte() == 0) {
            percentile = null;
        } else {
            percentile = in.readInt();
        }
        scoreAnalysis = in.createTypedArrayList(ScoreAnalysi.CREATOR);
        diffcultyLevelAnalysis = in.readParcelable(DiffcultyLevelAnalysis.class.getClassLoader());
        guessAnalysis = in.readParcelable(GuessAnalysis.class.getClassLoader());
        timeAnalysis = in.readParcelable(TimeAnalysis.class.getClassLoader());
        testName = in.readString();
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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getLowestRank() {
        return lowestRank;
    }

    public void setLowestRank(Integer lowestRank) {
        this.lowestRank = lowestRank;
    }

    public String getYourScore() {
        return yourScore;
    }

    public void setYourScore(String yourScore) {
        this.yourScore = yourScore;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getPercenatge() {
        return percenatge;
    }

    public void setPercenatge(String percenatge) {
        this.percenatge = percenatge;
    }

    public Integer getPercentile() {
        return percentile;
    }

    public void setPercentile(Integer percentile) {
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

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (rank == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(rank);
        }
        if (startTime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(startTime);
        }
        if (endTime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(endTime);
        }
        if (lowestRank == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(lowestRank);
        }
        parcel.writeString(yourScore);
        parcel.writeString(totalMarks);
        parcel.writeString(percenatge);
        if (percentile == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(percentile);
        }
        parcel.writeTypedList(scoreAnalysis);
        parcel.writeParcelable(diffcultyLevelAnalysis, i);
        parcel.writeParcelable(guessAnalysis, i);
        parcel.writeParcelable(timeAnalysis, i);
        parcel.writeString(testName);
    }
}