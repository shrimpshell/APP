package com.example.hsinhwang.shrimpshell.CustomerPanel;

        import java.io.Serializable;
        import java.util.Date;

public class ProfileReceiptDetail implements Serializable {
    private String order_id;
    private Date date_start;
    private Date date_end;
    private String orderNumber;
    private String roomType;
    private String mealsQuantity;
    private String meals;
    private String discounted;
    private String roomQuantity;
    private String totalPrice;
    private String result;

    public ProfileReceiptDetail() {
        super();
    }

    public ProfileReceiptDetail(String order_id, Date date_start, Date date_end, String orderNumber, String roomType, String mealsQuantity, String meals, String discounted, String roomQuantity, String totalPrice, String result) {
        this.order_id = order_id;
        this.date_start = date_start;
        this.date_end = date_end;
        this.orderNumber = orderNumber;
        this.roomType = roomType;
        this.mealsQuantity = mealsQuantity;
        this.meals = meals;
        this.discounted = discounted;
        this.roomQuantity = roomQuantity;
        this.totalPrice = totalPrice;
        this.result = result;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getMealsQuantity() {
        return mealsQuantity;
    }

    public void setMealsQuantity(String mealsQuantity) {
        this.mealsQuantity = mealsQuantity;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public String getDiscounted() {
        return discounted;
    }

    public void setDiscounted(String discounted) {
        this.discounted = discounted;
    }

    public String getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(String roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public String getTotal_price() {
        return totalPrice;
    }

    public void setTotal_price(String total_price) {
        this.totalPrice = total_price;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}