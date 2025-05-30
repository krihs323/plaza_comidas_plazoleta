package com.plaza.plazoleta.application.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailRequest {

    @Digits(integer = 3, fraction = 0, message = "Id del plato inválido")
    @PositiveOrZero
    private Long idMenu;

    @Digits(integer = 3, fraction = 0, message = "La cantidad debe ser númerica")
    @PositiveOrZero
    private Integer amount;
}
