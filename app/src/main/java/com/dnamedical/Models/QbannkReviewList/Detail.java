package com.dnamedical.Models.QbannkReviewList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("qname")
    @Expose
    private String qname;
    @SerializedName("optionA")
    @Expose
    private String optionA;
    @SerializedName("optionB")
    @Expose
    private String optionB;
    @SerializedName("optionC")
    @Expose
    private String optionC;
    @SerializedName("optionD")
    @Expose
    private String optionD;
    @SerializedName("optionAperc")
    @Expose
    private String optionAperc;
    @SerializedName("optionBperc")
    @Expose
    private String optionBperc;
    @SerializedName("optionCperc")
    @Expose
    private String optionCperc;
    @SerializedName("optionDperc")
    @Expose
    private String optionDperc;
    @SerializedName("gotrightperc")
    @Expose
    private String gotrightperc;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("useranswer")
    @Expose
    private String useranswer;
    @SerializedName("refrence")
    @Expose
    private String refrence;
    @SerializedName("description_url")
    @Expose
    private String descriptionUrl;

    protected Detail(Parcel in) {
        id = in.readString();
        qname = in.readString();
        optionA = in.readString();
        optionB = in.readString();
        optionC = in.readString();
        optionD = in.readString();
        optionAperc = in.readString();
        optionBperc = in.readString();
        optionCperc = in.readString();
        optionDperc = in.readString();
        gotrightperc = in.readString();
        answer = in.readString();
        useranswer = in.readString();
        refrence = in.readString();
        descriptionUrl = in.readString();
    }

    public static final Creator<Detail> CREATOR = new Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel in) {
            return new Detail(in);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQname() {
        return qname;
    }

    public void setQname(String qname) {
        this.qname = qname;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionAperc() {
        return optionAperc;
    }

    public void setOptionAperc(String optionAperc) {
        this.optionAperc = optionAperc;
    }

    public String getOptionBperc() {
        return optionBperc;
    }

    public void setOptionBperc(String optionBperc) {
        this.optionBperc = optionBperc;
    }

    public String getOptionCperc() {
        return optionCperc;
    }

    public void setOptionCperc(String optionCperc) {
        this.optionCperc = optionCperc;
    }

    public String getOptionDperc() {
        return optionDperc;
    }

    public void setOptionDperc(String optionDperc) {
        this.optionDperc = optionDperc;
    }

    public String getGotrightperc() {
        return gotrightperc;
    }

    public void setGotrightperc(String gotrightperc) {
        this.gotrightperc = gotrightperc;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUseranswer() {
        return useranswer;
    }

    public void setUseranswer(String useranswer) {
        this.useranswer = useranswer;
    }

    public String getRefrence() {
        return refrence;
    }

    public void setRefrence(String refrence) {
        this.refrence = refrence;
    }

    public String getDescriptionUrl() {
        return descriptionUrl;
    }

    public void setDescriptionUrl(String descriptionUrl) {
        this.descriptionUrl = descriptionUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(qname);
        dest.writeString(optionA);
        dest.writeString(optionB);
        dest.writeString(optionC);
        dest.writeString(optionD);
        dest.writeString(optionAperc);
        dest.writeString(optionBperc);
        dest.writeString(optionCperc);
        dest.writeString(optionDperc);
        dest.writeString(gotrightperc);
        dest.writeString(answer);
        dest.writeString(useranswer);
        dest.writeString(refrence);
        dest.writeString(descriptionUrl);
    }
}
