package com.dnamedical.Models.test.testresult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuessAnalysis {

@SerializedName("correctToWrong")
@Expose
private Integer correctToWrong;
@SerializedName("wrongToCorrect")
@Expose
private Integer wrongToCorrect;
@SerializedName("wrongToWrong")
@Expose
private Integer wrongToWrong;
@SerializedName("totalSwitch")
@Expose
private Integer totalSwitch;

public Integer getCorrectToWrong() {
return correctToWrong;
}

public void setCorrectToWrong(Integer correctToWrong) {
this.correctToWrong = correctToWrong;
}

public Integer getWrongToCorrect() {
return wrongToCorrect;
}

public void setWrongToCorrect(Integer wrongToCorrect) {
this.wrongToCorrect = wrongToCorrect;
}

public Integer getWrongToWrong() {
return wrongToWrong;
}

public void setWrongToWrong(Integer wrongToWrong) {
this.wrongToWrong = wrongToWrong;
}

public Integer getTotalSwitch() {
return totalSwitch;
}

public void setTotalSwitch(Integer totalSwitch) {
this.totalSwitch = totalSwitch;
}

}