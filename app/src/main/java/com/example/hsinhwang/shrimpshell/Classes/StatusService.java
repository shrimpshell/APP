package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;

public class StatusService implements Serializable {
    private int image;
    private String tvitem,tvstatus;

    public StatusService(int image, String tvitem, String tvstatus) {
        this.image = image;
        this.tvitem = tvitem;
        this.tvstatus = tvstatus;
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

    public String getTvstatus() {
        return tvstatus;
    }

    public void setTvstatus(String tvstatus) {
        this.tvstatus = tvstatus;
    }
}
