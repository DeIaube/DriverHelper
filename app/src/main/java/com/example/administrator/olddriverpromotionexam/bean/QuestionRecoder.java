package com.example.administrator.olddriverpromotionexam.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public class QuestionRecoder implements Parcelable {

    private String grade;
    private String timeConsum;
    private String date;

    @Override
    public String toString() {
        return "QuestionRecoder{" +
                "grade='" + grade + '\'' +
                ", timeConsum='" + timeConsum + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTimeConsum() {
        return timeConsum;
    }

    public void setTimeConsum(String timeConsum) {
        this.timeConsum = timeConsum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public QuestionRecoder(String grade, String timeConsum, String date) {

        this.grade = grade;
        this.timeConsum = timeConsum;
        this.date = date;
    }

    public QuestionRecoder() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.grade);
        dest.writeString(this.timeConsum);
        dest.writeString(this.date);
    }

    protected QuestionRecoder(Parcel in) {
        this.grade = in.readString();
        this.timeConsum = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<QuestionRecoder> CREATOR = new Parcelable.Creator<QuestionRecoder>() {
        @Override
        public QuestionRecoder createFromParcel(Parcel source) {
            return new QuestionRecoder(source);
        }

        @Override
        public QuestionRecoder[] newArray(int size) {
            return new QuestionRecoder[size];
        }
    };
}
