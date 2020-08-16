package com.dnamedical.Activities.custommodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModuleDetails {

@SerializedName("req")
@Expose
private String req;
@SerializedName("cat_id")
@Expose
private String catId;
@SerializedName("totalQn")
@Expose
private String totalQn;
@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("level")
@Expose
private String level;
@SerializedName("subjects")
@Expose
private String subjects;
@SerializedName("type")
@Expose
private String type;
@SerializedName("tags")
@Expose
private String tags;
@SerializedName("test_id")
@Expose
private Integer testId;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @SerializedName("created_at")
@Expose
private String created_at;

public String getReq() {
return req;
}

public void setReq(String req) {
this.req = req;
}

public String getCatId() {
return catId;
}

public void setCatId(String catId) {
this.catId = catId;
}

public String getTotalQn() {
return totalQn;
}

public void setTotalQn(String totalQn) {
this.totalQn = totalQn;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getLevel() {
return level;
}

public void setLevel(String level) {
this.level = level;
}

public String getSubjects() {
return subjects;
}

public void setSubjects(String subjects) {
this.subjects = subjects;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getTags() {
return tags;
}

public void setTags(String tags) {
this.tags = tags;
}

public Integer getTestId() {
return testId;
}

public void setTestId(Integer testId) {
this.testId = testId;
}

}