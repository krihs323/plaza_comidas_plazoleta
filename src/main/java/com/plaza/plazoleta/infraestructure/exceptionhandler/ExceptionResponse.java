package com.plaza.plazoleta.infraestructure.exceptionhandler;

public enum ExceptionResponse {
    MENU_VALIATION("El id del usuario no corresponde al propietario del restaurante"),
    RESTAURANT_VALIDATION_NOT_FOUND("No existe el restaurante"),
    CATEGORY_VALIDATION_NOT_FOUND("No existe la categoria"),
    RESTAURANT_VALIDATION_NOT_ROL_VALID("El id del usuario no corresponde al de un rol Propietario");


    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
