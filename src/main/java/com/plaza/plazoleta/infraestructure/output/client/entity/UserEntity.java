package com.plaza.plazoleta.infraestructure.output.client.entity;

public class UserEntity {

    private String rol;

    private Long idUser;

    private Long idRestaurantEmployee;

    private String phoneNumber;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getIdRestaurantEmployee() {
        return idRestaurantEmployee;
    }

    public void setIdRestaurantEmployee(Long idRestaurantEmployee) {
        this.idRestaurantEmployee = idRestaurantEmployee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
