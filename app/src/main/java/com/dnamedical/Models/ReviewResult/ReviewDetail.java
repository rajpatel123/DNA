package com.dnamedical.Models.ReviewResult;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewDetail implements Parcelable {

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

    protected ReviewDetail(Parcel in) {
        qid = in.readString();
        question = in.readString();
        answer1 = in.readString();
        answer2 = in.readString();
        answer3 = in.readString();
        answer4 = in.readString();
        currectAnswer = in.readString();
        userAnswer = in.readString();
        explanation = in.readString();
    }

    public static final Creator<ReviewDetail> CREATOR = new Creator<ReviewDetail>() {
        @Override
        public ReviewDetail createFromParcel(Parcel in) {
            return new ReviewDetail(in);
        }

        @Override
        public ReviewDetail[] newArray(int size) {
            return new ReviewDetail[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(qid);
        dest.writeString(question);
        dest.writeString(answer1);
        dest.writeString(answer2);
        dest.writeString(answer3);
        dest.writeString(answer4);
        dest.writeString(currectAnswer);
        dest.writeString(userAnswer);
        dest.writeString(explanation);
    }
}
