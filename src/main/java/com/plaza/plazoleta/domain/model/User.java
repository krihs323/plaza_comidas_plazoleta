package com.plaza.plazoleta.domain.model;

public class User {

    private String rol;

    private Long idUser;

    public User() {
    }


    public User(String rol, Long idUser) {
        this.rol = rol;
        this.idUser = idUser;
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
}
