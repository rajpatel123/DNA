package com.dnamedical.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Role {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("role")
@Expose
private String role;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getRole() {
return role;
}

public void setRole(String role) {
this.role = role;
}

}