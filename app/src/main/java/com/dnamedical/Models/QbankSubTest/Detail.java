package com.dnamedical.Models.QbankSubTest;


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
@SerializedName("answer")
@Expose
private String answer;
@SerializedName("description")
@Expose
private String description;

    protected Detail(Parcel in) {
        id = in.readString();
        qname = in.readString();
        optionA = in.readString();
        optionB = in.readString();
        optionC = in.readString();
        optionD = in.readString();
        answer = in.readString();
        description = in.readString();
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

public String getAnswer() {
return answer;
}

public void setAnswer(String answer) {
this.answer = answer;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
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
        dest.writeString(answer);
        dest.writeString(description);
    }
}
