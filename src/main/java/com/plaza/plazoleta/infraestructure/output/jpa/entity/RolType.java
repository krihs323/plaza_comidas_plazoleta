package com.plaza.plazoleta.infraestructure.output.jpa.entity;

public enum RolType {

    ADMIN(1),
    OWNER(2),
    EMPLOYEE(3),
    CUSTOMER(4);

    private int value;

    RolType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
