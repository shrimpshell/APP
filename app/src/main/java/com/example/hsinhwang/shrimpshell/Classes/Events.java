package com.example.hsinhwang.shrimpshell.Classes;


import java.io.Serializable;
import java.sql.Blob;

public class Events implements Serializable {
    private int eventId;
    private String name, description, start, end;
    private Blob eventImage;

    public Events(int eventId, String name, String description, String start, String end) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public String toString() {
        String text = "活動名稱: " + name +
                "\n活動期間: " + start + " ~ " + end +
                "\n活動內容: " + description;
        return text;
    }

    public void setEventImage(Blob eventImage) {
        this.eventImage = eventImage;
    }

    public Blob getEventImage() {
        return eventImage;
    }

    public int getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

}
