package com.dnamedical.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QustionDetails implements Parcelable {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("detail")
@Expose
private List<Detail> detail = null;

    protected QustionDetails(Parcel in) {
        status = in.readString();
        message = in.readString();
        detail = in.createTypedArrayList(Detail.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeTypedList(detail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QustionDetails> CREATOR = new Creator<QustionDetails>() {
        @Override
        public QustionDetails createFromParcel(Parcel in) {
            return new QustionDetails(in);
        }

        @Override
        public QustionDetails[] newArray(int size) {
            return new QustionDetails[size];
        }
    };

    public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Detail> getDetail() {
return detail;
}

public void setDetail(List<Detail> detail) {
this.detail = detail;
}

}