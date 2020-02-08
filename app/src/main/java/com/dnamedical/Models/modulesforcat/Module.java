package com.dnamedical.Models.modulesforcat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Module {

@SerializedName("id")
@Expose
private String id;

@SerializedName("title")
@Expose
private String title;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @SerializedName("image")
@Expose
private String image;

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

}

