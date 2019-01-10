package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;
import java.sql.Blob;

@SuppressWarnings("serial")
public class RoomType implements Serializable {

    private int id, price, roomQuantity, adultQuantity, childQuantity, reservationQuantity;
    private String name, roomSize, bed, roomNumber;
    private Blob image;

    public RoomType(int id, String name, String roomSize, String bed, int adultQuantity, int childQuantity, int roomQuantity, int price) {
        super();
        this.id = id;
        this.name = name;
        this.roomSize = roomSize;
        this.bed = bed;
        this.adultQuantity = adultQuantity;
        this.childQuantity = childQuantity;
        this.roomQuantity = roomQuantity;
        this.price = price;
    }

    public RoomType(int id, String name, String roomSize, String bed, int adultQuantity, int childQuantity, int roomQuantity, int price, String roomNumber) {
        super();
        this.id = id;
        this.name = name;
        this.roomSize = roomSize;
        this.bed = bed;
        this.adultQuantity = adultQuantity;
        this.childQuantity = childQuantity;
        this.roomQuantity = roomQuantity;
        this.price = price;
        this.roomNumber = roomNumber;
    }

    public RoomType(int id, String name, String roomSize, String bed, int adultQuantity, int childQuantity, int roomQuantity, int price, int reservationQuantity) {
        super();
        this.id = id;
        this.name = name;
        this.roomSize = roomSize;
        this.bed = bed;
        this.adultQuantity = adultQuantity;
        this.childQuantity = childQuantity;
        this.roomQuantity = roomQuantity;
        this.price = price;
        this.reservationQuantity = reservationQuantity;
    }

    public RoomType(int id, String name, String roomSize, String bed, int adultQuantity, int childQuantity,
                    int roomQuantity) {
        super();
        this.id = id;
        this.name = name;
        this.roomSize = roomSize;
        this.bed = bed;
        this.adultQuantity = adultQuantity;
        this.childQuantity = childQuantity;
        this.roomQuantity = roomQuantity;
    }

    public RoomType(int id, int roomQuantity) {
        super();
        this.id = id;
        this.roomQuantity = roomQuantity;
    }

    public RoomType(int id, String roomNumber) {
        super();
        this.id = id;
        this.roomNumber = roomNumber;
    }

    public String getRoomSize() {
        return roomSize;
    }
    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }
    public String getBed() {
        return bed;
    }
    public void setBed(String bed) {
        this.bed = bed;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Blob getImage() {
        return image;
    }
    public void setImage(Blob image) {
        this.image = image;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getRoomQuantity() {
        return roomQuantity;
    }
    public void setRoomQuantity(int roomQuantity) {
        this.roomQuantity = roomQuantity;
    }
    public int getAdultQuantity() {
        return adultQuantity;
    }
    public void setAdultQuantity(int adultQuantity) {
        this.adultQuantity = adultQuantity;
    }
    public int getChildQuantity() {
        return childQuantity;
    }
    public void setChildQuantity(int childQuantity) {
        this.childQuantity = childQuantity;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getReservationQuantity() {
        return reservationQuantity;
    }

    public void setReservationQuantity(int reservationQuantity) {
        this.reservationQuantity = reservationQuantity;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getDetail() {
        String str = "房間型號：" + this.getRoomSize() + "\n" +
                "床型：" + this.getBed() + "\n" +
                "大人人數：" + this.getAdultQuantity() + "\n" +
                "小孩人數：" + this.getChildQuantity() + "\n" +
                "定價：＄" + this.getPrice();
        return "房間資訊\n" + str;
    }
}
