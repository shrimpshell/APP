package com.example.hsinhwang.shrimpshell.Classes;


public class Instant {
    private int Status,Quantity,Price,IdDinling,IdCleanService,IdRoomService,
            IdRoomNumber,IdEmployee;
    private String DinlingTypeName,Type,EmployeeCode;

    public Instant(int status, int quantity, int price, int idDinling, int idRoomNumber,
                   int idEmployee, String dinlingTypeName) {
        Status = status;
        Quantity = quantity;
        Price = price;
        IdDinling = idDinling;
        IdRoomNumber = idRoomNumber;
        IdEmployee = idEmployee;
        DinlingTypeName = dinlingTypeName;
    }

    public Instant(int status, int quantity, int idRoomService, int idRoomNumber,
                   int idEmployee, String type) {
        Status = status;
        Quantity = quantity;
        IdRoomService = idRoomService;
        IdRoomNumber = idRoomNumber;
        IdEmployee = idEmployee;
        Type = type;
    }

    public Instant(int status, int idCleanService, int idRoomNumber, int idEmployee) {
        Status = status;
        IdCleanService = idCleanService;
        IdRoomNumber = idRoomNumber;
        IdEmployee = idEmployee;
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

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getIdDinling() {
        return IdDinling;
    }

    public void setIdDinling(int idDinling) {
        IdDinling = idDinling;
    }

    public int getIdCleanService() {
        return IdCleanService;
    }

    public void setIdCleanService(int idCleanService) {
        IdCleanService = idCleanService;
    }

    public int getIdRoomService() {
        return IdRoomService;
    }

    public void setIdRoomService(int idRoomService) {
        IdRoomService = idRoomService;
    }

    public int getIdRoomNumber() {
        return IdRoomNumber;
    }

    public void setIdRoomNumber(int idRoomNumber) {
        IdRoomNumber = idRoomNumber;
    }

    public int getIdEmployee() {
        return IdEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        IdEmployee = idEmployee;
    }

    public String getDinlingTypeName() {
        return DinlingTypeName;
    }

    public void setDinlingTypeName(String dinlingTypeName) {
        DinlingTypeName = dinlingTypeName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getEmployeeCode() {
        return EmployeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        EmployeeCode = employeeCode;
    }
}

