package com.example.hsinhwang.shrimpshell.Classes;

public class EmployeeClean {
    private int imageStatus, tvStatusNumber;
    private String tvRooId;

    public EmployeeClean(int imageStatus, int tvStatusNumber, String tvRooId) {
        this.imageStatus = imageStatus;
        this.tvStatusNumber = tvStatusNumber;
        this.tvRooId = tvRooId;
    }

    public int getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(int imageStatus) {
        this.imageStatus = imageStatus;
    }

    public int getTvStatusNumber() {
        return tvStatusNumber;
    }

    public void setTvStatusNumber(int tvStatusNumber) {
        this.tvStatusNumber = tvStatusNumber;
    }

    public String getTvRooId() {
        return tvRooId;
    }

    public void setTvRooId(String tvRooId) {
        this.tvRooId = tvRooId;
    }
}
