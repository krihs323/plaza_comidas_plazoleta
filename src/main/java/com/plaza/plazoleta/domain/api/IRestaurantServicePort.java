package com.plaza.plazoleta.domain.api;

import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.infraestructure.exception.RestaurantValidationException;

public interface IRestaurantServicePort {

    void saveRestaurant(Restaurant restaurant);
}
