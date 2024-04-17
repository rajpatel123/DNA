package com.dnamedical.Models.maincat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubSubChild {

@SerializedName("id")
@Expose
private String id;
@SerializedName("sub_child_name")
@Expose
private String subChildName;

    public String getFile_upload() {
        return file_upload;
    }

    public void setFile_upload(String file_upload) {
        this.file_upload = file_upload;
    }

    @SerializedName("file_upload")
    @Expose
    private String file_upload;

    public String getAdmin_discount() {
        return admin_discount;
    }

    public void setAdmin_discount(String admin_discount) {
        this.admin_discount = admin_discount;
    }

    public String getIsFull() {
        return isFull;
    }

    public void setIsFull(String isFull) {
        this.isFull = isFull;
    }

    @SerializedName("admin_discount")
@Expose
private String admin_discount;


@SerializedName("is_full")
@Expose
private String isFull;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getSubChildName() {
return subChildName;
}

public void setSubChildName(String subChildName) {
this.subChildName = subChildName;
}

}