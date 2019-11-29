package com.dnamedical.Models.test.testp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Question implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("option_1")
    @Expose
    private String option1;
    @SerializedName("option_2")
    @Expose
    private String option2;
    @SerializedName("option_3")
    @Expose
    private String option3;
    @SerializedName("option_4")
    @Expose
    private String option4;
    @SerializedName("explanation")
    @Expose
    private String explanation;

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }


    private String selectedOption;

    public boolean isBookMarked() {
        return isBookMarked;
    }

    public void setBookMarked(boolean bookMarked) {
        isBookMarked = bookMarked;
    }

    public static Creator<Question> getCREATOR() {
        return CREATOR;
    }

    private boolean isBookMarked;

    public boolean isGues() {
        return isGues;
    }

    public void setGues(boolean gues) {
        isGues = gues;
    }

    private boolean isGues;

    @SerializedName("option_1_image")
    @Expose
    private String option_1_image;

    @SerializedName("option_2_image")
    @Expose
    private String option_2_image;



    @SerializedName("option_3_image")
    @Expose
    private String option_3_image;

    @SerializedName("option_4_image")
    @Expose
    private String option_4_image;

    @SerializedName("title_image")
    @Expose
    private String title_image;

    @SerializedName("is_multiple_answer")
    @Expose
    private boolean is_multiple_answer;


    protected Question(Parcel in) {
        id = in.readString();
        title = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        option_1_image = in.readString();
        option_2_image = in.readString();
        option_3_image = in.readString();
        option_4_image = in.readString();
        title_image = in.readString();
        explanation = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
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


    public String getOption_1_image() {
        return option_1_image;
    }

    public void setOption_1_image(String option_1_image) {
        this.option_1_image = option_1_image;
    }

    public String getOption_2_image() {
        return option_2_image;
    }

    public void setOption_2_image(String option_2_image) {
        this.option_2_image = option_2_image;
    }

    public String getOption_3_image() {
        return option_3_image;
    }

    public void setOption_3_image(String option_3_image) {
        this.option_3_image = option_3_image;
    }

    public String getOption_4_image() {
        return option_4_image;
    }

    public void setOption_4_image(String option_4_image) {
        this.option_4_image = option_4_image;
    }

    public String getTitle_image() {
        return title_image;
    }

    public void setTitle_image(String title_image) {
        this.title_image = title_image;
    }

    public boolean isIs_multiple_answer() {
        return is_multiple_answer;
    }

    public void setIs_multiple_answer(boolean is_multiple_answer) {
        this.is_multiple_answer = is_multiple_answer;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeString(explanation);
        dest.writeString(option_1_image);
        dest.writeString(option_2_image);
        dest.writeString(option_3_image);
        dest.writeString(option_4_image);
        dest.writeString(title_image);
    }
}
