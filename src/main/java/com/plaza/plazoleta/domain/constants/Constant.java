package com.plaza.plazoleta.domain.constants;

public enum Constant {

    MESSAGE_PIN("Pin para reclamar"),
    MESSAGE_ORDER_IN_PREPARATION("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse");

    private String value;

    Constant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
