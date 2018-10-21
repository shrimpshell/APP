package com.example.hsinhwang.shrimpshell.Classes;

public class OrderInstantDetail {
    private String diningTypeName, quantity, dtPrice, roomGroup;

    public OrderInstantDetail(String diningTypeName, String quantity, String dtPrice, String roomGroup) {
        this.diningTypeName = diningTypeName;
        this.quantity = quantity;
        this.dtPrice = dtPrice;
        this.roomGroup = roomGroup;
    }

    public String getDiningTypeName() {
        return diningTypeName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDtPrice() {
        return dtPrice;
    }

    public String getRoomGroup() {
        return roomGroup;
    }

}
