package com.dnamedical.Models.video;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoList {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("free")
@Expose
private List<Free> free = null;
@SerializedName("Price")
@Expose
private List<Price> price = null;

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

public List<Free> getFree() {
return free;
}

public void setFree(List<Free> free) {
this.free = free;
}

public List<Price> getPrice() {
return price;
}

public void setPrice(List<Price> price) {
this.price = price;
}

}