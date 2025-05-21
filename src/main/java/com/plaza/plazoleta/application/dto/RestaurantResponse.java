package com.plaza.plazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponse {

    private String name;

    private Long numberId;

    private String address;

    private String phoneNumber;

    private String urlLogo;

    private Integer userId;
}
