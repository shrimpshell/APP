package com.example.hsinhwang.shrimpshell.Classes;

public class Reservation {
    private String roomTypeName, checkInDate, checkOutDate, quantity;

    public Reservation(String roomTypeName, String checkInDate, String checkOutDate,
                       String quantity) {
        this.roomTypeName = roomTypeName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.quantity = quantity;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
