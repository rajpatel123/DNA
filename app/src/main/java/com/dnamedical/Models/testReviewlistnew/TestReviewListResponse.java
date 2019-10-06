package com.dnamedical.Models.testReviewlistnew;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestReviewListResponse implements Parcelable {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("data")
@Expose
private Data data;

    protected TestReviewListResponse(Parcel in) {
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<TestReviewListResponse> CREATOR = new Creator<TestReviewListResponse>() {
        @Override
        public TestReviewListResponse createFromParcel(Parcel in) {
            return new TestReviewListResponse(in);
        }

        @Override
        public TestReviewListResponse[] newArray(int size) {
            return new TestReviewListResponse[size];
        }
    };

    public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        parcel.writeParcelable(data, i);
    }
}