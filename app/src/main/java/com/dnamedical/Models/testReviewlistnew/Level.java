package com.dnamedical.Models.testReviewlistnew;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Level {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;


    public String isSelected() {
        return isSelected;
    }

    public void setSelected(String selected) {
        isSelected = selected;
    }

    private String isSelected="false";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}