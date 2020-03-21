package com.dnamedical.Models.subs.ssugplans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NEETSSPlan implements Parcelable {

@SerializedName("DM")
@Expose
private List<DM> dM = null;
@SerializedName("MCH")
@Expose
private List<MCH> mCH = null;

    protected NEETSSPlan(Parcel in) {
        dM = in.createTypedArrayList(DM.CREATOR);
        mCH = in.createTypedArrayList(MCH.CREATOR);
    }

    public static final Creator<NEETSSPlan> CREATOR = new Creator<NEETSSPlan>() {
        @Override
        public NEETSSPlan createFromParcel(Parcel in) {
            return new NEETSSPlan(in);
        }

        @Override
        public NEETSSPlan[] newArray(int size) {
            return new NEETSSPlan[size];
        }
    };

    public List<DM> getDM() {
return dM;
}

public void setDM(List<DM> dM) {
this.dM = dM;
}

public List<MCH> getMCH() {
return mCH;
}

public void setMCH(List<MCH> mCH) {
this.mCH = mCH;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(dM);
        dest.writeTypedList(mCH);
    }
}