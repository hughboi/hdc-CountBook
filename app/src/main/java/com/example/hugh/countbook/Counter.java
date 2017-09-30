package com.example.hugh.countbook;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by hughc on 2017-09-23.
 */

public class Counter implements Parcelable {
    private String counterName;
    private Date lastModifiedDate;
    private int currentCounterValue;
    private int initialCounterValue;
    private String comment;

    public Counter(String name, int initialValue){
        this.counterName = name;
        this.initialCounterValue = initialValue;
        this.currentCounterValue = initialValue;
        this.lastModifiedDate = new Date();
    }

    public Counter(String name, int initialValue, String comment){
        this.counterName = name;
        this.comment = comment;
        this.initialCounterValue = initialValue;
        this.currentCounterValue = initialValue;
        this.lastModifiedDate = new Date();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(counterName);
        dest.writeSerializable(lastModifiedDate);
        dest.writeInt(currentCounterValue);
        dest.writeInt(initialCounterValue);
        dest.writeString(comment);
    }

    public static final Parcelable.Creator<Counter> CREATOR = new Parcelable.Creator<Counter>() {
        public Counter createFromParcel(Parcel in){
            return new Counter(in);
        }

        public Counter[] newArray(int size){
            return new Counter[size];
        }
    };

    private Counter(Parcel in){
        counterName = in.readString();
        lastModifiedDate = (java.util.Date)in.readSerializable();
        currentCounterValue = in.readInt();
        initialCounterValue = in.readInt();
        comment = in.readString();
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getInitialCounterValue() { return initialCounterValue; }

    public void setInitialCounterValue(int i) { this.initialCounterValue = i; }

    public int getCurrentCounterValue() {
        return currentCounterValue;
    }

    public void setCurrentCounterValue(int i) { this.currentCounterValue = i; }

    public void incrementCurrentCounterValue() {
        this.currentCounterValue++;
    }

    public void decrementCurrentCounterValue() { this.currentCounterValue--; }

    public void resetCounterValue() { this.currentCounterValue = this.initialCounterValue; }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return counterName + " Counter Value: " + currentCounterValue;
    }
}
