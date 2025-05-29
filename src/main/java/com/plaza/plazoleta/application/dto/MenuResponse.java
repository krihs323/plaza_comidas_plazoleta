package com.plaza.plazoleta.application.dto;

import com.plaza.plazoleta.domain.model.Category;
import com.plaza.plazoleta.domain.model.Restaurant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuResponse {

    private Long id;

    private String name;

    private Long price;

    private String description;

    private String urlLogo;

    private Category category;

    private Restaurant restaurant;

}
