package com.plaza.plazoleta.application.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RestaurantRequest {

        private String name;

        @Digits(integer = 15, fraction = 0, message = "El número de NIT debe ser númerico")
        @NotNull
        private Long numberId;

        private String address;

        private String phoneNumber;

        private String urlLogo;

        private Integer userId;



}
