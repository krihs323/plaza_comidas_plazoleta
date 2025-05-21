package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.RestaurantRequest;

public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequest restaurantRequest);
}