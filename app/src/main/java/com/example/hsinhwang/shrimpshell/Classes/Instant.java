package com.example.hsinhwang.shrimpshell.Classes;


public class Instant {
    private int IdInstantDetail, IdInstantService, Status, Quantity, IdInstantType, IdRoomStatus;
    private String RoomNumber;

    public Instant(int idInstantDetail, int idInstantService, String roomNumber, int status, int quantity, int idInstantType, int idRoomStatus) {
        IdInstantDetail = idInstantDetail;
        IdInstantService = idInstantService;
        Status = status;
        Quantity = quantity;
        IdInstantType = idInstantType;
        IdRoomStatus = idRoomStatus;
        RoomNumber = roomNumber;
    }

    public Instant(int idInstantDetail, int idInstantService, String roomNumber, int status, int quantity, int idInstantType) {
        IdInstantDetail = idInstantDetail;
        IdInstantService = idInstantService;
        Status = status;
        Quantity = quantity;
        IdInstantType = idInstantType;
        RoomNumber = roomNumber;
    }


    public Instant(int idInstantService, String roomNumber,int status, int quantity, int idInstantType, int idRoomStatus) {
        IdInstantService = idInstantService;
        Status = status;
        Quantity = quantity;
        IdInstantType = idInstantType;
        IdRoomStatus = idRoomStatus;
        RoomNumber = roomNumber;
    }

    public Instant(int idInstantService, String roomNumber,int status, int quantity, int idInstantType) {
        IdInstantService = idInstantService;
        Status = status;
        Quantity = quantity;
        IdInstantType = idInstantType;
        RoomNumber = roomNumber;
    }

    public Instant(int idInstantDetail, int status) {
        IdInstantDetail = idInstantDetail;
        Status = status;
    }

    public int getIdInstantDetail() {
        return IdInstantDetail;
    }

    public void setIdInstantDetail(int idInstantDetail) {
        IdInstantDetail = idInstantDetail;
    }

    public int getIdInstantService() {
        return IdInstantService;
    }

    public void setIdInstantService(int idInstantService) {
        IdInstantService = idInstantService;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getIdInstantType() {
        return IdInstantType;
    }

    public void setIdInstantType(int idInstantType) {
        IdInstantType = idInstantType;
    }

    public int getIdRoomStatus() {
        return IdRoomStatus;
    }

    public void setIdRoomStatus(int idRoomStatus) {
        IdRoomStatus = idRoomStatus;
    }

    public String getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        RoomNumber = roomNumber;
    }
}

