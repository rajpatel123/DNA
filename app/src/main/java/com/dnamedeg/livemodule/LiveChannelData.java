
package com.dnamedeg.livemodule;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveChannelData implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("chat")
    @Expose
    private List<Chanel> chat = new ArrayList<>();

    protected LiveChannelData(Parcel in) {
        status = in.readString();
        message = in.readString();
    }

    public static final Creator<LiveChannelData> CREATOR = new Creator<LiveChannelData>() {
        @Override
        public LiveChannelData createFromParcel(Parcel in) {
            return new LiveChannelData(in);
        }

        @Override
        public LiveChannelData[] newArray(int size) {
            return new LiveChannelData[size];
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

    public List<Chanel> getChat() {
        return chat;
    }

    public void setChat(List<Chanel> chat) {
        this.chat = chat;
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
