package edu.com.medicalapp.Models;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("email")
@Expose
private String email;
@SerializedName("name")
@Expose
private String name;
@SerializedName("lastName")
@Expose
private String lastName;
@SerializedName("active")
@Expose
private Integer active;
@SerializedName("token")
@Expose
private String token;
@SerializedName("roles")
@Expose
private List<Role> roles = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getLastName() {
return lastName;
}

public void setLastName(String lastName) {
this.lastName = lastName;
}

public Integer getActive() {
return active;
}

public void setActive(Integer active) {
this.active = active;
}

public String getToken() {
return token;
}

public void setToken(String token) {
this.token = token;
}

public List<Role> getRoles() {
return roles;
}

public void setRoles(List<Role> roles) {
this.roles = roles;
}

}
