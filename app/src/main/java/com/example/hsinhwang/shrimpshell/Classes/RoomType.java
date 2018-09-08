package com.example.hsinhwang.shrimpshell.Classes;

public class RoomType {
    int ivRoomType, tvRoomTypeLastQuantity, tvRoomTypePrice;
    String tvRoomTypeName, tvRoomTypeSize, tvRoomTypeBed, tvRoomTypeAdult;

    public RoomType() {
        super();
    }

    public RoomType(int ivRoomType, int tvRoomTypeLastQuantity,
                    int tvRoomTypePrice, String tvRoomTypeName,
                    String tvRoomTypeSize, String tvRoomTypeBed,
                    String tvRoomTypeAdult) {
        super();
        this.ivRoomType = ivRoomType;
        this.tvRoomTypeLastQuantity = tvRoomTypeLastQuantity;
        this.tvRoomTypePrice = tvRoomTypePrice;
        this.tvRoomTypeName = tvRoomTypeName;
        this.tvRoomTypeSize = tvRoomTypeSize;
        this.tvRoomTypeBed = tvRoomTypeBed;
        this.tvRoomTypeAdult = tvRoomTypeAdult;
    }

    public int getIvRoomType() {
        return ivRoomType;
    }

    public void setIvRoomType(int ivRoomType) {
        this.ivRoomType = ivRoomType;
    }

    public int getTvRoomTypeLastQuantity() {
        return tvRoomTypeLastQuantity;
    }

    public void setTvRoomTypeLastQuantity(int tvRoomTypeLastQuantity) {
        this.tvRoomTypeLastQuantity = tvRoomTypeLastQuantity;
    }

    public int getTvRoomTypePrice() {
        return tvRoomTypePrice;
    }

    public void setTvRoomTypePrice(int tvRoomTypePrice) {
        this.tvRoomTypePrice = tvRoomTypePrice;
    }

    public String getTvRoomTypeName() {
        return tvRoomTypeName;
    }

    public void setTvRoomTypeName(String tvRoomTypeName) {
        this.tvRoomTypeName = tvRoomTypeName;
    }

    public String getTvRoomTypeSize() {
        return tvRoomTypeSize;
    }

    public void setTvRoomTypeSize(String tvRoomTypeSize) {
        this.tvRoomTypeSize = tvRoomTypeSize;
    }

    public String getTvRoomTypeBed() {
        return tvRoomTypeBed;
    }

    public void setTvRoomTypeBed(String tvRoomTypeBed) {
        this.tvRoomTypeBed = tvRoomTypeBed;
    }

    public String getTvRoomTypeAdult() {
        return tvRoomTypeAdult;
    }

    public void setTvRoomTypeAdult(String tvRoomTypeAdult) {
        this.tvRoomTypeAdult = tvRoomTypeAdult;
    }
}
