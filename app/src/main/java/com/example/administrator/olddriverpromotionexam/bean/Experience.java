package com.example.administrator.olddriverpromotionexam.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public class Experience implements Parcelable {
    private String title;
    private String content;

    public Experience(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Experience() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
    }

    protected Experience(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<Experience> CREATOR = new Parcelable.Creator<Experience>() {
        @Override
        public Experience createFromParcel(Parcel source) {
            return new Experience(source);
        }

        @Override
        public Experience[] newArray(int size) {
            return new Experience[size];
        }
    };
}
