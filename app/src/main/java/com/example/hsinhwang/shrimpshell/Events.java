package com.example.hsinhwang.shrimpshell;


import java.sql.Date;

public class Events {
    private int imageId;
    private String name, description;
    private Date startDate, endDate;

    public Events(int imageId, String name, String description, Date startDate, Date endDate) {
        this.imageId = imageId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
