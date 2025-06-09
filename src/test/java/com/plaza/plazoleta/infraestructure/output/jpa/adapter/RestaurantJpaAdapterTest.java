package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.model.PageResult;
import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.domain.exception.RestaurantValidationException;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.RestaurantEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.RestaurantEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestaurantJpaAdapterTest {

    @Mock
    private IRestaurantRepository restaurantRepository;
    @Mock
    private RestaurantEntityMapper restaurantEntityMapper;

    @InjectMocks
    private RestaurantJpaAdapter restaurantJpaAdapter;

    private RestaurantEntity restaurantEntity;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Il Forno");
        restaurantEntity.setUserId(1);
        restaurantEntity.setAddress("av 1 2 10");
        restaurantEntity.setUrlLogo("url");
        restaurantEntity.setNumberId(12345L);
        restaurantEntity.setPhoneNumber("+789787688");

        restaurant = new Restaurant();
        restaurant.setName("Il Forno");
        restaurant.setUrlLogo("http");

    }

    @Test
    void saveRestaurant() {


        Mockito.when(restaurantEntityMapper.toEntity(any())).thenReturn(restaurantEntity);

        restaurantJpaAdapter.saveRestaurant(any());

        verify(restaurantRepository).save(restaurantEntity);

    }

    @Test
    void getRestaurantById() {

        Restaurant restaurantMock = new Restaurant();
        restaurantMock.setId(1L);
        restaurantMock.setName("Il Forno");
        restaurantMock.setUserId(1L);
        restaurantMock.setAddress("av 1 2 10");
        restaurantMock.setUrlLogo("url");
        restaurantMock.setNumberId(12345L);
        restaurantMock.setPhoneNumber("+789787688");



        Mockito.when(restaurantEntityMapper.toRestaurant(any())).thenReturn(restaurantMock);
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantEntity));

        Restaurant restaurantExpected = restaurantJpaAdapter.getRestaurantById(anyLong());

        assertEquals(restaurantExpected.getId(), restaurantEntity.getId());

    }

    @Test
    void getRestaurantByIdNotFoundException() {

        Mockito.when(restaurantEntityMapper.toRestaurant(any())).thenReturn(restaurant);
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());


        RestaurantValidationException exception = assertThrows(RestaurantValidationException.class, () ->
            restaurantJpaAdapter.getRestaurantById(anyLong())
        );

        assertEquals(ExceptionResponse.RESTAURANT_VALIDATION_NOT_FOUND.getMessage(), exception.getMessage());

    }


    @DisplayName("Should return all restaurants")
    @Test
    void getAllRestaurants() {

        List<RestaurantEntity> testRestaurants = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.ASC , "name");
        Pageable pageable = PageRequest.of(0,2, sort);


        testRestaurants.add(restaurantEntity);
        testRestaurants.add(restaurantEntity);

        Page<RestaurantEntity> restaurantsPage = new PageImpl<>(testRestaurants, pageable, testRestaurants.size());


        when(restaurantRepository.findAll(pageable)).thenReturn(restaurantsPage);
        when(restaurantEntityMapper.toRestaurant(any())).thenReturn(restaurant);

        PageResult<Restaurant> restaurantsPageReturn = restaurantJpaAdapter.getAllRestaurants(2);

        assertEquals(2, restaurantsPageReturn.getContent().size());
    }

    @DisplayName("Should return restaurant by owner")
    @Test
    void getRestaurantByUserId() {

        Mockito.when(restaurantRepository.findByUserId(anyLong())).thenReturn(Optional.of(restaurantEntity));
        Mockito.when(restaurantEntityMapper.toRestaurant(any())).thenReturn(restaurant);

        Optional<Restaurant> restaurantResponse = restaurantJpaAdapter.getRestaurantByUserId(anyLong());
        assertEquals(restaurant.getId(), restaurantResponse.get().getId());
    }

}