package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.exception.RestaurantUserCaseValidationException;
import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.domain.model.Role;
import com.plaza.plazoleta.domain.model.User;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.domain.exception.RestaurantValidationException;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;
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

    private User user;

    private Restaurant restaurant;

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
    }


    @Test
    void saveRestaurant() {

        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        when(userPersistencePort.getById(anyLong())).thenReturn(user);

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

        List<Restaurant> testRestaurants = new ArrayList<>();
        Pageable pageable = PageRequest.of(0,2);

        testRestaurants.add(restaurant);
        testRestaurants.add(restaurant);

        //Page<Restaurant> restaurantsPage = new PageImpl<>(testRestaurants, pageable, testRestaurants.size());

        //when(restaurantPersistencePort.getAllRestaurants(2)).thenReturn(restaurantsPage);

        //Page<Restaurant> restaurantsPageReturn = restaurantUserCase.getAllRestaurants(2);

        //assertEquals(2, restaurantsPageReturn.getContent().size());
    }

    //Validaciones
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
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        when(userPersistencePort.getById(anyLong())).thenReturn(user);
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