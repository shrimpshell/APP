package com.example.hsinhwang.shrimpshell;

public class MainOptions {
    private int optionId, imageId;
    private String optionTitle;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public MainOptions(int optionId, String optionTitle, int imageId) {
        this.optionId = optionId;

        this.optionTitle = optionTitle;
        this.imageId = imageId;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }
}
