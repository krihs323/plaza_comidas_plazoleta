package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.RestaurantListResponse;
import com.plaza.plazoleta.application.dto.RestaurantRequest;
import com.plaza.plazoleta.application.mapper.RestaurantRequestMapper;
import com.plaza.plazoleta.application.mapper.RestaurantResponseMapper;
import com.plaza.plazoleta.domain.api.IRestaurantServicePort;
import com.plaza.plazoleta.domain.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final RestaurantResponseMapper restaurantResponseMapper;
    private final RestaurantRequestMapper restaurantRequestMapper;

    public RestaurantHandler(IRestaurantServicePort restaurantServicePort, RestaurantResponseMapper restaurantResponseMapper, RestaurantRequestMapper restaurantRequestMapper) {
        this.restaurantServicePort = restaurantServicePort;
        this.restaurantResponseMapper = restaurantResponseMapper;
        this.restaurantRequestMapper = restaurantRequestMapper;
    }

    @Override
    public void saveRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRequest);
        restaurantServicePort.saveRestaurant(restaurant);
    }

    @Override
    public Page<RestaurantListResponse> getAllRestaurants(Integer pages) {
        Page<Restaurant> restaurantList = restaurantServicePort.getAllRestaurants(pages);
        return restaurantList.map(restaurantRequestMapper::toRestaurantListRespones);
    }

}
