package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;

/*
 *消費者訂房資訊明細檔
 */
public class OrderDetail implements Serializable{
	private String roomNumber;//房號
	private String roomLevel;//房間等級
	private String roomType;//房型名稱
	private String price;//房間價格
	private String diningTypeName;//餐點名稱
	private String quantity;//餐點數量
	private String dtPrice;//餐點價格
	private String discount;//訂房時有在優惠活動範圍內的折扣,也可能沒有

	public OrderDetail() {
		super();
	}

	public OrderDetail(String roomNumber, String roomLevel, String roomType, String price, String diningTypeName, String quantity, String dtPrice, String discount) {
		this.roomNumber = roomNumber;
		this.roomLevel = roomLevel;
		this.roomType = roomType;
		this.price = price;
		this.diningTypeName = diningTypeName;
		this.quantity = quantity;
		this.dtPrice = dtPrice;
		this.discount = discount;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomLevel() {
		return roomLevel;
	}

	public void setRoomLevel(String roomLevel) {
		this.roomLevel = roomLevel;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiningTypeName() {
		return diningTypeName;
	}

	public void setDiningTypeName(String diningTypeName) {
		this.diningTypeName = diningTypeName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDtPrice() {
		return dtPrice;
	}

	public void setDtPrice(String dtPrice) {
		this.dtPrice = dtPrice;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
}
