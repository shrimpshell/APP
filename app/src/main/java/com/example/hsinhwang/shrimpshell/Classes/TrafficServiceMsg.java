package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;

public class TrafficServiceMsg implements Serializable {
    private String tvTraffic1;
    private int image;
    private int number;

    public TrafficServiceMsg(String tvTraffic1, int image, int number) {
        this.tvTraffic1 = tvTraffic1;
        this.image = image;
        this.number = number;
    }

    public String getTvTraffic1() {
        return tvTraffic1;
    }

    public void setTvTraffic1(String tvTraffic1) {
        this.tvTraffic1 = tvTraffic1;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
