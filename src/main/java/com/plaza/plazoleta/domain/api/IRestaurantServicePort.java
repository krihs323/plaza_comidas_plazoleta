package com.plaza.plazoleta.domain.api;

import com.plaza.plazoleta.domain.model.Restaurant;
import org.springframework.data.domain.Page;

public interface IRestaurantServicePort {

    void saveRestaurant(Restaurant restaurant);

    Page<Restaurant> getAllRestaurants(Integer pages);
}
