package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.RestaurantListResponse;
import com.plaza.plazoleta.application.dto.RestaurantRequest;
import com.plaza.plazoleta.application.mapper.RestaurantRequestMapper;
import com.plaza.plazoleta.domain.api.IRestaurantServicePort;
import com.plaza.plazoleta.domain.model.PageResult;
import com.plaza.plazoleta.domain.model.Restaurant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final RestaurantRequestMapper restaurantRequestMapper;

    public RestaurantHandler(IRestaurantServicePort restaurantServicePort, RestaurantRequestMapper restaurantRequestMapper) {
        this.restaurantServicePort = restaurantServicePort;
        this.restaurantRequestMapper = restaurantRequestMapper;
    }

    @Override
    public void saveRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRequest);
        restaurantServicePort.saveRestaurant(restaurant);
    }

    @Override
    public PageResult<RestaurantListResponse> getAllRestaurants(Integer elementsByPage) {
        PageResult<Restaurant> restaurantList = restaurantServicePort.getAllRestaurants(elementsByPage);
        return restaurantRequestMapper.toPageResultResponse(restaurantList);

    }

}
