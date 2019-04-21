package com.dnamedical.Models.TestReviewList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

@SerializedName("qid")
@Expose
private String qid;
@SerializedName("question")
@Expose
private String question;
@SerializedName("answer1")
@Expose
private String answer1;
@SerializedName("answer2")
@Expose
private String answer2;
@SerializedName("answer3")
@Expose
private String answer3;
@SerializedName("answer4")
@Expose
private String answer4;
@SerializedName("currect_answer")
@Expose
private String currectAnswer;
@SerializedName("user_answer")
@Expose
private String userAnswer;
@SerializedName("explanation")
@Expose
private String explanation;

public String getQid() {
return qid;
}

public void setQid(String qid) {
this.qid = qid;
}

public String getQuestion() {
return question;
}

public void setQuestion(String question) {
this.question = question;
}

public String getAnswer1() {
return answer1;
}

public void setAnswer1(String answer1) {
this.answer1 = answer1;
}

public String getAnswer2() {
return answer2;
}

public void setAnswer2(String answer2) {
this.answer2 = answer2;
}

public String getAnswer3() {
return answer3;
}

public void setAnswer3(String answer3) {
this.answer3 = answer3;
}

public String getAnswer4() {
return answer4;
}

public void setAnswer4(String answer4) {
this.answer4 = answer4;
}

public String getCurrectAnswer() {
return currectAnswer;
}

public void setCurrectAnswer(String currectAnswer) {
this.currectAnswer = currectAnswer;
}

public String getUserAnswer() {
return userAnswer;
}

public void setUserAnswer(String userAnswer) {
this.userAnswer = userAnswer;
}

public String getExplanation() {
return explanation;
}

public void setExplanation(String explanation) {
this.explanation = explanation;
}

}

