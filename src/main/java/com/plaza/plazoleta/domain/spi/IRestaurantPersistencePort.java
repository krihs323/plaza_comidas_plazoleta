package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.PageResult;
import com.plaza.plazoleta.domain.model.Restaurant;

import java.util.Optional;

public interface IRestaurantPersistencePort {

    Restaurant saveRestaurant(Restaurant restaurant);
    Restaurant getRestaurantById(Long id);

    PageResult<Restaurant> getAllRestaurants(Integer pages);

    Optional<Restaurant> getRestaurantByUserId(Long idUser);
}
