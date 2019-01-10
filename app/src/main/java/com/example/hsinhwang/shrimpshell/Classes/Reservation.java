package com.example.hsinhwang.shrimpshell.Classes;

public class Reservation {
    public String reservationDate, checkInDate, checkOutDate, reservationStatus, roomGroup;
    public int roomReservationId, roomTypeId, quantity, extraBed, price, eventId, customerId;

    public Reservation(String reservationDate, String checkInDate, String checkOutDate, int extraBed, String reservationStatus, int quantity,
                       int customerId, int roomTypeId, int eventId, String roomGroup, int price) {
        super();
        this.reservationDate = reservationDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.extraBed = extraBed;
        this.quantity = quantity;
        this.reservationStatus = reservationStatus;
        this.customerId = customerId;
        this.roomTypeId = roomTypeId;
        this.eventId = eventId;
        this.roomGroup = roomGroup;
        this.price = price;
    }

    public Reservation(String reservationDate, String checkInDate, String checkOutDate, int extraBed, int quantity,
                       String reservationStatus, int customerId, int roomTypeId, String roomGroup, int price) {
        super();
        this.reservationDate = reservationDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.extraBed = extraBed;
        this.quantity = quantity;
        this.reservationStatus = reservationStatus;
        this.customerId = customerId;
        this.roomTypeId = roomTypeId;
        this.roomGroup = roomGroup;
        this.price = price;
    }

    public Reservation(int roomReservationId, int quantity) {
        super();
        this.roomReservationId = roomReservationId;
        this.quantity = quantity;
    }



    public Reservation(int roomReservationId) {
        super();
        this.roomReservationId = roomReservationId;
    }

    public String getReservationDate() {
        return reservationDate;
    }



    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
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



    public String getReservationStatus() {
        return reservationStatus;
    }



    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }



    public String getRoomGroup() {
        return roomGroup;
    }



    public void setRoomGroup(String roomGroup) {
        this.roomGroup = roomGroup;
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



    public int getExtraBed() {
        return extraBed;
    }



    public void setExtraBed(int extraBed) {
        this.extraBed = extraBed;
    }



    public int getPrice() {
        return price;
    }



    public void setPrice(int price) {
        this.price = price;
    }



    public int getEventId() {
        return eventId;
    }



    public void setEventId(int eventId) {
        this.eventId = eventId;
    }



    public int getCustomerId() {
        return customerId;
    }



    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRoomReservationId() {
        return roomReservationId;
    }

    public void setRoomReservationId(int roomReservationId) {
        this.roomReservationId = roomReservationId;
    }
}
