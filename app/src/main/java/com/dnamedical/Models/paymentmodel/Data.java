package com.dnamedical.Models.paymentmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("order_id")
@Expose
private String orderId;
@SerializedName("order_details")
@Expose
private OrderDetails orderDetails;

public String getOrderId() {
return orderId;
}

public void setOrderId(String orderId) {
this.orderId = orderId;
}

public OrderDetails getOrderDetails() {
return orderDetails;
}

public void setOrderDetails(OrderDetails orderDetails) {
this.orderDetails = orderDetails;
}

}