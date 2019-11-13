package com.dnamedical.Models.acadamic;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Academic {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("Acdemics")
@Expose
private List<Acdemic> acdemics = null;

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

public List<Acdemic> getAcdemics() {
return acdemics;
}

public void setAcdemics(List<Acdemic> acdemics) {
this.acdemics = acdemics;
}

}
