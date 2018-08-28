package com.example.hsinhwang.shrimpshell.Classes;

public class Rooms {
    private int roomImageId, roomId;
    private String roomName, roomiDetail;

    public Rooms(int roomImageId, int roomId, String roomName, String roomiDetail) {
        this.roomImageId = roomImageId;
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomiDetail = roomiDetail;
    }

    public int getRoomImageId() {
        return roomImageId;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomiDetail() {
        return roomiDetail;
    }
}
