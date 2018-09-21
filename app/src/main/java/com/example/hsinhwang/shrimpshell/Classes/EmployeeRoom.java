package com.example.hsinhwang.shrimpshell.Classes;

public class EmployeeRoom {
    private int imageStatus,tvStatusNumber;
    private String tvRoomId,tvItem,tvQuantity;

    public EmployeeRoom(int imageStatus, int tvStatusNumber, String tvRoomId, String tvItem, String tvQuantity) {
        this.imageStatus = imageStatus;
        this.tvStatusNumber = tvStatusNumber;
        this.tvRoomId = tvRoomId;
        this.tvItem = tvItem;
        this.tvQuantity = tvQuantity;
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

    public String getTvRoomId() {
        return tvRoomId;
    }

    public void setTvRoomId(String tvRoomId) {
        this.tvRoomId = tvRoomId;
    }

    public String getTvItem() {
        return tvItem;
    }

    public void setTvItem(String tvItem) {
        this.tvItem = tvItem;
    }

    public String getTvQuantity() {
        return tvQuantity;
    }

    public void setTvQuantity(String tvQuantity) {
        this.tvQuantity = tvQuantity;
    }
}
