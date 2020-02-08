package com.dnamedical.Models.paidvideo;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sub_child_cat")
    @Expose
    private String subChildCat;
    @SerializedName("title")
    @Expose
    private String title;


    @SerializedName("time")
    @Expose
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @SerializedName("sub_title")
    @Expose
    private String subTitle;


    public String getIsbuyall() {
        return isbuyall;
    }

    public void setIsbuyall(String isbuyall) {
        this.isbuyall = isbuyall;
    }

    @SerializedName("isbuyall")
    @Expose
    private String isbuyall;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("coupan_id")
    @Expose
    private String coupanId;
    @SerializedName("coupan_code")
    @Expose
    private String coupanCode;

    public String getCh_name() {
        return ch_name;
    }

    public void setCh_name(String ch_name) {
        this.ch_name = ch_name;
    }

    @SerializedName("ch_name")
    @Expose
    private String ch_name;
    @SerializedName("coupan_value")
    @Expose
    private String coupanValue;
    @SerializedName("price")
    @Expose
    private String price;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("shipping_charge")
    @Expose
    private String shippingCharge;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("dr_img")
    @Expose
    private String drImg;

    public String getSubscription_status() {
        return subscription_status;
    }

    public void setSubscription_status(String subscription_status) {
        this.subscription_status = subscription_status;
    }

    @SerializedName("subscription_status")
    @Expose
    private String subscription_status;
    @SerializedName("source_time")
    @Expose
    private List<SourceTime> sourceTime = null;
    @SerializedName("url")
    @Expose
    private String url;

    protected Price(Parcel in) {
        id = in.readString();
        subChildCat = in.readString();
        title = in.readString();
        time = in.readString();
        subTitle = in.readString();
        description = in.readString();
        coupanId = in.readString();
        coupanCode = in.readString();
        coupanValue = in.readString();
        price = in.readString();
        discount = in.readString();
        shippingCharge = in.readString();
        userId = in.readString();
        paymentStatus = in.readString();
        drImg = in.readString();
        subscription_status = in.readString();
        url = in.readString();
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel in) {
            return new Price(in);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubChildCat() {
        return subChildCat;
    }

    public void setSubChildCat(String subChildCat) {
        this.subChildCat = subChildCat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoupanId() {
        return coupanId;
    }

    public void setCoupanId(String coupanId) {
        this.coupanId = coupanId;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDrImg() {
        return drImg;
    }

    public void setDrImg(String drImg) {
        this.drImg = drImg;
    }

    public List<SourceTime> getSourceTime() {
        return sourceTime;
    }

    public void setSourceTime(List<SourceTime> sourceTime) {
        this.sourceTime = sourceTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(subChildCat);
        dest.writeString(title);
        dest.writeString(time);
        dest.writeString(subTitle);
        dest.writeString(description);
        dest.writeString(coupanId);
        dest.writeString(coupanCode);
        dest.writeString(coupanValue);
        dest.writeString(price);
        dest.writeString(discount);
        dest.writeString(shippingCharge);
        dest.writeString(userId);
        dest.writeString(paymentStatus);
        dest.writeString(drImg);
        dest.writeString(subscription_status);
        dest.writeString(url);
    }
}
