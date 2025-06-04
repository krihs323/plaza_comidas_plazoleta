package com.plaza.plazoleta.application.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuUpdateRequest {

        @Digits(integer = 15, fraction = 0, message = "El precio debe ser númerico")
        private Long price;

        private String description;
}
