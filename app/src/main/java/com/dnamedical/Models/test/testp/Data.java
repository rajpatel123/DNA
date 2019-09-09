package com.dnamedical.Models.test.testp;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("test_list")
@Expose
private List<TestList> testList = null;

public List<TestList> getTestList() {
return testList;
}

public void setTestList(List<TestList> testList) {
this.testList = testList;
}

}




