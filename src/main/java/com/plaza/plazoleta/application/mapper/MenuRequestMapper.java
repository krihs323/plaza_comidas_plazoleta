package com.plaza.plazoleta.application.mapper;

import com.plaza.plazoleta.application.dto.MenuDisableRequest;
import com.plaza.plazoleta.application.dto.MenuRequest;
import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MenuRequestMapper {

    @Mapping(source = "menuRequest.restaurantId", target = "restaurant.id")
    @Mapping(source = "menuRequest.categoriId", target = "category.id")
    Menu toMenu(MenuRequest menuRequest);

    @Mapping(source = "menuRequest.restaurantId", target = "id")
    Restaurant toRestaurant(MenuRequest menuRequest);


    Menu toMenu(MenuDisableRequest menuDisableRequest);

}
