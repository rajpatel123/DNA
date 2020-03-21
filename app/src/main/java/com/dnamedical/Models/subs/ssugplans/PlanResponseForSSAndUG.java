package com.dnamedical.Models.subs.ssugplans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanResponseForSSAndUG implements Parcelable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("NEETSS_Plan")
    @Expose
    private List<NEETSSPlan> nEETSSPlan = null;
    @SerializedName("NEETUG_Plan")
    @Expose
    private List<NEETUGPlan> nEETUGPlan = null;

    protected PlanResponseForSSAndUG(Parcel in) {
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        message = in.readString();
        nEETSSPlan = in.createTypedArrayList(NEETSSPlan.CREATOR);
        nEETUGPlan = in.createTypedArrayList(NEETUGPlan.CREATOR);
    }

    public static final Creator<PlanResponseForSSAndUG> CREATOR = new Creator<PlanResponseForSSAndUG>() {
        @Override
        public PlanResponseForSSAndUG createFromParcel(Parcel in) {
            return new PlanResponseForSSAndUG(in);
        }

        @Override
        public PlanResponseForSSAndUG[] newArray(int size) {
            return new PlanResponseForSSAndUG[size];
        }
    };

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NEETSSPlan> getNEETSSPlan() {
        return nEETSSPlan;
    }

    public void setNEETSSPlan(List<NEETSSPlan> nEETSSPlan) {
        this.nEETSSPlan = nEETSSPlan;
    }

    public List<NEETUGPlan> getNEETUGPlan() {
        return nEETUGPlan;
    }

    public void setNEETUGPlan(List<NEETUGPlan> nEETUGPlan) {
        this.nEETUGPlan = nEETUGPlan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        dest.writeString(message);
        dest.writeTypedList(nEETSSPlan);
        dest.writeTypedList(nEETUGPlan);
    }
}