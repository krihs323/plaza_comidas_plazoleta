package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.domain.model.User;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.infraestructure.exception.RestaurantValidationException;
import com.plaza.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

class RestaurantUserCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private RestaurantUserCase restaurantUserCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void saveRestaurant() {
        User user = new User();
        user.setRol(2);

        Restaurant restaurant = new Restaurant();
        restaurant.setUserId(2L);

        Mockito.when(userPersistencePort.getById(anyLong())).thenReturn(user);

        restaurantUserCase.saveRestaurant(restaurant);

        verify(restaurantPersistencePort).saveRestaurant(any());

    }

    @DisplayName("Should not create when user is not owner rol")
    @Test
    void validationSaveWhenIdUuerIsNotOwnerRol() {
        User user = new User();
        user.setRol(1);

        Restaurant restaurant = new Restaurant();
        restaurant.setUserId(2L);

        Mockito.when(userPersistencePort.getById(anyLong())).thenReturn(user);


        RestaurantValidationException exception = assertThrows(RestaurantValidationException.class, () ->
                restaurantUserCase.saveRestaurant(restaurant));
        assertEquals(ExceptionResponse.RESTAURANT_VALIDATION_NOT_ROL_VALID.getMessage(), exception.getMessage());

    }
}