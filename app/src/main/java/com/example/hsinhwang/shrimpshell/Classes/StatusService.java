package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;

public class StatusService implements Serializable {
    private int image;
    private String tvitem,tvquantity;

    public StatusService(int image, String tvitem, String tvquantity) {
        this.image = image;
        this.tvitem = tvitem;
        this.tvquantity = tvquantity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTvitem() {
        return tvitem;
    }

    public void setTvitem(String tvitem) {
        this.tvitem = tvitem;
    }

    public String getTvquantity() {
        return tvquantity;
    }

    public void setTvquantity(String tvquantity) {
        this.tvquantity = tvquantity;
    }
}
