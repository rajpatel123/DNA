package com.dnamedical.Models.test.testresult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

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

}