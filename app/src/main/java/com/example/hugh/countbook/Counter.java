package com.example.hugh.countbook;

import java.util.Date;

/**
 * Created by hughc on 2017-09-23.
 */

public class Counter {
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

    public int getCurrentCounterValue() {
        return currentCounterValue;
    }

    public void incrementCurrentCounterValue() {
        this.currentCounterValue++;
    }

    public void decrementCurrentCounterValue() { this.currentCounterValue--; }

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
