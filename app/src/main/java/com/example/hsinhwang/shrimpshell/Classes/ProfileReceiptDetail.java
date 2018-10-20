package com.example.hsinhwang.shrimpshell.Classes;

        import android.os.Bundle;

        import java.io.Serializable;
        import java.util.Date;

public class ProfileReceiptDetail implements Serializable {
    private int idRoomReservation;
    private String checkInDate, checkOuntDate, roomNumber, price, roomQuantity, roomTypeName, roomReservationStatus;
    public ProfileReceiptDetail(int idRoomReservation, String checkInDate, String checkOuntDate,
                                String roomNumber, String price, String roomQuantity, String RoomTypeName, String roomReservationStatus) {
        super();
        this.idRoomReservation = idRoomReservation;
        this.price = price;
        this.checkInDate = checkInDate;
        this.checkOuntDate = checkOuntDate;
        this.roomNumber = roomNumber;
        this.roomQuantity = roomQuantity;
        this.roomTypeName = RoomTypeName;
        this.roomReservationStatus = roomReservationStatus;
    }

    public ProfileReceiptDetail(int idRoomReservation){
        this.idRoomReservation = idRoomReservation;
    }

    public int getIdRoomReservation() {
        return idRoomReservation;
    }
    public String getPrice() {
        return price;
    }
    public String getCheckInDate() {
        return checkInDate;
    }
    public String getCheckOuntDate() {
        return checkOuntDate;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public String getRoomQuantity() {
        return roomQuantity;
    }
    public String getRoomTypeName() {
        return roomTypeName;
    }
    public String getRoomReservationStatus() {
        return roomReservationStatus;
    }

}