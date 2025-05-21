package com.plaza.plazoleta.infraestructure.exception;

public class RestaurantAlreadyExistException extends RuntimeException {

    public RestaurantAlreadyExistException(String message) {
        super(message);
    }
}