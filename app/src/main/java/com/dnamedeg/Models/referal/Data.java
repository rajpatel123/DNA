package com.dnamedeg.Models.referal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("coupon_id")
@Expose
private String couponId;
@SerializedName("coupan_value")
@Expose
private String coupanValue;
@SerializedName("coupon_code")
@Expose
private String couponCode;

public String getCouponId() {
return couponId;
}

public void setCouponId(String couponId) {
this.couponId = couponId;
}

public String getCoupanValue() {
return coupanValue;
}

public void setCoupanValue(String coupanValue) {
this.coupanValue = coupanValue;
}

public String getCouponCode() {
return couponCode;
}

public void setCouponCode(String couponCode) {
this.couponCode = couponCode;
}

}