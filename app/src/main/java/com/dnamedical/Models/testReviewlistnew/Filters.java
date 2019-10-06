package com.dnamedical.Models.testReviewlistnew;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.logging.Filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filters implements Parcelable {

@SerializedName("levels")
@Expose
private List<Level> levels = null;
@SerializedName("subject")
@Expose
private List<Subject> subject = null;
@SerializedName("answers")
@Expose
private List<Answer> answers = null;

    protected Filters(Parcel in) {
        answers = in.createTypedArrayList(Answer.CREATOR);
    }

    public static final Creator<Filters> CREATOR = new Creator<Filters>() {
        @Override
        public Filters createFromParcel(Parcel in) {
            return new Filters(in);
        }

        @Override
        public Filters[] newArray(int size) {
            return new Filters[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(answers);
    }
}
