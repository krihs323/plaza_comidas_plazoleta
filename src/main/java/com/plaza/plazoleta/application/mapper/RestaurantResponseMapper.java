package com.plaza.plazoleta.application.mapper;

import com.plaza.plazoleta.application.dto.RestaurantResponse;
import com.plaza.plazoleta.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantResponseMapper {

    RestaurantResponse toResponse(Restaurant restaurant);

}
