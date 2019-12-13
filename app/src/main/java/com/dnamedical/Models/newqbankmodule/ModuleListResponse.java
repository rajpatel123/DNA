package com.dnamedical.Models.newqbankmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModuleListResponse {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("details")
@Expose
private List<Detail> details = null;
@SerializedName("total_bookmark")
@Expose
private Integer totalBookmark;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Detail> getDetails() {
return details;
}

public void setDetails(List<Detail> details) {
this.details = details;
}

public Integer getTotalBookmark() {
return totalBookmark;
}

public void setTotalBookmark(Integer totalBookmark) {
this.totalBookmark = totalBookmark;
}

}