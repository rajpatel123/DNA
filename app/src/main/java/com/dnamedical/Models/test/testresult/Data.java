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
private String startTime;
@SerializedName("end_time")
@Expose
private String endTime;
@SerializedName("lowest_rank")
@Expose
private Integer lowestRank;
@SerializedName("your_score")
@Expose
private Integer yourScore;
@SerializedName("total_marks")
@Expose
private Integer totalMarks;
@SerializedName("percenatge")
@Expose
private Integer percenatge;
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

    protected Data(Parcel in) {
        if (in.readByte() == 0) {
            rank = null;
        } else {
            rank = in.readInt();
        }
        startTime = in.readString();
        endTime = in.readString();
        if (in.readByte() == 0) {
            lowestRank = null;
        } else {
            lowestRank = in.readInt();
        }
        if (in.readByte() == 0) {
            yourScore = null;
        } else {
            yourScore = in.readInt();
        }
        if (in.readByte() == 0) {
            totalMarks = null;
        } else {
            totalMarks = in.readInt();
        }
        if (in.readByte() == 0) {
            percenatge = null;
        } else {
            percenatge = in.readInt();
        }
        if (in.readByte() == 0) {
            percentile = null;
        } else {
            percentile = in.readInt();
        }
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

public Integer getLowestRank() {
return lowestRank;
}

public void setLowestRank(Integer lowestRank) {
this.lowestRank = lowestRank;
}

public Integer getYourScore() {
return yourScore;
}

public void setYourScore(Integer yourScore) {
this.yourScore = yourScore;
}

public Integer getTotalMarks() {
return totalMarks;
}

public void setTotalMarks(Integer totalMarks) {
this.totalMarks = totalMarks;
}

public Integer getPercenatge() {
return percenatge;
}

public void setPercenatge(Integer percenatge) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (rank == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rank);
        }
        dest.writeString(startTime);
        dest.writeString(endTime);
        if (lowestRank == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(lowestRank);
        }
        if (yourScore == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(yourScore);
        }
        if (totalMarks == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalMarks);
        }
        if (percenatge == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(percenatge);
        }
        if (percentile == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(percentile);
        }
    }
}