package com.dnamedical.Models.QbankTest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.dnamedical.Models.qbank.Detail;

public class QbankTestResponse implements Parcelable {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<QbankTest> details = null;

    public QbankTestResponse() {
    }

    public QbankTestResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
        details = in.createTypedArrayList(QbankTest.CREATOR);
    }

    public static final Creator<QbankTestResponse> CREATOR = new Creator<QbankTestResponse>() {
        @Override
        public QbankTestResponse createFromParcel(Parcel in) {
            return new QbankTestResponse(in);
        }

        @Override
        public QbankTestResponse[] newArray(int size) {
            return new QbankTestResponse[size];
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

    public List<QbankTest> getDetails() {
        return details;
    }

    public void setDetails(List<QbankTest> details) {
        this.details = details;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeTypedList(details);
    }
}
