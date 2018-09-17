package com.example.hsinhwang.shrimpshell.Classes;

public class EmployeeClean {
    private int imageStatus;
    private String tvRooId;

    public EmployeeClean(int imageStatus, String tvRooId) {
        this.imageStatus = imageStatus;
        this.tvRooId = tvRooId;
    }

    public int getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(int imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getTvRooId() {
        return tvRooId;
    }

    public void setTvRooId(String tvRooId) {
        this.tvRooId = tvRooId;
    }
}
