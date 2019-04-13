package com.dnamedical.Models;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Directors {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("faculty")
@Expose
private List<Faculty> faculty = null;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Faculty> getFaculty() {
return faculty;
}

public void setFaculty(List<Faculty> faculty) {
this.faculty = faculty;
}

}

