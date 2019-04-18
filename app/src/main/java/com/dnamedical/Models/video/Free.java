package com.dnamedical.Models.video;
import android.os.Parcel;
import android.os.Parcelable;

import com.dnamedical.Models.video.SourceTime;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Free implements  Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("sub_title")
    @Expose
    private String subTitle;

    protected Free(Parcel in) {
        id = in.readString();
        title = in.readString();
        subTitle = in.readString();
        dr_img = in.readString();
        description = in.readString();
        sourceTime = in.createTypedArrayList(SourceTime.CREATOR);
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(dr_img);
        dest.writeString(description);
        dest.writeTypedList(sourceTime);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Free> CREATOR = new Creator<Free>() {
        @Override
        public Free createFromParcel(Parcel in) {
            return new Free(in);
        }

        @Override
        public Free[] newArray(int size) {
            return new Free[size];
        }
    };

    public String getDr_img() {
        return dr_img;
    }

    public void setDr_img(String dr_img) {
        this.dr_img = dr_img;
    }

    @SerializedName("dr_img")
    @Expose
    private String dr_img;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("source_time")
    @Expose
    private List<SourceTime> sourceTime = null;
    @SerializedName("url")
    @Expose
    private String url;





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SourceTime> getSourceTime() {
        return sourceTime;
    }

    public void setSourceTime(List<SourceTime> sourceTime) {
        this.sourceTime = sourceTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
