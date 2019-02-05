package edu.com.medicalapp.Models.test;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectTest {

@SerializedName("test_id")
@Expose
private String testId;
@SerializedName("test_name")
@Expose
private String testName;
@SerializedName("test_date")
@Expose
private String testDate;
@SerializedName("test_duration")
@Expose
private String testDuration;
@SerializedName("test_queation")
@Expose
private String testQueation;
@SerializedName("test_category")
@Expose
private String testCategory;
@SerializedName("test_paid")
@Expose
private String testPaid;
@SerializedName("test_image")
@Expose
private String testImage;

public String getTestId() {
return testId;
}

public void setTestId(String testId) {
this.testId = testId;
}

public String getTestName() {
return testName;
}

public void setTestName(String testName) {
this.testName = testName;
}

public String getTestDate() {
return testDate;
}

public void setTestDate(String testDate) {
this.testDate = testDate;
}

public String getTestDuration() {
return testDuration;
}

public void setTestDuration(String testDuration) {
this.testDuration = testDuration;
}

public String getTestQueation() {
return testQueation;
}

public void setTestQueation(String testQueation) {
this.testQueation = testQueation;
}

public String getTestCategory() {
return testCategory;
}

public void setTestCategory(String testCategory) {
this.testCategory = testCategory;
}

public String getTestPaid() {
return testPaid;
}

public void setTestPaid(String testPaid) {
this.testPaid = testPaid;
}

public String getTestImage() {
return testImage;
}

public void setTestImage(String testImage) {
this.testImage = testImage;
}

}
