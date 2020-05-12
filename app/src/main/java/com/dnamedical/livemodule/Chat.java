
package com.dnamedical.livemodule;

import com.dnamedical.Models.test.testp.Test;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Chat  implements Comparable<Chat>{

    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("coupon_id")
    @Expose
    private String couponId;
    @SerializedName("coupan_code")
    @Expose
    private String coupanCode;
    @SerializedName("coupan_value")
    @Expose
    private String coupanValue;
    @SerializedName("paid_status")
    @Expose
    private Integer paidStatus;

    @SerializedName("chapter_name")
    @Expose
    private String chaptername;

    @SerializedName("category_name")
    @Expose
    private String categoryname;

    @SerializedName("batch_name")
    @Expose
    private String batchname;

    public String getBatchname() {
        return batchname;
    }

    public void setBatchname(String batchname) {
        this.batchname = batchname;
    }

    public String getChaptername() {
        return chaptername;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCoupanCode() {
        return coupanCode;
    }

    public void setCoupanCode(String coupanCode) {
        this.coupanCode = coupanCode;
    }

    public String getCoupanValue() {
        return coupanValue;
    }

    public void setCoupanValue(String coupanValue) {
        this.coupanValue = coupanValue;
    }

    public Integer getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(Integer paidStatus) {
        this.paidStatus = paidStatus;
    }

    @Override
    public int compareTo(Chat o) {
        if (Long.parseLong(getLiveStartedTime()) == 0 || Long.parseLong(o.getLiveStartedTime()) == 0) {
            return 0;
        }
        return new Date(Long.parseLong(getLiveStartedTime())).compareTo(new Date(Long.parseLong(o.getLiveStartedTime())));
    }
}
