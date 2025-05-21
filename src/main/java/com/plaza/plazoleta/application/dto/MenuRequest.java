package com.plaza.plazoleta.application.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequest {

        @Size(min = 1, max = 200)
        @Pattern(regexp = "^[a-zA-ZñÑáÁéÉíÍóÓúÚ\\d ]+$", message = "El nombre del plato solo debe contener solo letras")
        private String name;

        @Digits(integer = 15, fraction = 0, message = "El precio debe ser númerico")
        @PositiveOrZero
        private Long price;

        @Size(min = 1, max = 200)
        @Pattern(regexp = "^[a-zA-ZñÑáÁéÉíÍóÓúÚ\\d ]+$", message = "El nombre del plato solo debe contener solo letras")
        private String description;

        @NotNull
        @Size(min = 1, max = 100)
        private String urlLogo;

        @NotNull
        private Long categoriId;

        @NotNull
        private Long restaurantId;

}
