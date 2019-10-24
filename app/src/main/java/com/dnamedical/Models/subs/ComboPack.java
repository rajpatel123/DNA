package com.dnamedical.Models.subs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComboPack {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("subname")
@Expose
private String subname;
@SerializedName("price")
@Expose
private String price;
@SerializedName("discount")
@Expose
private String discount;

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

public String getSubname() {
return subname;
}

public void setSubname(String subname) {
this.subname = subname;
}

public String getPrice() {
return price;
}

public void setPrice(String price) {
this.price = price;
}

public String getDiscount() {
return discount;
}

public void setDiscount(String discount) {
this.discount = discount;
}

}