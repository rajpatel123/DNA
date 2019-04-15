package com.dnamedical.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoModel {

@SerializedName("id")
@Expose
private String id;
@SerializedName("v_name")
@Expose
private String vName;
@SerializedName("title")
@Expose
private String title;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getVName() {
return vName;
}

public void setVName(String vName) {
this.vName = vName;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

}

