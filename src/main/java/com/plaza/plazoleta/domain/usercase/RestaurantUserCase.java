package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.api.IRestaurantServicePort;
import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.domain.model.User;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.infraestructure.exception.RestaurantValidationException;
import com.plaza.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;

import java.util.Optional;

public class RestaurantUserCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    private final IUserPersistencePort userPersistencePort;

    public RestaurantUserCase(IRestaurantPersistencePort restaurantPersistencePort, IUserPersistencePort userPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {

        Optional<User> rolByUserId = Optional.of(userPersistencePort.getById(restaurant.getUserId()));
        if (rolByUserId.get().getRol()!=2) {
            throw new RestaurantValidationException(ExceptionResponse.RESTAURANT_VALIDATION_NOT_ROL_VALID.getMessage());
        }
        restaurantPersistencePort.saveRestaurant(restaurant);
    }


}
