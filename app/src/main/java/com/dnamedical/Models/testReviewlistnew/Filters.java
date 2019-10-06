package com.dnamedical.Models.testReviewlistnew;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.logging.Filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Filters {

    @SerializedName("levels")
    @Expose
    private List<Level> levels = null;
    @SerializedName("subject")
    @Expose
    private List<Subject> subject = null;
    @SerializedName("answers")
    @Expose
    private List<Answer> answers = null;

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public List<Subject> getSubject() {
        return subject;
    }

    public void setSubject(List<Subject> subject) {
        this.subject = subject;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

}