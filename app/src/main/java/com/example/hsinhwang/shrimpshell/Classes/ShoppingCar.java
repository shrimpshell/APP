package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;

public class ShoppingCar implements Serializable {
    private String roomTypeName, checkInDate, checkOutDate;
    private int roomTypeId, quantity, addBed, price;

    public ShoppingCar(int roomTypeId, String roomTypeName, String checkInDate, String checkOutDate, int quantity, int price) {
        this.roomTypeName = roomTypeName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomTypeId = roomTypeId;
        this.quantity = quantity;
        this.price = price;
    }

    public ShoppingCar(int roomTypeId, String roomTypeName, String checkInDate, String checkOutDate, int quantity, int addBed, int price) {
        this.roomTypeName = roomTypeName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomTypeId = roomTypeId;
        this.quantity = quantity;
        this.addBed = addBed;
        this.price = price;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAddBed() {
        return addBed;
    }

    public void setAddBed(int addBed) {
        this.addBed = addBed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
