package com.apps.kunalfarmah.Spo2Watcher.model;

public class Time {

    private int hour, minutes, day, month, year;

    public Time(){}

    public Time(int hour, int minutes, int day, int month, int year) {
        this.hour = hour;
        this.minutes = minutes;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}
