
package com.dnamedical.livemodule;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Chanel implements Comparable<Chanel>, Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("faculty_id")
    @Expose
    private String faculty_id;

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

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    @SerializedName("category_id")
    @Expose
    private String category_id;
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

    protected Chanel(Parcel in) {
        id = in.readString();
        faculty_id = in.readString();
        channelId = in.readString();
        channelName = in.readString();
        doctorName = in.readString();
        liveStartedTime = in.readString();
        liveEndTime = in.readString();
        liveSubject = in.readString();
        doctorImage = in.readString();
        thumbnail = in.readString();
        price = in.readString();
        paid = in.readString();
        couponId = in.readString();
        coupanCode = in.readString();
        coupanValue = in.readString();
        if (in.readByte() == 0) {
            paidStatus = null;
        } else {
            paidStatus = in.readInt();
        }
        chaptername = in.readString();
        categoryname = in.readString();
        batchname = in.readString();
    }

    public static final Creator<Chanel> CREATOR = new Creator<Chanel>() {
        @Override
        public Chanel createFromParcel(Parcel in) {
            return new Chanel(in);
        }

        @Override
        public Chanel[] newArray(int size) {
            return new Chanel[size];
        }
    };

    public String getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        this.faculty_id = faculty_id;
    }

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
    public int compareTo(Chanel o) {
        if (Long.parseLong(getLiveStartedTime()) == 0 || Long.parseLong(o.getLiveStartedTime()) == 0) {
            return 0;
        }
        return new Date(Long.parseLong(getLiveStartedTime())).compareTo(new Date(Long.parseLong(o.getLiveStartedTime())));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(faculty_id);
        dest.writeString(channelId);
        dest.writeString(channelName);
        dest.writeString(doctorName);
        dest.writeString(liveStartedTime);
        dest.writeString(liveEndTime);
        dest.writeString(liveSubject);
        dest.writeString(doctorImage);
        dest.writeString(thumbnail);
        dest.writeString(price);
        dest.writeString(paid);
        dest.writeString(couponId);
        dest.writeString(coupanCode);
        dest.writeString(coupanValue);
        if (paidStatus == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(paidStatus);
        }
        dest.writeString(chaptername);
        dest.writeString(categoryname);
        dest.writeString(batchname);
    }
}
