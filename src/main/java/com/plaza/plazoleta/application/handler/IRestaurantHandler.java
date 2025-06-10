package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.RestaurantListResponse;
import com.plaza.plazoleta.application.dto.RestaurantRequest;
import com.plaza.plazoleta.domain.model.PageResult;

public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequest restaurantRequest);

    PageResult<RestaurantListResponse> getAllRestaurants(Integer elementsByPage);
}