package com.dnamedical.livemodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveChannelResponse {

@SerializedName("id")
@Expose
private String id;
@SerializedName("channel_id")
@Expose
private String channelId;
@SerializedName("channel_name")
@Expose
private String channelName;
@SerializedName("dr_name")
@Expose
private String drName;
@SerializedName("live_started_time")
@Expose
private String liveStartedTime;
@SerializedName("live_end_time")
@Expose
private String liveEndTime;
@SerializedName("live_subject")
@Expose
private String liveSubject;
@SerializedName("live_date")
@Expose
private String liveDate;
@SerializedName("thumbnail")
@Expose
private String thumbnail;
@SerializedName("status")
@Expose
private String status;
@SerializedName("createdOn")
@Expose
private String createdOn;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

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

public String getDrName() {
return drName;
}

public void setDrName(String drName) {
this.drName = drName;
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

public String getLiveDate() {
return liveDate;
}

public void setLiveDate(String liveDate) {
this.liveDate = liveDate;
}

public String getThumbnail() {
return thumbnail;
}

public void setThumbnail(String thumbnail) {
this.thumbnail = thumbnail;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getCreatedOn() {
return createdOn;
}

public void setCreatedOn(String createdOn) {
this.createdOn = createdOn;
}

}
