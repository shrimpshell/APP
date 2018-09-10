package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;

public class RoomServiceMsg implements Serializable {
    private String tvRoomService1;
    private int image;
    private int number;

    public RoomServiceMsg(String tvRoomService1, int image, int number) {
        this.tvRoomService1 = tvRoomService1;
        this.image = image;
        this.number = number;
    }

    public String getTvRoomService1() {
        return tvRoomService1;
    }

    public void setTvRoomService1(String tvRoomService1) {
        this.tvRoomService1 = tvRoomService1;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
