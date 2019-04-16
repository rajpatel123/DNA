package com.dnamedical.Models.QbannkReviewList;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewListResponse implements Parcelable {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("details")
@Expose
private List<Detail> details = null;

    protected ReviewListResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
    }

    public static final Creator<ReviewListResponse> CREATOR = new Creator<ReviewListResponse>() {
        @Override
        public ReviewListResponse createFromParcel(Parcel in) {
            return new ReviewListResponse(in);
        }

        @Override
        public ReviewListResponse[] newArray(int size) {
            return new ReviewListResponse[size];
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
    }
}