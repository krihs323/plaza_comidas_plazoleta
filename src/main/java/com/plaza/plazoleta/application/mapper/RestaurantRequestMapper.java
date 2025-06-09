package com.plaza.plazoleta.application.mapper;

import com.plaza.plazoleta.application.dto.RestaurantListResponse;
import com.plaza.plazoleta.application.dto.RestaurantRequest;
import com.plaza.plazoleta.domain.model.PageResult;
import com.plaza.plazoleta.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantRequestMapper {

    Restaurant toRestaurant(RestaurantRequest restaurantRequest);

    RestaurantListResponse toRestaurantListRespones(Restaurant restaurant);

    PageResult<RestaurantListResponse> toPageResultResponse(PageResult<Restaurant> pageResult);
}
