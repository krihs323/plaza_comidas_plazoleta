package com.plaza.plazoleta.domain.api;

import com.plaza.plazoleta.domain.model.PageResult;
import com.plaza.plazoleta.domain.model.Restaurant;

public interface IRestaurantServicePort {

    void saveRestaurant(Restaurant restaurant);

    PageResult<Restaurant> getAllRestaurants(Integer pages);
}
