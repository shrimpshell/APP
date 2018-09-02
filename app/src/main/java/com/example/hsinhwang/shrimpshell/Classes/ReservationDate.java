package com.example.hsinhwang.shrimpshell.Classes;

import android.widget.TextView;

import java.io.Serializable;

public class ReservationDate implements Serializable {
    private TextView year, month, day, week, lastYear, lastMonth, lastDay, lastWeek;

    public ReservationDate(TextView year, TextView month, TextView day, TextView week,
                           TextView lastYear, TextView lastMonth, TextView lastDay, TextView lastWeek) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.week = week;
        this.lastYear = lastYear;
        this.lastMonth = lastMonth;
        this.lastDay = lastDay;
        this.lastWeek = lastWeek;
    }

    public TextView getYear() {
        return year;
    }

    public void setYear(TextView year) {
        this.year = year;
    }

    public TextView getMonth() {
        return month;
    }

    public void setMonth(TextView month) {
        this.month = month;
    }

    public TextView getDay() {
        return day;
    }

    public void setDay(TextView day) {
        this.day = day;
    }

    public TextView getWeek() {
        return week;
    }

    public void setWeek(TextView week) {
        this.week = week;
    }

    public TextView getLastYear() {
        return lastYear;
    }

    public void setLastYear(TextView lastYear) {
        this.lastYear = lastYear;
    }

    public TextView getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(TextView lastMonth) {
        this.lastMonth = lastMonth;
    }

    public TextView getLastDay() {
        return lastDay;
    }

    public void setLastDay(TextView lastDay) {
        this.lastDay = lastDay;
    }

    public TextView getLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(TextView lastWeek) {
        this.lastWeek = lastWeek;
    }
}
