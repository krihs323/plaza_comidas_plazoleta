package com.plaza.plazoleta.infraestructure.exceptionhandler;

public enum ExceptionResponse {
    MENU_VALIATION("El id del usuario no corresponde al de un rol Propietario"),
    RESTAURANT_VALIDATION_NOT_FOUND("No existe el restaurante"),
    MENU_VALIDATION_USER_NOT_VALID("El id del usuario no corresponde al propietario del restaurante"),
    ;

    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}