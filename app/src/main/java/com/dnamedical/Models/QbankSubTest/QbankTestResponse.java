package com.dnamedical.Models.QbankSubTest;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QbankTestResponse implements Parcelable {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("details")
@Expose
private List<Detail> details = null;

    protected QbankTestResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
        details = in.createTypedArrayList(Detail.CREATOR);
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

public List<Detail> getDetails() {
return details;
}

public void setDetails(List<Detail> details) {
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