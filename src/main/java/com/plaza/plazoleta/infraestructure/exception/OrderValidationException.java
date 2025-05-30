package com.plaza.plazoleta.infraestructure.exception;

public class OrderValidationException extends RuntimeException {

    public OrderValidationException(String message) {
        super(message);
    }

}
