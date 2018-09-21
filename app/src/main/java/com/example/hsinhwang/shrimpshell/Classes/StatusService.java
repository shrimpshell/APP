package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;

public class StatusService implements Serializable {
    private int image;
    private String tvitem,tvquantity,tvservice;

    public StatusService(int image, String tvitem, String tvquantity, String tvservice) {
        this.image = image;
        this.tvitem = tvitem;
        this.tvquantity = tvquantity;
        this.tvservice = tvservice;
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

    public String getTvservice() {
        return tvservice;
    }

    public void setTvservice(String tvservice) {
        this.tvservice = tvservice;
    }
}
