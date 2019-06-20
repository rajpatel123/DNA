package com.dnamedical.Models.saveOrder;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("product_id")
    @Expose
    private String productId;

    @SerializedName("sub_child_cat_id")
    @Expose
    private String subChildCatId;


    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("test_id")
    @Expose
    private String testId;
    @SerializedName("status")
    @Expose
    private String status;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

