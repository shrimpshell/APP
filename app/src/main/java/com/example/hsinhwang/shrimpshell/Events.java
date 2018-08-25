package com.example.hsinhwang.shrimpshell;


import java.sql.Date;

public class Events {
    private String name, description;
    private Date start_date, end_date;

    public Events(String name, String description, Date start_date, Date end_date) {
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }
}
