package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.Restaurant;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);
}
