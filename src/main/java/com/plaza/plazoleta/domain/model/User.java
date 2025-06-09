package com.plaza.plazoleta.domain.model;

public class User {

    private String rol;

    private Long idUser;

    private Long idRestaurantEmployee;

    private String phoneNumber;

    private String name;

    private String lastName;

    public User() {
    }

    public User(String rol, Long idUser) {
        this.rol = rol;
        this.idUser = idUser;
    }

    public User(String rol, Long idUser, Long idRestaurantEmployee) {
        this.rol = rol;
        this.idUser = idUser;
        this.idRestaurantEmployee = idRestaurantEmployee;
    }

    public User(String rol, Long idUser, Long idRestaurantEmployee, String phoneNumber, String name, String lastName) {
        this.rol = rol;
        this.idUser = idUser;
        this.idRestaurantEmployee = idRestaurantEmployee;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.lastName = lastName;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
