package com.dnamedical.Models.test.testresult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScoreAnalysi {

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
private Integer score;

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

public Integer getScore() {
return score;
}

public void setScore(Integer score) {
this.score = score;
}

}
