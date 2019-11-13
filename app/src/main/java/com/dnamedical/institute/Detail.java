package com.dnamedical.institute;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

@SerializedName("id")
@Expose
private String id;
@SerializedName("institute_name")
@Expose
private String instituteName;
@SerializedName("institute_logo")
@Expose
private String instituteLogo;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getInstituteName() {
return instituteName;
}

public void setInstituteName(String instituteName) {
this.instituteName = instituteName;
}

public String getInstituteLogo() {
return instituteLogo;
}

public void setInstituteLogo(String instituteLogo) {
this.instituteLogo = instituteLogo;
}

}
