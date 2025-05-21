package com.plaza.plazoleta.domain.model;

import com.plaza.plazoleta.infraestructure.exception.RestaurantValidationException;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.RolType;

import java.time.LocalDate;
import java.time.Period;

public class Restaurant {

    private String name;
    private Long numberId;
    private String address;
    private String phoneNumber;
    private String urlLogo;
    private Long userId;

    public Restaurant(String name, Long numberId, String address, String phoneNumber, String urlLogo, Long userId) {
        this.name = name;
        this.numberId = numberId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.urlLogo = urlLogo;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumberId() {
        return numberId;
    }

    public void setNumberId(Long numberId) {
        this.numberId = numberId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
