package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.exception.RestaurantUserCaseValidationException;
import com.plaza.plazoleta.domain.model.*;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.domain.exception.RestaurantValidationException;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RestaurantUserCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private RestaurantUserCase restaurantUserCase;

    private User user;

    private Restaurant restaurant;

    private PageResult<Restaurant> pageResultRestaurant;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User(Role.OWNER.name(), 9L, null);

        restaurant = new Restaurant();
        restaurant.setName("Restaurante");
        restaurant.setNumberId(1143826302L);
        restaurant.setUserId(2L);
        restaurant.setAddress("Avenida siempre viva");
        restaurant.setPhoneNumber("+573155763456");
        restaurant.setUrlLogo("hhpt:");

        List<Restaurant> testRestaurants = new ArrayList<>();
        testRestaurants.add(restaurant);
        testRestaurants.add(restaurant);

        pageResultRestaurant = new PageResult<>();
        pageResultRestaurant.setContent(testRestaurants);
        pageResultRestaurant.setPageNumber(1);
        pageResultRestaurant.setPageSize(1);
        pageResultRestaurant.setTotalPages(1);
        pageResultRestaurant.setLast(true);
        pageResultRestaurant.setTotalElements(1);
    }


    @Test
    void saveRestaurant() {

        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        when(userPersistencePort.getById(anyLong())).thenReturn(user);
        when(restaurantPersistencePort.saveRestaurant(any())).thenReturn(restaurant);
        doNothing().when(userPersistencePort).updateUserRestaurantAsOwner(anyLong(), any());

        restaurantUserCase.saveRestaurant(restaurant);

        verify(restaurantPersistencePort).saveRestaurant(any());

    }

    @DisplayName("Should not create when user is not owner rol")
    @Test
    void validationSaveWhenIdUuerIsNotOwnerRol() {

        user.setRol(Role.ADMIN.toString());

        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        when(userPersistencePort.getById(anyLong())).thenReturn(user);

        RestaurantValidationException exception = assertThrows(RestaurantValidationException.class, () ->
                restaurantUserCase.saveRestaurant(restaurant));
        assertEquals(ExceptionResponse.MENU_VALIATION.getMessage(), exception.getMessage());

    }

    @DisplayName("Should return all restaurants")
    @Test
    void getAllRestaurants() {

        when(restaurantPersistencePort.getAllRestaurants(2)).thenReturn(pageResultRestaurant);
        PageResult<Restaurant> restaurantsPageReturn = restaurantUserCase.getAllRestaurants(2);
        assertEquals(2, restaurantsPageReturn.getContent().size());
    }

    @Test
    void saveRestaurantWhenNameIsNotValid() {

        restaurant.setName("");

        RestaurantUserCaseValidationException exception = assertThrows(RestaurantUserCaseValidationException.class, () ->
                restaurantUserCase.saveRestaurant(restaurant));
        assertEquals(ExceptionResponse.RESTAURANT_VALIATION_NAME.getMessage(), exception.getMessage());

    }

    @Test
    void saveRestaurantWhenNameContaisOnlyNumber() {
        restaurant.setName("123");
        RestaurantUserCaseValidationException exception = assertThrows(RestaurantUserCaseValidationException.class, () ->
                restaurantUserCase.saveRestaurant(restaurant));
        assertEquals(ExceptionResponse.RESTAURANT_VALIATION_NAME.getMessage(), exception.getMessage());
    }

    @Test
    void saveRestaurantWhenNameContainsNumberAndLetters() {
        restaurant.setName("Restaurant 123");
        when(userPersistencePort.getUseAuth()).thenReturn(user);
        when(userPersistencePort.getById(anyLong())).thenReturn(user);
        when(restaurantPersistencePort.saveRestaurant(any())).thenReturn(restaurant);
        doNothing().when(userPersistencePort).updateUserRestaurantAsOwner(anyLong(), any());

        restaurantUserCase.saveRestaurant(restaurant);
        verify(restaurantPersistencePort).saveRestaurant(any());
    }

    @Test
    void DontSaveWhenRestaurantWhenNumberIdIsNotValid() {

        restaurant.setNumberId(null);

        RestaurantUserCaseValidationException exception = assertThrows(RestaurantUserCaseValidationException.class, () ->
                restaurantUserCase.saveRestaurant(restaurant));
        assertEquals(ExceptionResponse.VALIDATION_NUMBER_ID.getMessage(), exception.getMessage());

    }

    @Test
    void DontSaveWhenRestaurantWhenAdressIsNotValid() {

        restaurant.setAddress("");

        RestaurantUserCaseValidationException exception = assertThrows(RestaurantUserCaseValidationException.class, () ->
                restaurantUserCase.saveRestaurant(restaurant));
        assertEquals(ExceptionResponse.VALIDATION_ADRESS.getMessage(), exception.getMessage());

    }

    @Test
    void DontSaveWhenRestaurantWhenPhoneNumberIsNotValid() {
        restaurant.setPhoneNumber("+123456789012345");
        RestaurantUserCaseValidationException exception = assertThrows(RestaurantUserCaseValidationException.class, () ->
                restaurantUserCase.saveRestaurant(restaurant));
        assertEquals(ExceptionResponse.VALIDATION_PHONE_NUMBER.getMessage(), exception.getMessage());
    }

    @Test
    void DontSaveWhenRestaurantWhenPhoneNumberIsNull() {
        restaurant.setPhoneNumber("");
        RestaurantUserCaseValidationException exception = assertThrows(RestaurantUserCaseValidationException.class, () ->
                restaurantUserCase.saveRestaurant(restaurant));
        assertEquals(ExceptionResponse.VALIDATION_PHONE_NUMBER.getMessage(), exception.getMessage());
    }

    @Test
    void DontSaveWhenRestaurantWhenLogoIsNull() {
        restaurant.setUrlLogo("");
        RestaurantUserCaseValidationException exception = assertThrows(RestaurantUserCaseValidationException.class, () ->
                restaurantUserCase.saveRestaurant(restaurant));
        assertEquals(ExceptionResponse.VALIDATION_LOGO.getMessage(), exception.getMessage());
    }

    @Test
    void DontSaveWhenRestaurantWhenUserIdIsNull() {
        restaurant.setUserId(null);
        RestaurantUserCaseValidationException exception = assertThrows(RestaurantUserCaseValidationException.class, () ->
                restaurantUserCase.saveRestaurant(restaurant));
        assertEquals(ExceptionResponse.VALIDATION_USER_ID.getMessage(), exception.getMessage());
    }

}