package com.dnamedical.Models.faculties;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Faculty {

@SerializedName("f_name")
@Expose
private String fName;
@SerializedName("f_deg")
@Expose
private String fDeg;
@SerializedName("f_desc")
@Expose
private String fDesc;
@SerializedName("f_quote")
@Expose
private String fQuote;
@SerializedName("f_image")
@Expose
private String fImage;

public String getFName() {
return fName;
}

public void setFName(String fName) {
this.fName = fName;
}

public String getFDeg() {
return fDeg;
}

public void setFDeg(String fDeg) {
this.fDeg = fDeg;
}

public String getFDesc() {
return fDesc;
}

public void setFDesc(String fDesc) {
this.fDesc = fDesc;
}

public String getFQuote() {
return fQuote;
}

public void setFQuote(String fQuote) {
this.fQuote = fQuote;
}

public String getFImage() {
return fImage;
}

public void setFImage(String fImage) {
this.fImage = fImage;
}

}
