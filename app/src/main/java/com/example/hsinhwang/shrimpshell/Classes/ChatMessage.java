package com.example.hsinhwang.shrimpshell.Classes;

public class ChatMessage {
    private String type;
    private String sender;
    private String receiver;
    private String state;
    private String item;
    private String quantity;

    public ChatMessage(String type, String sender, String receiver, String state, String item, String quantity) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.state = state;
        this.item = item;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
