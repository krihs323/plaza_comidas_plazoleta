package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.User;

import java.util.HashMap;

public interface IUserPersistencePort {

    User getById(Long id);

    User getByEmail(String mail, String authorizationHeader);

    User getUseAuth();

    void updateUserRestaurantAsOwner(Long userId, HashMap<String, Long> restaurantEmployee);
}
