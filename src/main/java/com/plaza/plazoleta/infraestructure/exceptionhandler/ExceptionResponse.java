package com.plaza.plazoleta.infraestructure.exceptionhandler;

public enum ExceptionResponse {
    MENU_VALIATION("El id del usuario no corresponde al de un rol Propietario"),
    MENU_VALIATION_OWNER("El id del usuario no corresponde al propietario del restaurante"),
    RESTAURANT_VALIDATION_NOT_FOUND("No existe el restaurante"),
    MENU_VALIDATION_USER_NOT_VALID("El id del usuario no corresponde al propietario del restaurante"),
    CATEGORY_VALIDATION_NOT_FOUND("No existe la categoria"),
    RESTAURANT_VALIDATION_NOT_ROL_VALID("El id del usuario no corresponde al de un rol Propietario"),
    ORDER_VALIDATION_NOT_VALID("No se puede crear un nuevo pedido, ya que hay un pedido actual en estado en preparacion, pendiente o listo")
    ;

    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}

