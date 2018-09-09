package com.example.hsinhwang.shrimpshell.Classes;

public class RoomType {
    int roomTypeImageId, roomTypeLastQuantity, roomTypePrice;
    String roomTypeName, roomTypeSize, roomTypeBed, roomTypeAdult;

    public RoomType() {
        super();
    }

    public RoomType(int roomTypeImageId, int roomTypeLastQuantity,
                    int roomTypePrice, String roomTypeName, String roomTypeSize,
                    String roomTypeBed, String roomTypeAdult) {
        this.roomTypeImageId = roomTypeImageId;
        this.roomTypeLastQuantity = roomTypeLastQuantity;
        this.roomTypePrice = roomTypePrice;
        this.roomTypeName = roomTypeName;
        this.roomTypeSize = roomTypeSize;
        this.roomTypeBed = roomTypeBed;
        this.roomTypeAdult = roomTypeAdult;
    }

    public int getRoomTypeImageId() {
        return roomTypeImageId;
    }

    public void setRoomTypeImageId(int roomTypeImageId) {
        this.roomTypeImageId = roomTypeImageId;
    }

    public int getRoomTypeLastQuantity() {
        return roomTypeLastQuantity;
    }

    public void setRoomTypeLastQuantity(int roomTypeLastQuantity) {
        this.roomTypeLastQuantity = roomTypeLastQuantity;
    }

    public int getRoomTypePrice() {
        return roomTypePrice;
    }

    public void setRoomTypePrice(int roomTypePrice) {
        this.roomTypePrice = roomTypePrice;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getRoomTypeSize() {
        return roomTypeSize;
    }

    public void setRoomTypeSize(String roomTypeSize) {
        this.roomTypeSize = roomTypeSize;
    }

    public String getRoomTypeBed() {
        return roomTypeBed;
    }

    public void setRoomTypeBed(String roomTypeBed) {
        this.roomTypeBed = roomTypeBed;
    }

    public String getRoomTypeAdult() {
        return roomTypeAdult;
    }

    public void setRoomTypeAdult(String roomTypeAdult) {
        this.roomTypeAdult = roomTypeAdult;
    }
}
