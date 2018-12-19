package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;

public class DinlingServiceMsg implements Serializable {
    private String tvDinling1, tvDinglin2; // 代表訊息內容
    private int image;
    private int number;


    public DinlingServiceMsg(String tvDinling1, String tvDinglin2, int image,
                             int number) {
        this.tvDinling1 = tvDinling1;
        this.tvDinglin2 = tvDinglin2;
        this.image = image;
        this.number = number;

    }

    public String getTvDinling1() {
        return tvDinling1;
    }

    public void setTvDinling1(String tvDinling1) {
        this.tvDinling1 = tvDinling1;
    }

    public String getTvDinglin2() {
        return tvDinglin2;
    }

    public void setTvDinglin2(String tvDinglin2) {
        this.tvDinglin2 = tvDinglin2;
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
