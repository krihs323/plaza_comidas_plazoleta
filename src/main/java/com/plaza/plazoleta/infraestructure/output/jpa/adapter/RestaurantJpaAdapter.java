package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.model.PageResult;
import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.exception.RestaurantValidationException;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.RestaurantEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.RestaurantEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IRestaurantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    public RestaurantJpaAdapter(IRestaurantRepository restaurantRepository, RestaurantEntityMapper restaurantEntityMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantEntityMapper = restaurantEntityMapper;
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));

        return restaurantEntityMapper.toRestaurant(restaurantEntity);
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
        if (restaurantEntity.isPresent()) {
            return restaurantEntityMapper.toRestaurant(restaurantEntity.orElseThrow());
        }
        throw new RestaurantValidationException(ExceptionResponse.RESTAURANT_VALIDATION_NOT_FOUND.getMessage());

    }

    @Override
    public PageResult<Restaurant> getAllRestaurants(Integer elementsByPage) {

        Sort sort = Sort.by(Sort.Direction.ASC , "name");
        Pageable pageable = PageRequest.of(0, elementsByPage, sort);
        Page<RestaurantEntity> restaurantRepositoryAll = restaurantRepository.findAll(pageable);

        List<Restaurant> domainRestaurants = restaurantRepositoryAll
                .getContent()
                .stream()
                .map(restaurantEntityMapper::toRestaurant)
                .toList();

        return new PageResult<>(
                domainRestaurants,
                restaurantRepositoryAll.getNumber(),
                restaurantRepositoryAll.getSize(),
                restaurantRepositoryAll.getTotalElements(),
                restaurantRepositoryAll.getTotalPages(),
                restaurantRepositoryAll.isLast()
        );


    }

    @Override
    public Optional<Restaurant> getRestaurantByUserId(Long idUser) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findByUserId(idUser);
        return restaurantEntity.map(restaurantEntityMapper::toRestaurant);
    }


}
