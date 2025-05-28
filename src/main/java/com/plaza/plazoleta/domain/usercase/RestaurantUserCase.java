package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.api.IRestaurantServicePort;
import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.domain.model.Role;
import com.plaza.plazoleta.domain.model.User;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.infraestructure.exception.RestaurantValidationException;
import com.plaza.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;
import com.plaza.plazoleta.infraestructure.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;


import java.util.Optional;

public class RestaurantUserCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    private final IUserPersistencePort userPersistencePort;

    private final HttpServletRequest httpServletRequest;

    public RestaurantUserCase(IRestaurantPersistencePort restaurantPersistencePort, IUserPersistencePort userPersistencePort, HttpServletRequest httpServletRequest) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");


        Optional<User> rolByUserId = Optional.of(userPersistencePort.getById(restaurant.getUserId(), authorizationHeader));
        if (!rolByUserId.get().getRol().equals(Role.OWNER.toString())) {
            throw new RestaurantValidationException(ExceptionResponse.MENU_VALIATION.getMessage());

        }
        restaurantPersistencePort.saveRestaurant(restaurant);
    }


}
