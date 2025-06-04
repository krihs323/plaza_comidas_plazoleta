package com.plaza.plazoleta.domain.model;

public class Message {

    private String message;

    private String phoneNumber;

    public Message(String message, String phoneNumber) {
        this.message = message;
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}