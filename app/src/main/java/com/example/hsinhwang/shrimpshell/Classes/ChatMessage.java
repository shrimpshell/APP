package com.example.hsinhwang.shrimpshell.Classes;

public class ChatMessage {

    private String senderId, receiverId, senderGroupId, receiverGroupId, serviceItem;
    private int serviceId, status, quantity;

    public ChatMessage(String senderId, String receiverId, String senderGroupId,
                       String receiverGroupId, String serviceItem, int serviceId,
                       int status, int quantity) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderGroupId = senderGroupId;
        this.receiverGroupId = receiverGroupId;
        this.serviceItem = serviceItem;
        this.serviceId = serviceId;
        this.status = status;
        this.quantity = quantity;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderGroupId() {
        return senderGroupId;
    }

    public void setSenderGroupId(String senderGroupId) {
        this.senderGroupId = senderGroupId;
    }

    public String getReceiverGroupId() {
        return receiverGroupId;
    }

    public void setReceiverGroupId(String receiverGroupId) {
        this.receiverGroupId = receiverGroupId;
    }

    public String getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(String serviceItem) {
        this.serviceItem = serviceItem;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
