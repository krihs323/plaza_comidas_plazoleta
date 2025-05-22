package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.model.Category;
import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.domain.spi.ICategoryPersistencePort;
import com.plaza.plazoleta.domain.spi.IMenuPersistencePort;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.infraestructure.exception.MenuValidationException;
import com.plaza.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class MenuUserCaseTest {

    @Mock
    private IMenuPersistencePort menuPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private MenuUserCase menuUserCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    private static Menu getMenu() {

        Long id = 1L;
        String name = "Arroz con pollo";
        Long price = 2000L;
        String description = "Arroz con pollo con salsa de la casa";
        String urlLogo = "url";
        Category category = new Category(1L, "Comida Tipica");

        //Objeto restaurante
        Long idRestaurante = 1L;
        String nameRestaurant = "El Forno";
        Long numberId = 31309170L;
        String address = "Avenida 1 calle 2";
        String phoneNumber = "+573158383";
        String urlLogoRestaurant="urlLogoRestaurant";

        Long userId = 2L;
        Restaurant restaurant = new Restaurant(idRestaurante, nameRestaurant, numberId, address, phoneNumber, urlLogoRestaurant, userId);
        //Finaliza objeto restaurante
        Boolean active = true;

        return new Menu(id, name, price, description, urlLogo, category, restaurant, active);

    }

    @DisplayName("Save menu should save")
    @Test
    void saveMenu() {

        Menu menu = getMenu();

        Mockito.when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(menu.getRestaurant());
        doNothing().when(menuPersistencePort).saveMenu(any());

        menuUserCase.saveMenu(menu);

        verify(menuPersistencePort).saveMenu(menu);

    }

    @DisplayName("Should not create when user is not the restaurants owner")
    @Test
    void validationWhenIdUserIsNotOwner() {

        Menu menu = getMenu();
        menu.getRestaurant().setUserId(1L);

        Mockito.when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(menu.getRestaurant());
        doNothing().when(menuPersistencePort).saveMenu(any());

        MenuValidationException exception = assertThrows(MenuValidationException.class, () -> {
            menuUserCase.saveMenu(menu);
        });

        assertEquals(ExceptionResponse.MENU_VALIATION.getMessage(), exception.getMessage());

    }


    @DisplayName("Udate menu should save")
    @Test
    void updateMenu() {

        Menu menu = getMenu();

        Optional<Menu> menuFound = Optional.of(menu);
        Category categoryNew = new Category(2L, "prueba");

        Mockito.when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(menu.getRestaurant());
        Mockito.when(menuPersistencePort.findById(anyLong())).thenReturn(menuFound);
        Mockito.when(categoryPersistencePort.getCategoryById(anyLong())).thenReturn(categoryNew);

        doNothing().when(menuPersistencePort).updateMenu(anyLong(), any());

        menuUserCase.updateMenu(1L, menu);

        verify(menuPersistencePort).updateMenu(1L, menu);

    }

    @DisplayName("Should not create when user is not the restaurants owner")
    @Test
    void validationUpdateWhenIdUserIsNotOwner() {

        Menu menu = getMenu();
        menu.getRestaurant().setUserId(1L);

        Optional<Menu> menuFound = Optional.of(menu);
        Category categoryNew = new Category(2L, "prueba");

        Mockito.when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(menu.getRestaurant());
        Mockito.when(menuPersistencePort.findById(anyLong())).thenReturn(menuFound);
        Mockito.when(categoryPersistencePort.getCategoryById(anyLong())).thenReturn(categoryNew);

        doNothing().when(menuPersistencePort).updateMenu(anyLong(), any());

        MenuValidationException exception = assertThrows(MenuValidationException.class, () -> {
            menuUserCase.updateMenu(1L, menu);
        });

        assertEquals(ExceptionResponse.MENU_VALIATION.getMessage(), exception.getMessage());


    }



}