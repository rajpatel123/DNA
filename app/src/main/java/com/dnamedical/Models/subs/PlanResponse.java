package com.dnamedical.Models.subs;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class PlanResponse  implements Parcelable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Individual Plan")
    @Expose
    private List<IndividualPlan> individualPlan = null;
    @SerializedName("Combo Pack")
    @Expose
    private List<ComboPack> comboPack = null;

    protected PlanResponse(Parcel in) {
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        message = in.readString();
    }

    public static final Creator<PlanResponse> CREATOR = new Creator<PlanResponse>() {
        @Override
        public PlanResponse createFromParcel(Parcel in) {
            return new PlanResponse(in);
        }

        @Override
        public PlanResponse[] newArray(int size) {
            return new PlanResponse[size];
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

    public List<IndividualPlan> getIndividualPlan() {
        return individualPlan;
    }

    public void setIndividualPlan(List<IndividualPlan> individualPlan) {
        this.individualPlan = individualPlan;
    }

    public List<ComboPack> getComboPack() {
        return comboPack;
    }

    public void setComboPack(List<ComboPack> comboPack) {
        this.comboPack = comboPack;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        dest.writeString(message);
    }
}