package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;

public class ReservationDate implements Serializable {
    private String year, month, day, week, lastYear, lastMonth, lastDay, lastWeek, adultQuantity, childQuantity;

    public ReservationDate(String year, String month, String day, String week,
                           String lastYear, String lastMonth, String lastDay,
                           String lastWeek, String adultQuantity, String childQuantity) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.week = week;
        this.lastYear = lastYear;
        this.lastMonth = lastMonth;
        this.lastDay = lastDay;
        this.lastWeek = lastWeek;
        this.adultQuantity = adultQuantity;
        this.childQuantity = childQuantity;
    }

    public String getAdultQuantity() {
        return adultQuantity;
    }

    public void setAdultQuantity(String adultQuantity) {
        this.adultQuantity = adultQuantity;
    }

    public String getChildQuantity() {
        return childQuantity;
    }

    public void setChildQuantity(String childQuantity) {
        this.childQuantity = childQuantity;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getLastYear() {
        return lastYear;
    }

    public void setLastYear(String lastYear) {
        this.lastYear = lastYear;
    }

    public String getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(String lastMonth) {
        this.lastMonth = lastMonth;
    }

    public String getLastDay() {
        return lastDay;
    }

    public void setLastDay(String lastDay) {
        this.lastDay = lastDay;
    }

    public String getLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(String lastWeek) {
        this.lastWeek = lastWeek;
    }
}
