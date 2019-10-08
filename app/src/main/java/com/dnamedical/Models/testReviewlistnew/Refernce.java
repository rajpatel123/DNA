package com.dnamedical.Models.testReviewlistnew;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Refernce implements Parcelable {

@SerializedName("image")
@Expose
private String image;
@SerializedName("title")
@Expose
private String title;

    protected Refernce(Parcel in) {
        image = in.readString();
        title = in.readString();
    }

    public static final Creator<Refernce> CREATOR = new Creator<Refernce>() {
        @Override
        public Refernce createFromParcel(Parcel in) {
            return new Refernce(in);
        }

        @Override
        public Refernce[] newArray(int size) {
            return new Refernce[size];
        }
    };

    public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(title);
    }
}