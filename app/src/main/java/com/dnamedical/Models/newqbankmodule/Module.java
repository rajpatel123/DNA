package com.dnamedical.Models.newqbankmodule;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Module implements Parcelable, Comparable<Module>, Cloneable {

    @SerializedName("chapter_id")
    @Expose
    private String chapterId;
    @SerializedName("chapter_name")
    @Expose
    private String chapterName;
    @SerializedName("chapter_image")
    @Expose
    private String chapterImage;
    @SerializedName("module_id")
    @Expose
    private String moduleId;
    @SerializedName("module_name")
    @Expose
    private String moduleName;
    @SerializedName("is_paid")
    @Expose
    private String isPaid;

    @SerializedName("module_submit_time")
    @Expose
    private long module_submit_time;
    @SerializedName("is_paused")
    @Expose
    private String isPaused;
    @SerializedName("price")
    @Expose
    private String price;


    public int getTotal_bookmarks() {
        return total_bookmarks;
    }

    public void setTotal_bookmarks(int total_bookmarks) {
        this.total_bookmarks = total_bookmarks;
    }

    @SerializedName("total_bookmarks")
    @Expose
    private Integer total_bookmarks;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("module_status")
    @Expose
    private String isCompleted;
    @SerializedName("total_mcq")
    @Expose
    private Integer totalMcq;
    @SerializedName("total_attemptedmcq")
    @Expose
    private Integer totalAttemptedmcq;

    protected Module(Parcel in) {
        chapterId = in.readString();
        chapterName = in.readString();
        chapterImage = in.readString();
        moduleId = in.readString();
        moduleName = in.readString();
        module_submit_time = in.readLong();
        isPaid = in.readString();
        isPaused = in.readString();
        price = in.readString();
        rating = in.readString();
        isCompleted = in.readString();
        if (in.readByte() == 0) {
            totalMcq = null;
        } else {
            totalMcq = in.readInt();
        }


        if (in.readByte() == 0) {
            total_bookmarks = null;
        } else {
            total_bookmarks = in.readInt();
        }
        if (in.readByte() == 0) {
            totalAttemptedmcq = null;
        } else {
            totalAttemptedmcq = in.readInt();
        }
    }


    @Override
    public int hashCode() {
        return Integer.parseInt(this.chapterId);
    }

    public static final Creator<Module> CREATOR = new Creator<Module>() {
        @Override
        public Module createFromParcel(Parcel in) {
            return new Module(in);
        }

        @Override
        public Module[] newArray(int size) {
            return new Module[size];
        }
    };

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterImage() {
        return chapterImage;
    }

    public void setChapterImage(String chapterImage) {
        this.chapterImage = chapterImage;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getIsPaused() {
        return isPaused;
    }

    public void setIsPaused(String isPaused) {
        this.isPaused = isPaused;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getTotalMcq() {
        return totalMcq;
    }

    public void setTotalMcq(Integer totalMcq) {
        this.totalMcq = totalMcq;
    }

    public Integer getTotalAttemptedmcq() {
        return totalAttemptedmcq;
    }

    public void setTotalAttemptedmcq(Integer totalAttemptedmcq) {
        this.totalAttemptedmcq = totalAttemptedmcq;
    }

    @Override
    public int compareTo(Module o) {
        return this.getChapterName().compareTo(o.getChapterName());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getModule_submit_time() {
        return module_submit_time;
    }

    public void setModule_submit_time(long module_submit_time) {
        this.module_submit_time = module_submit_time;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chapterId);
        dest.writeString(chapterName);
        dest.writeString(chapterImage);
        dest.writeString(moduleId);
        dest.writeString(moduleName);
        dest.writeString(isPaid);
        dest.writeLong(module_submit_time);
        dest.writeString(isPaused);
        dest.writeString(price);
        dest.writeString(rating);
        dest.writeString(isCompleted);
        if (totalMcq == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalMcq);
        }


        if (total_bookmarks == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(total_bookmarks);
        }
        if (totalAttemptedmcq == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalAttemptedmcq);
        }
    }


}