package com.example.hsinhwang.shrimpshell.Classes;


import java.io.Serializable;
import java.sql.Date;

public class Events implements Serializable {
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

    public String toString() {
        String text = "活動名稱: " + name +
                "\n活動期間: " + startDate + " ~ " + endDate+
                "\n活動內容: " + description;
        return text;
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
