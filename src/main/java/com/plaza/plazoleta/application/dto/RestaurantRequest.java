package com.plaza.plazoleta.application.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RestaurantRequest {

        @Size(min = 1, max = 200)
        @Pattern(regexp = "^(?!\\d+$)[a-zA-ZñÑáÁéÉíÍóÓúÚ\\d ]+$", message = "El nombre del restaurante solo debe contener letras y números y no debe contener solo números")
        private String name;

        @Digits(integer = 15, fraction = 0, message = "El número de NIT debe ser númerico")
        @NotNull
        private Long numberId;

        @NotNull
        @Size(min = 1, max = 100)
        private String address;

        @Pattern(regexp = "^\\+?\\d{1,13}$", message = "El número teléfonico no es valido")
        @NotNull
        private String phoneNumber;

        @NotNull
        @Size(min = 1, max = 100)
        private String urlLogo;

        @NotNull
        private Integer userId;



}
