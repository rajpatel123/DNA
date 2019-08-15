package com.dnamedical.Models.paymentmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetails {

@SerializedName("receipt")
@Expose
private String receipt;
@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("product_type")
@Expose
private String productType;
@SerializedName("amount")
@Expose
private String amount;
@SerializedName("currency")
@Expose
private String currency;
@SerializedName("product_id")
@Expose
private String productId;

public String getReceipt() {
return receipt;
}

public void setReceipt(String receipt) {
this.receipt = receipt;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getProductType() {
return productType;
}

public void setProductType(String productType) {
this.productType = productType;
}

public String getAmount() {
return amount;
}

public void setAmount(String amount) {
this.amount = amount;
}

public String getCurrency() {
return currency;
}

public void setCurrency(String currency) {
this.currency = currency;
}

public String getProductId() {
return productId;
}

public void setProductId(String productId) {
this.productId = productId;
}

}