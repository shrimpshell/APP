package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;
import java.sql.Blob;

public class Rooms implements Serializable {
    private int roomId, roomAdult, roomChild, roomQuantity;
    private String roomName, roomSize, roomBed;
    Blob image;

    public Rooms(int roomId, String roomName, String roomSize, String roomBed, int roomAdult, int roomChild, int roomQuantity) {
        this.roomId = roomId;
        this.roomAdult = roomAdult;
        this.roomChild = roomChild;
        this.roomQuantity = roomQuantity;
        this.roomName = roomName;
        this.roomSize = roomSize;
        this.roomBed = roomBed;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomAdult() {
        return roomAdult;
    }

    public void setRoomAdult(int roomAdult) {
        this.roomAdult = roomAdult;
    }

    public int getRoomChild() {
        return roomChild;
    }

    public void setRoomChild(int roomChild) {
        this.roomChild = roomChild;
    }

    public int getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(int roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public String getRoomBed() {
        return roomBed;
    }

    public void setRoomBed(String roomBed) {
        this.roomBed = roomBed;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
