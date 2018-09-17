package com.example.hsinhwang.shrimpshell.Classes;

public class EmployeeDinling {
    private int imageStatus;
    private String tvRoomId,tvItem,tvQuantity;

    public EmployeeDinling(int imageStatus, String tvRoomId, String tvItem, String tvQuantity) {
        this.imageStatus = imageStatus;
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
