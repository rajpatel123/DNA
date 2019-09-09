package com.dnamedical.Models.test.testp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestList {

@SerializedName("key")
@Expose
private String key;
@SerializedName("value")
@Expose
private String value;
@SerializedName("list")
@Expose
private List<Test> list = null;

public String getKey() {
return key;
}

public void setKey(String key) {
this.key = key;
}

public String getValue() {
return value;
}

public void setValue(String value) {
this.value = value;
}

public java.util.List<Test> getList() {
return list;
}

public void setList(java.util.List<Test> list) {
this.list = list;
}

}