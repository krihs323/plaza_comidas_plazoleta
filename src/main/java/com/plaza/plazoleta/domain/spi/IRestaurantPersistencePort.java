package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.Restaurant;
import org.springframework.data.domain.Page;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);
    Restaurant getRestaurantById(Long id);

    Page<Restaurant> getAllRestaurants(Integer pages);
}
