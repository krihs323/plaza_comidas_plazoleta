package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.api.IRestaurantServicePort;
import com.plaza.plazoleta.domain.model.PageResult;
import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.domain.model.Role;
import com.plaza.plazoleta.domain.model.User;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.domain.validation.RestaurantValidations;
import com.plaza.plazoleta.domain.exception.RestaurantValidationException;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;

import java.util.HashMap;

public class RestaurantUserCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    private final IUserPersistencePort userPersistencePort;


    public RestaurantUserCase(IRestaurantPersistencePort restaurantPersistencePort, IUserPersistencePort userPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        RestaurantValidations.saveRestaurant(restaurant);
        User userAuth = userPersistencePort.getById(restaurant.getUserId());
        if (!userAuth.getRol().equals(Role.OWNER.toString())) {
            throw new RestaurantValidationException(ExceptionResponse.MENU_VALIATION.getMessage());
        }
        Restaurant restaurantSaved = restaurantPersistencePort.saveRestaurant(restaurant);
        HashMap<String, Long> restaurantEmployee = new HashMap<>();
        restaurantEmployee.put("idRestaurantEmployee", restaurantSaved.getId());
        userPersistencePort.updateUserRestaurantAsOwner(restaurant.getUserId(), restaurantEmployee);
    }

    @Override
    public PageResult<Restaurant> getAllRestaurants(Integer pages) {
        return restaurantPersistencePort.getAllRestaurants(pages);
    }

}
