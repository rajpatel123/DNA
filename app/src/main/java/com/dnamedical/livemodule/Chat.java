package com.dnamedical.livemodule;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chat {

@SerializedName("channel_id")
@Expose
private String channelId;
@SerializedName("channel_name")
@Expose
private String channelName;
@SerializedName("doctor_name")
@Expose
private String doctorName;
@SerializedName("live_started_time")
@Expose
private String liveStartedTime;
@SerializedName("live_end_time")
@Expose
private String liveEndTime;
@SerializedName("live_subject")
@Expose
private String liveSubject;
@SerializedName("doctor_image")
@Expose
private String doctorImage;
@SerializedName("thumbnail")
@Expose
private String thumbnail;

public String getChannelId() {
return channelId;
}

public void setChannelId(String channelId) {
this.channelId = channelId;
}

public String getChannelName() {
return channelName;
}

public void setChannelName(String channelName) {
this.channelName = channelName;
}

public String getDoctorName() {
return doctorName;
}

public void setDoctorName(String doctorName) {
this.doctorName = doctorName;
}

public String getLiveStartedTime() {
return liveStartedTime;
}

public void setLiveStartedTime(String liveStartedTime) {
this.liveStartedTime = liveStartedTime;
}

public String getLiveEndTime() {
return liveEndTime;
}

public void setLiveEndTime(String liveEndTime) {
this.liveEndTime = liveEndTime;
}

public String getLiveSubject() {
return liveSubject;
}

public void setLiveSubject(String liveSubject) {
this.liveSubject = liveSubject;
}

public String getDoctorImage() {
return doctorImage;
}

public void setDoctorImage(String doctorImage) {
this.doctorImage = doctorImage;
}

public String getThumbnail() {
return thumbnail;
}

public void setThumbnail(String thumbnail) {
this.thumbnail = thumbnail;
}

}
