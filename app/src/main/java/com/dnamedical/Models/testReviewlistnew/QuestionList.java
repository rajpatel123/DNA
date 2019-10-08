package com.dnamedical.Models.testReviewlistnew;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionList implements Parcelable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_image")
    @Expose
    private String titleImage;
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
    @SerializedName("option_1_image")
    @Expose
    private String option1Image;
    @SerializedName("correct_answer")
    @Expose
    private String correctAnswer;
    @SerializedName("option_2_image")
    @Expose
    private String option2Image;
    @SerializedName("option_3_image")
    @Expose
    private String option3Image;
    @SerializedName("option_4_image")
    @Expose
    private String option4Image;
    @SerializedName("is_bookmark")
    @Expose
    private Integer isBookmark;
    @SerializedName("is_guess")
    @Expose
    private String isGuess;
    @SerializedName("given_answer")
    @Expose
    private String givenAnswer;
    @SerializedName("option_1_percenatge")
    @Expose
    private Integer option1Percenatge;
    @SerializedName("option_2_percenatge")
    @Expose
    private Integer option2Percenatge;
    @SerializedName("option_3_percenatge")
    @Expose
    private Integer option3Percenatge;
    @SerializedName("option_4_percenatge")
    @Expose
    private Integer option4Percenatge;

    @SerializedName("percentage")
    @Expose
    private Integer percentage;

    protected QuestionList(Parcel in) {
        id = in.readString();
        categoryId = in.readString();
        categoryName = in.readString();
        title = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        explanation = in.readString();
        correctAnswer = in.readString();
        if (in.readByte() == 0) {
            isBookmark = null;
        } else {
            isBookmark = in.readInt();
        }
        isGuess = in.readString();
        givenAnswer = in.readString();
        if (in.readByte() == 0) {
            option1Percenatge = null;
        } else {
            option1Percenatge = in.readInt();
        }
        if (in.readByte() == 0) {
            option2Percenatge = null;
        } else {
            option2Percenatge = in.readInt();
        }
        if (in.readByte() == 0) {
            option3Percenatge = null;
        } else {
            option3Percenatge = in.readInt();
        }
        if (in.readByte() == 0) {
            option4Percenatge = null;
        } else {
            option4Percenatge = in.readInt();
        }
        if (in.readByte() == 0) {
            percentage = null;
        } else {
            percentage = in.readInt();
        }
        refernce = in.readParcelable(Refernce.class.getClassLoader());
    }

    public static final Creator<QuestionList> CREATOR = new Creator<QuestionList>() {
        @Override
        public QuestionList createFromParcel(Parcel in) {
            return new QuestionList(in);
        }

        @Override
        public QuestionList[] newArray(int size) {
            return new QuestionList[size];
        }
    };

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }


    @SerializedName("explanation_image")
    @Expose
    private List<String> explanationImage = null;
    @SerializedName("refernce")
    @Expose
    private Refernce refernce;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
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

    public String getOption1Image() {
        return option1Image;
    }

    public void setOption1Image(String option1Image) {
        this.option1Image = option1Image;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getOption2Image() {
        return option2Image;
    }

    public void setOption2Image(String option2Image) {
        this.option2Image = option2Image;
    }

    public String getOption3Image() {
        return option3Image;
    }

    public void setOption3Image(String option3Image) {
        this.option3Image = option3Image;
    }

    public String getOption4Image() {
        return option4Image;
    }

    public void setOption4Image(String option4Image) {
        this.option4Image = option4Image;
    }

    public Integer getIsBookmark() {
        return isBookmark;
    }

    public void setIsBookmark(Integer isBookmark) {
        this.isBookmark = isBookmark;
    }

    public String getIsGuess() {
        return isGuess;
    }

    public void setIsGuess(String isGuess) {
        this.isGuess = isGuess;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public Integer getOption1Percenatge() {
        return option1Percenatge;
    }

    public void setOption1Percenatge(Integer option1Percenatge) {
        this.option1Percenatge = option1Percenatge;
    }

    public Integer getOption2Percenatge() {
        return option2Percenatge;
    }

    public void setOption2Percenatge(Integer option2Percenatge) {
        this.option2Percenatge = option2Percenatge;
    }

    public Integer getOption3Percenatge() {
        return option3Percenatge;
    }

    public void setOption3Percenatge(Integer option3Percenatge) {
        this.option3Percenatge = option3Percenatge;
    }

    public Integer getOption4Percenatge() {
        return option4Percenatge;
    }

    public void setOption4Percenatge(Integer option4Percenatge) {
        this.option4Percenatge = option4Percenatge;
    }

    public List<String> getExplanationImage() {
        return explanationImage;
    }

    public void setExplanationImage(List<String> explanationImage) {
        this.explanationImage = explanationImage;
    }

    public Refernce getRefernce() {
        return refernce;
    }

    public void setRefernce(Refernce refernce) {
        this.refernce = refernce;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(categoryId);
        dest.writeString(categoryName);
        dest.writeString(title);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeString(explanation);
        dest.writeString(correctAnswer);
        if (isBookmark == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isBookmark);
        }
        dest.writeString(isGuess);
        dest.writeString(givenAnswer);
        if (option1Percenatge == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(option1Percenatge);
        }
        if (option2Percenatge == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(option2Percenatge);
        }
        if (option3Percenatge == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(option3Percenatge);
        }
        if (option4Percenatge == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(option4Percenatge);
        }
        if (percentage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(percentage);
        }
        dest.writeParcelable(refernce, flags);
    }
}