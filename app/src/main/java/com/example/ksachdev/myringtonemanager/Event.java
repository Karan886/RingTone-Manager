package com.example.ksachdev.myringtonemanager;

import android.util.Log;

/**
 * Created by ksachdev on 2018-03-31.
 */

public class Event {
    private static String TAG = "event class";

    private String title;
    private String desc;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private int[] alarmID;
    private int key;
    private int imgResource = 0;




    public Event(String title, String desc, String startDate, String startTime, String endDate, String endTime) {
        this.title = title;
        this.desc = desc;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    public Event(String title, String desc, String startDate, String startTime, String endDate, String endTime, int imgResource) {
        this.title = title;
        this.desc = desc;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.imgResource = imgResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int[] getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int[] alarmID) {
        this.alarmID = alarmID;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setImgResource(int resourceID){
        this.imgResource = resourceID;
    }
    public int getImgResource(){
        return this.imgResource;
    }



}
