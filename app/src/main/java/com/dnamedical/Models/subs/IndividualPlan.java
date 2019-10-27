package com.dnamedical.Models.subs;



import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndividualPlan implements Parcelable {

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

    public String getPack_key() {
        return pack_key;
    }

    public void setPack_key(String pack_key) {
        this.pack_key = pack_key;
    }

    @SerializedName("pack_key")
    @Expose
    private String pack_key;
    @SerializedName("discount")
    @Expose
    private String discount;

    protected IndividualPlan(Parcel in) {
        id = in.readString();
        name = in.readString();
        subname = in.readString();
        price = in.readString();
        discount = in.readString();
        pack_key = in.readString();
    }

    public static final Creator<IndividualPlan> CREATOR = new Creator<IndividualPlan>() {
        @Override
        public IndividualPlan createFromParcel(Parcel in) {
            return new IndividualPlan(in);
        }

        @Override
        public IndividualPlan[] newArray(int size) {
            return new IndividualPlan[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(subname);
        dest.writeString(price);
        dest.writeString(discount);
        dest.writeString(pack_key);
    }
}