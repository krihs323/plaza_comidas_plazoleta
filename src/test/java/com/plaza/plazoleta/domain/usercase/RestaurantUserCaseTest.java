package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.domain.model.Role;
import com.plaza.plazoleta.domain.model.User;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.infraestructure.exception.RestaurantValidationException;
import com.plaza.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantUserCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private RestaurantUserCase restaurantUserCase;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void saveRestaurant() {
        User user = new User();
        user.setRol(Role.OWNER.toString());

        Restaurant restaurant = new Restaurant();
        restaurant.setUserId(2L);

        when(userPersistencePort.getById(anyLong(), anyString())).thenReturn(user);

        when(httpServletRequest.getHeader(anyString())).thenReturn("Bearer");


        restaurantUserCase.saveRestaurant(restaurant);

        verify(restaurantPersistencePort).saveRestaurant(any());

    }

    @DisplayName("Should not create when user is not owner rol")
    @Test
    void validationSaveWhenIdUuerIsNotOwnerRol() {
        User user = new User();
        user.setRol(Role.ADMIN.toString());


        Restaurant restaurant = new Restaurant();
        restaurant.setUserId(2L);

        when(userPersistencePort.getById(anyLong(), anyString())).thenReturn(user);

        when(httpServletRequest.getHeader(anyString())).thenReturn("Bearer");

        RestaurantValidationException exception = assertThrows(RestaurantValidationException.class, () ->
                restaurantUserCase.saveRestaurant(restaurant));
        assertEquals(ExceptionResponse.MENU_VALIATION.getMessage(), exception.getMessage());


    }

    //HU 10
    @DisplayName("Should return all restaurants")
    @Test
    void getAllRestaurants() {

        List<Restaurant> testRestaurants = new ArrayList<>();
        Pageable pageable = PageRequest.of(0,2);

        Restaurant restaurant = new Restaurant();
        testRestaurants.add(restaurant);
        testRestaurants.add(restaurant);

        Page<Restaurant> restaurantsPage = new PageImpl<>(testRestaurants, pageable, testRestaurants.size());

        when(restaurantPersistencePort.getAllRestaurants(2)).thenReturn(restaurantsPage);

        Page<Restaurant> restaurantsPageReturn = restaurantUserCase.getAllRestaurants(2);

        assertEquals(2, restaurantsPageReturn.getContent().size());
    }
}