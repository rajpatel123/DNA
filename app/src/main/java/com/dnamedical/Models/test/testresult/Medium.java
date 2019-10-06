package com.dnamedical.Models.test.testresult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium {

@SerializedName("correct")
@Expose
private Integer correct;
@SerializedName("wrong")
@Expose
private Integer wrong;
@SerializedName("skip")
@Expose
private Integer skip;

public Integer getCorrect() {
return correct;
}

public void setCorrect(Integer correct) {
this.correct = correct;
}

public Integer getWrong() {
return wrong;
}

public void setWrong(Integer wrong) {
this.wrong = wrong;
}

public Integer getSkip() {
return skip;
}

public void setSkip(Integer skip) {
this.skip = skip;
}

}
