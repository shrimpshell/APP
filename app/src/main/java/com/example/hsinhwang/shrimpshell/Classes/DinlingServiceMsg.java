package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;

public class DinlingServiceMsg implements Serializable {
    public final static int TYPE_UNFINISH = 0;
    public final static int TYPE_DOING = 1;
    public final static int TYPE_FINISH = 2;
    private String tvDinling1, tvDinglin2; // 代表訊息內容
    private int image;
    private int number;
    private int type;// 點擊事件代碼

    public DinlingServiceMsg(String tvDinling1, String tvDinglin2, int image,
                             int number, int type) {
        this.tvDinling1 = tvDinling1;
        this.tvDinglin2 = tvDinglin2;
        this.image = image;
        this.number = number;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
