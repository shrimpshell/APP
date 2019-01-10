package com.example.hsinhwang.shrimpshell.Classes;

public class OrderRoomDetail {
    private String roomNumber, roomReservationStatus;
    private int idRoomStatus;

    public OrderRoomDetail(String roomNumber, String roomReservationStatus, int idRoomStatus) {
        this.roomNumber = roomNumber;
        this.roomReservationStatus = roomReservationStatus;
        this.idRoomStatus = idRoomStatus;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomReservationStatus() {
        return roomReservationStatus;
    }

    public void setRoomReservationStatus(String roomReservationStatus) {
        this.roomReservationStatus = roomReservationStatus;
    }

    public int getIdRoomStatus() {
        return idRoomStatus;
    }

    public void setIdRoomStatus(int idRoomStatus) {
        this.idRoomStatus = idRoomStatus;
    }
}
