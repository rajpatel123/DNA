package com.dnamedical.Models.paidvideo;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaidVideoResponse implements Parcelable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Price")
    @Expose
    private List<Price> price = null;

    protected PaidVideoResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
        price = in.createTypedArrayList(Price.CREATOR);
    }

    public static final Creator<PaidVideoResponse> CREATOR = new Creator<PaidVideoResponse>() {
        @Override
        public PaidVideoResponse createFromParcel(Parcel in) {
            return new PaidVideoResponse(in);
        }

        @Override
        public PaidVideoResponse[] newArray(int size) {
            return new PaidVideoResponse[size];
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

    public List<Price> getPrice() {
        return price;
    }

    public void setPrice(List<Price> price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeTypedList(price);
    }
}
