package com.dnamedical.Models.test.testp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionData implements Parcelable {

    @SerializedName("question_list")
    @Expose
    private ArrayList<Question> questionList = null;
    @SerializedName("total_question")
    @Expose
    private Integer totalQuestion;

    protected QuestionData(Parcel in) {
        questionList = in.createTypedArrayList(Question.CREATOR);
        if (in.readByte() == 0) {
            totalQuestion = null;
        } else {
            totalQuestion = in.readInt();
        }
    }

    public static final Creator<QuestionData> CREATOR = new Creator<QuestionData>() {
        @Override
        public QuestionData createFromParcel(Parcel in) {
            return new QuestionData(in);
        }

        @Override
        public QuestionData[] newArray(int size) {
            return new QuestionData[size];
        }
    };

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    public Integer getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(Integer totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(questionList);
        if (totalQuestion == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalQuestion);
        }
    }
}
