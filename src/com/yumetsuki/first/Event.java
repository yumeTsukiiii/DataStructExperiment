package com.yumetsuki.first;

/**
 * 事件类，用于记录工作事件
 * */
public class Event {
    private Job job;
    private int arrivalTime;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
