package com.example.hsinhwang.shrimpshell.Classes;

public class EmployeeInstantCall {
    private int image;
    private String tvRoomId,tvState;

    public EmployeeInstantCall(int image, String tvRoomId, String tvState) {
        this.image = image;
        this.tvRoomId = tvRoomId;
        this.tvState = tvState;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTvRoomId() {
        return tvRoomId;
    }

    public void setTvRoomId(String tvRoomId) {
        this.tvRoomId = tvRoomId;
    }

    public String getTvState() {
        return tvState;
    }

    public void setTvState(String tvState) {
        this.tvState = tvState;
    }
}
