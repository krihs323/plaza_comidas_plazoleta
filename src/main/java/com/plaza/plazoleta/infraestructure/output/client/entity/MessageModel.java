package com.plaza.plazoleta.infraestructure.output.client.entity;

public class MessageModel {

    private String message;

    private Long phoneNumber;

    public MessageModel(String message, Long phoneNumber) {
        this.message = message;
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
