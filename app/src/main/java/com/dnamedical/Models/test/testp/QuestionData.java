package com.dnamedical.Models.test.testp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionData {

    @SerializedName("question_list")
    @Expose
    private List<Question> questionList = null;
    @SerializedName("total_question")
    @Expose
    private Integer totalQuestion;

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public Integer getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(Integer totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

}
