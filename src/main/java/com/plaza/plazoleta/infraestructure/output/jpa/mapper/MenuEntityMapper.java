package com.plaza.plazoleta.infraestructure.output.jpa.mapper;

import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.MenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MenuEntityMapper {

    @Mapping(source = "restaurant", target = "restaurantEntity")
    @Mapping(source = "category", target = "categoryEntity")
    MenuEntity toEntity(Menu menu);


    Menu toMenu(MenuEntity menuEntity);

}
