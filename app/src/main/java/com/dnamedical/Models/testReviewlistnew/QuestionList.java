package com.dnamedical.Models.testReviewlistnew;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionList implements Parcelable {

@SerializedName("id")
@Expose
private String id;
@SerializedName("category_id")
@Expose
private String categoryId;
@SerializedName("category_name")
@Expose
private String categoryName;
@SerializedName("title")
@Expose
private String title;
@SerializedName("title_image")
@Expose
private Object titleImage;
@SerializedName("is_bookmark")
@Expose
private Integer isBookmark;

    protected QuestionList(Parcel in) {
        id = in.readString();
        categoryId = in.readString();
        categoryName = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            isBookmark = null;
        } else {
            isBookmark = in.readInt();
        }
    }

    public static final Creator<QuestionList> CREATOR = new Creator<QuestionList>() {
        @Override
        public QuestionList createFromParcel(Parcel in) {
            return new QuestionList(in);
        }

        @Override
        public QuestionList[] newArray(int size) {
            return new QuestionList[size];
        }
    };

    public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getCategoryId() {
return categoryId;
}

public void setCategoryId(String categoryId) {
this.categoryId = categoryId;
}

public String getCategoryName() {
return categoryName;
}

public void setCategoryName(String categoryName) {
this.categoryName = categoryName;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public Object getTitleImage() {
return titleImage;
}

public void setTitleImage(Object titleImage) {
this.titleImage = titleImage;
}

public Integer getIsBookmark() {
return isBookmark;
}

public void setIsBookmark(Integer isBookmark) {
this.isBookmark = isBookmark;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(categoryId);
        parcel.writeString(categoryName);
        parcel.writeString(title);
        if (isBookmark == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(isBookmark);
        }
    }
}