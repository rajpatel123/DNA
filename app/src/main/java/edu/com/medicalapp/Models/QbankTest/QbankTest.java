package edu.com.medicalapp.Models.QbankTest;

import android.os.Parcel;
import android.os.Parcelable;

public class QbankTest implements Parcelable {

    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;

    public QbankTest() {
    }

    public QbankTest(Parcel in) {
        question = in.readString();
        answer1 = in.readString();
        answer2 = in.readString();
        answer3 = in.readString();
        answer4 = in.readString();
    }

    public static final Creator<QbankTest> CREATOR = new Creator<QbankTest>() {
        @Override
        public QbankTest createFromParcel(Parcel in) {
            return new QbankTest(in);
        }

        @Override
        public QbankTest[] newArray(int size) {
            return new QbankTest[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answer1);
        dest.writeString(answer2);
        dest.writeString(answer3);
        dest.writeString(answer4);
    }
}
