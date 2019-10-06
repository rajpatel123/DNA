package com.dnamedical.Models.testReviewlistnew;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {

@SerializedName("question_list")
@Expose
private List<QuestionList> questionList = null;
@SerializedName("filters")
@Expose
private Filters filters;

    protected Data(Parcel in) {
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public List<QuestionList> getQuestionList() {
return questionList;
}

public void setQuestionList(List<QuestionList> questionList) {
this.questionList = questionList;
}

public Filters getFilters() {
return filters;
}

public void setFilters(Filters filters) {
this.filters = filters;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}