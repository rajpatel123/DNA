package com.dnamedical.Models.test.testp;

import com.dnamedical.Models.test.AllTest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Test implements Comparable<Test>{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;

    public String getNo_of_subjects() {
        return no_of_subjects;
    }

    public void setNo_of_subjects(String no_of_subjects) {
        this.no_of_subjects = no_of_subjects;
    }

    @SerializedName("no_of_subjects")
    @Expose
    private String no_of_subjects;
    @SerializedName("logo")
    @Expose
    private Object logo;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("result_date")
    @Expose
    private String resultDate;

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @SerializedName("end_date")
    @Expose
    private String end_date;
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

    public String getTest_status() {
        return test_status;
    }

    public void setTest_status(String test_status) {
        this.test_status = test_status;
    }

    @SerializedName("test_status")
    @Expose
    private String test_status;

    public String getQuestion_count() {
        return question_count;
    }

    public void setQuestion_count(String question_count) {
        this.question_count = question_count;
    }

    @SerializedName("question_count")
    @Expose
    private String question_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
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


    @Override
    public int compareTo(Test u) {
        if (Long.parseLong(getStartDate()) == 0 || Long.parseLong(u.getStartDate()) == 0) {
            return 0;
        }
        return new Date(Long.parseLong(getStartDate())).compareTo(new Date(Long.parseLong(u.getStartDate())));
    }
}
