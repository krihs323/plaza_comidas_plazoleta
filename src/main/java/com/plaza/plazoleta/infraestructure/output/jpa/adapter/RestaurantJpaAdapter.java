package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.infraestructure.exception.RestaurantAlreadyExistException;
import com.plaza.plazoleta.infraestructure.exception.RestaurantValidationException;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.RestaurantEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.RestaurantEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IRestaurantRepository;

import java.util.Optional;

//@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    public RestaurantJpaAdapter(IRestaurantRepository restaurantRepository, RestaurantEntityMapper restaurantEntityMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantEntityMapper = restaurantEntityMapper;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
    }

    @Override
    public Restaurant getRestaurantById(Long id) {

        //Optional<RestaurantEntity> = restaurantRepository.findBy(id);
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
        if (restaurantEntity.isPresent()) {
            return restaurantEntityMapper.toRestaurant(restaurantEntity.orElseThrow());
        }
        throw new RestaurantValidationException("No existe el restaurante");

    }


}
