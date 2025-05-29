package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.RestaurantListResponse;
import com.plaza.plazoleta.application.dto.RestaurantRequest;
import org.springframework.data.domain.Page;

public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequest restaurantRequest);

    Page<RestaurantListResponse> getAllRestaurants(Integer pages);
}