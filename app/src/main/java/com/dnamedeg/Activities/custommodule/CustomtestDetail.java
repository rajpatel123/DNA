package com.dnamedeg.Activities.custommodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomtestDetail {

@SerializedName("id")
@Expose
private String id;
@SerializedName("no_of_subjects")
@Expose
private String noOfSubjects;
@SerializedName("title")
@Expose
private String title;
@SerializedName("description")
@Expose
private String description;
@SerializedName("logo")
@Expose
private Object logo;
@SerializedName("start_date")
@Expose
private String startDate;
@SerializedName("timezone")
@Expose
private String timezone;
@SerializedName("end_date")
@Expose
private String endDate;
@SerializedName("result_date")
@Expose
private String resultDate;
@SerializedName("test_valid_till_date")
@Expose
private String testValidTillDate;
@SerializedName("duration")
@Expose
private String duration;
@SerializedName("is_paid")
@Expose
private String isPaid;
@SerializedName("amount")
@Expose
private Object amount;
@SerializedName("currency")
@Expose
private Object currency;
@SerializedName("type")
@Expose
private String type;
@SerializedName("question_count")
@Expose
private Object questionCount;
@SerializedName("test_status")
@Expose
private String testStatus;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getNoOfSubjects() {
return noOfSubjects;
}

public void setNoOfSubjects(String noOfSubjects) {
this.noOfSubjects = noOfSubjects;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public Object getLogo() {
return logo;
}

public void setLogo(Object logo) {
this.logo = logo;
}

public String getStartDate() {
return startDate;
}

public void setStartDate(String startDate) {
this.startDate = startDate;
}

public String getTimezone() {
return timezone;
}

public void setTimezone(String timezone) {
this.timezone = timezone;
}

public String getEndDate() {
return endDate;
}

public void setEndDate(String endDate) {
this.endDate = endDate;
}

public String getResultDate() {
return resultDate;
}

public void setResultDate(String resultDate) {
this.resultDate = resultDate;
}

public String getTestValidTillDate() {
return testValidTillDate;
}

public void setTestValidTillDate(String testValidTillDate) {
this.testValidTillDate = testValidTillDate;
}

public String getDuration() {
return duration;
}

public void setDuration(String duration) {
this.duration = duration;
}

public String getIsPaid() {
return isPaid;
}

public void setIsPaid(String isPaid) {
this.isPaid = isPaid;
}

public Object getAmount() {
return amount;
}

public void setAmount(Object amount) {
this.amount = amount;
}

public Object getCurrency() {
return currency;
}

public void setCurrency(Object currency) {
this.currency = currency;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Object getQuestionCount() {
return questionCount;
}

public void setQuestionCount(Object questionCount) {
this.questionCount = questionCount;
}

public String getTestStatus() {
return testStatus;
}

public void setTestStatus(String testStatus) {
this.testStatus = testStatus;
}

}