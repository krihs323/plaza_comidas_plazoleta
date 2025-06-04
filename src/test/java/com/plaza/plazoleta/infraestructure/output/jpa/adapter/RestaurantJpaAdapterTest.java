package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveRestaurant() {
        RestaurantEntity restaurantMock = new RestaurantEntity();
        restaurantMock.setId(1L);
        restaurantMock.setName("Il Forno");
        Mockito.when(restaurantEntityMapper.toEntity(any())).thenReturn(restaurantMock);

        restaurantJpaAdapter.saveRestaurant(any());

        verify(restaurantRepository).save(restaurantMock);

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

        RestaurantEntity restaurantEntityMock = new RestaurantEntity();
        restaurantEntityMock.setId(1L);
        restaurantEntityMock.setName("Il Forno");
        restaurantEntityMock.setUserId(1);
        restaurantEntityMock.setAddress("av 1 2 10");
        restaurantEntityMock.setUrlLogo("url");
        restaurantEntityMock.setNumberId(12345L);
        restaurantEntityMock.setPhoneNumber("+789787688");

        Mockito.when(restaurantEntityMapper.toRestaurant(any())).thenReturn(restaurantMock);
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantEntityMock));

        Restaurant restaurantExpected = restaurantJpaAdapter.getRestaurantById(anyLong());

        assertEquals(restaurantExpected.getId(), restaurantEntityMock.getId());

    }

    @Test
    void getRestaurantByIdNotFoundException() {

        Restaurant restaurantMock = new Restaurant();
        restaurantMock.setId(1L);
        restaurantMock.setName("Il Forno");

        RestaurantEntity restaurantEntityMock = new RestaurantEntity();
        restaurantEntityMock.setId(1L);
        restaurantEntityMock.setName("Il Forno");

        Mockito.when(restaurantEntityMapper.toRestaurant(any())).thenReturn(restaurantMock);
        Mockito.when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());


        RestaurantValidationException exception = assertThrows(RestaurantValidationException.class, () -> {
            Restaurant restaurantExpected = restaurantExpected = restaurantJpaAdapter.getRestaurantById(anyLong());
        });

        assertEquals(ExceptionResponse.RESTAURANT_VALIDATION_NOT_FOUND.getMessage(), exception.getMessage());

    }


    @DisplayName("Should return all restaurants")
    @Test
    void getAllRestaurants() {

        List<RestaurantEntity> testRestaurants = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.ASC , "name");
        Pageable pageable = PageRequest.of(0,2, sort);

        RestaurantEntity restaurantEntityMock = new RestaurantEntity();
        restaurantEntityMock.setName("Il Forno");
        restaurantEntityMock.setUrlLogo("http");
        testRestaurants.add(restaurantEntityMock);
        testRestaurants.add(restaurantEntityMock);

        Page<RestaurantEntity> restaurantsPage = new PageImpl<>(testRestaurants, pageable, testRestaurants.size());

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Il Forno");
        restaurant.setUrlLogo("http");

        when(restaurantRepository.findAll(pageable)).thenReturn(restaurantsPage);
        when(restaurantEntityMapper.toRestaurant(any())).thenReturn(restaurant);

        //Page<Restaurant> restaurantsPageReturn = restaurantJpaAdapter.getAllRestaurants(2);

        //assertEquals(2, restaurantsPageReturn.getContent().size());
    }
}