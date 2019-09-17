package com.dnamedical.Models.test.testresult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeAnalysis {

@SerializedName("change_question")
@Expose
private Integer changeQuestion;
@SerializedName("change_option")
@Expose
private Integer changeOption;
@SerializedName("total_time")
@Expose
private Integer totalTime;

public Integer getChangeQuestion() {
return changeQuestion;
}

public void setChangeQuestion(Integer changeQuestion) {
this.changeQuestion = changeQuestion;
}

public Integer getChangeOption() {
return changeOption;
}

public void setChangeOption(Integer changeOption) {
this.changeOption = changeOption;
}

public Integer getTotalTime() {
return totalTime;
}

public void setTotalTime(Integer totalTime) {
this.totalTime = totalTime;
}
