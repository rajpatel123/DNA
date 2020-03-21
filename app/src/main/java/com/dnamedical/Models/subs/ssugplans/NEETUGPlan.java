package com.dnamedical.Models.subs.ssugplans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NEETUGPlan implements Parcelable {

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
@SerializedName("discount")
@Expose
private String discount;
@SerializedName("pack_key")
@Expose
private String packKey;
@SerializedName("Combo")
@Expose
private List<Combo> combo = null;

    protected NEETUGPlan(Parcel in) {
        id = in.readString();
        name = in.readString();
        subname = in.readString();
        price = in.readString();
        discount = in.readString();
        packKey = in.readString();
        combo = in.createTypedArrayList(Combo.CREATOR);
    }

    public static final Creator<NEETUGPlan> CREATOR = new Creator<NEETUGPlan>() {
        @Override
        public NEETUGPlan createFromParcel(Parcel in) {
            return new NEETUGPlan(in);
        }

        @Override
        public NEETUGPlan[] newArray(int size) {
            return new NEETUGPlan[size];
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

public String getPackKey() {
return packKey;
}

public void setPackKey(String packKey) {
this.packKey = packKey;
}

public List<Combo> getCombo() {
return combo;
}

public void setCombo(List<Combo> combo) {
this.combo = combo;
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
        dest.writeString(packKey);
        dest.writeTypedList(combo);
    }
}
