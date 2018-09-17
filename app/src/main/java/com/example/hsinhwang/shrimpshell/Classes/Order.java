package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;
import java.util.List;

/*
 *消費者訂房資訊主檔
 */
public class Order implements Serializable {
    private int idRoomReservation;
    private String checkInDate;
    private String checkOuntDate;
    private String roomReservationStatus;
    private List<OrderDetail> orderDetailList;

    public Order() {
        super();
    }

    public Order(int idRoomReservation, String checkInDate, String checkOuntDate, String roomReservationStatus,
                 List<OrderDetail> orderDetailList) {
        super();
        this.idRoomReservation = idRoomReservation;
        this.checkInDate = checkInDate;
        this.checkOuntDate = checkOuntDate;
        this.roomReservationStatus = roomReservationStatus;
        this.orderDetailList = orderDetailList;
    }

    public int getIdRoomReservation() {
        return idRoomReservation;
    }

    public void setIdRoomReservation(int idRoomReservation) {
        this.idRoomReservation = idRoomReservation;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOuntDate() {
        return checkOuntDate;
    }

    public void setCheckOuntDate(String checkOuntDate) {
        this.checkOuntDate = checkOuntDate;
    }

    public String getRoomReservationStatus() {
        return roomReservationStatus;
    }

    public void setRoomReservationStatus(String roomReservationStatus) {
        this.roomReservationStatus = roomReservationStatus;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
