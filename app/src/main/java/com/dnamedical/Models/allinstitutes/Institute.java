package com.dnamedical.Models.allinstitutes;

import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;


public class Institute {

    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("institute_name")
    @Expose
    private String instituteName;
    @SerializedName("institute_logo")
    @Expose
    private String instituteLogo;

    public String getInstituteId() {
return instituteId;
}

public void setInstituteId(String instituteId) {
this.instituteId = instituteId;
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