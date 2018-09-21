package com.example.hsinhwang.shrimpshell.Classes;

public class ChatMessage {

    private String senderId;
    private String receiverId;
    private String senderGroupId;
    private String receiverGroupId;
    private int serviceId;

    public ChatMessage(String senderId, String receiverId, String senderGroupId, String receiverGroupId,
                       int serviceId) {
        super();
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderGroupId = senderGroupId;
        this.receiverGroupId = receiverGroupId;
        this.serviceId = serviceId;
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

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
