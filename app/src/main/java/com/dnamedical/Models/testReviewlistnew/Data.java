package com.dnamedical.Models.testReviewlistnew;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Data {

    @SerializedName("question_list")
    @Expose
    private ArrayList<QuestionList> questionList = null;
    @SerializedName("filters")
    @Expose
    private Filters filters;

    public ArrayList<QuestionList> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<QuestionList> questionList) {
        this.questionList = questionList;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

}