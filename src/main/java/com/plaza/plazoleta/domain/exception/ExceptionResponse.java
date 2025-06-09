package com.plaza.plazoleta.domain.exception;

public enum ExceptionResponse {
    MENU_VALIATION("El id del usuario no corresponde al de un rol Propietario"),
    MENU_VALIATION_OWNER("El id del usuario no corresponde al propietario del restaurante"),
    RESTAURANT_VALIDATION_NOT_FOUND("No existe el restaurante"),
    CATEGORY_VALIDATION_NOT_FOUND("No existe la categoria"),
    ORDER_VALIDATION_NOT_VALID("No se puede crear un nuevo pedido, ya que hay un pedido actual en estado en preparacion, pendiente o listo"),
    RESTAURANT_VALIATION_NAME("El nombre del restaurante solo debe contener letras y números y no debe contener solo números"),
    VALIDATION_NUMBER_ID("El documento debe ser númerico"),
    VALIDATION_ADRESS("La dirección es obligatoria"),
    VALIDATION_PHONE_NUMBER("El teléfono debe ser valido"),
    VALIDATION_LOGO("El logo es obligatorio"),
    VALIDATION_USER_ID("El id del usuario debe ser obligatorio"),
    MENU_VALIATION_NAME("El nombre del plato solo debe contener solo letras"),
    VALIDATION_PRICE("El precio debe ser númerico"),
    VALIDATION_DESCRIPTION("La descripción solo debe contener solo letras"),
    VALIDATION_CATEGORY("Categoria no puede ser nula"),
    VALIDATION_RESTAURANT("Restaurante no puede ser nulo"),
    VALIDATION_PRICE_POSITIVE("El precio debe ser un número positivo"),
    ORDER_VALIDATION_NOT_FOUND("Orden no existe"),
    ORDER_VALIATION_RESTAURANT("Debe ingresar un restaurante"),
    ORDER_VALIATION_DETAIL("Debe ingresar el detalle"),
    ORDER_VALIDATION_NOT_IS_READY("Solo el pedido en estado Listo puede pasar a estado Entregado"),
    ORDER_VALIDATION_NOT_IS_PENDING("Solo se pueden cancelar pedidos que se encuentren en estado Pendiente"),
    MENU_VALIATION_NOT_FOUND("No se encontró el Plato que intenta modificar"),
    ORDER_OWNER_RESTAURANT_NOT_FOUND("El propietario no tiene un restaurante asignado"),
    ORDER_VALIATION_DETAIL_MENU_IS_NULL("El plato debe ir presente en el detalle del pedido"),
    ORDER_LIST_STATUS_IS_EMPTY("Debe ingresar el estado por el cual desea filtrar"),
    ORDER_VALIDATION_ORDER_NOT_ASIGNED_TO_EMPLOYEE("La orden que intenta modificar no esta asignada al empleado autenticado"),
    ORDER_VALIDATION_ORDER_NOT_ASIGNED_TO_RESTAURANT_EMPLOYEE("La orden que intenta modificar no esta asignada a su restaurante"),
    ORDER_VALIDATION_CUSTOMER_NOTALLOWED("Solo puede cancelar pedidos solicitados por el cliente");

    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}

