package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.exception.MenuUserCaseValidationException;
import com.plaza.plazoleta.domain.model.*;
import com.plaza.plazoleta.domain.spi.ICategoryPersistencePort;
import com.plaza.plazoleta.domain.spi.IMenuPersistencePort;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.domain.exception.MenuValidationException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

    private Long idRestaurant;
    private int page;
    private int size;
    private String sortBy;
    private Menu menu;
    private User user;
    private PageResult<Menu> pageResultMenu;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        idRestaurant = 1L;
        page = 0;
        size = 2;
        sortBy = "id";

        user = new User(Role.OWNER.name(), 9L, null);

        Long id = 1L;
        String name = "Arroz con pollo";
        Long price = 2000L;
        String description = "Arroz con pollo con salsa de la casa";
        String urlLogo = "url";

        Category category = new Category();
        category.setId(1L);
        category.setName("Comida Tipica");


        Long idRestaurante = 20L;

        String nameRestaurant = "El Forno";
        Long numberId = 31309170L;
        String address = "Avenida 1 calle 2";
        String phoneNumber = "+573158383";
        String urlLogoRestaurant="urlLogoRestaurant";

        Long userId = 9L;

        Restaurant restaurant = new Restaurant(idRestaurante, nameRestaurant, numberId, address, phoneNumber, urlLogoRestaurant, userId);

        Boolean active = true;
        menu = new Menu();
        menu.setId(id);
        menu.setName(name);
        menu.setPrice(price);
        menu.setDescription(description);
        menu.setUrlLogo(urlLogo);
        menu.setCategory(category);
        menu.setRestaurant(restaurant);
        menu.setActive(active);

        List<Menu> testMenus = new ArrayList<>();
        testMenus.add(menu);
        testMenus.add(menu);

        pageResultMenu = new PageResult<>();
        pageResultMenu.setContent(testMenus);
        pageResultMenu.setPageNumber(1);
        pageResultMenu.setPageSize(1);
        pageResultMenu.setTotalPages(1);
        pageResultMenu.setLast(true);
        pageResultMenu.setTotalElements(1);

    }


    @DisplayName("Save menu should save")
    @Test
    void saveMenu() {
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        Mockito.when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(menu.getRestaurant());
        doNothing().when(menuPersistencePort).saveMenu(any());
        menuUserCase.saveMenu(menu);
        verify(menuPersistencePort).saveMenu(menu);
    }

    @DisplayName("Udate menu should save")
    @Test
    void updateMenu() {

        Menu menuNew = menu;


        menu.setId(menuNew.getId());
        menu.setActive(menuNew.getActive());
        menu.setRestaurant(menuNew.getRestaurant());
        menu.setDescription(menuNew.getDescription());
        menu.setCategory(menuNew.getCategory());
        menu.setName(menuNew.getName());
        menu.setPrice(menuNew.getPrice());
        menu.setUrlLogo(menuNew.getUrlLogo());

        Optional<Menu> menuFound = Optional.of(menu);
        Category categoryNew = new Category(2L, "prueba");

        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        Mockito.when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(menu.getRestaurant());
        Mockito.when(menuPersistencePort.findById(anyLong())).thenReturn(menuFound);
        Mockito.when(categoryPersistencePort.getCategoryById(anyLong())).thenReturn(categoryNew);

        doNothing().when(menuPersistencePort).saveMenu(any());

        menuUserCase.updateMenu(1L, menu);

        verify(menuPersistencePort).saveMenu(menu);

    }

    @DisplayName("Should not create when user is not the restaurants owner")
    @Test
    void validationWhenIdUserIsNotOwner() {

        menu.getRestaurant().setUserId(1L);
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        Mockito.when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(menu.getRestaurant());
        doNothing().when(menuPersistencePort).saveMenu(any());
        MenuValidationException exception = assertThrows(MenuValidationException.class, () -> {
            menuUserCase.saveMenu(menu);
        });

        assertEquals(ExceptionResponse.MENU_VALIATION_OWNER.getMessage(), exception.getMessage());

    }




    @DisplayName("Should not create when user is not the restaurants owner")
    @Test
    void validationUpdateWhenIdUserIsNotOwner() {

        menu.getRestaurant().setUserId(1L);

        Optional<Menu> menuFound = Optional.of(menu);
        Category categoryNew = new Category(2L, "prueba");

        Mockito.when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(menu.getRestaurant());
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);

        Mockito.when(menuPersistencePort.findById(anyLong())).thenReturn(menuFound);
        Mockito.when(categoryPersistencePort.getCategoryById(anyLong())).thenReturn(categoryNew);

        doNothing().when(menuPersistencePort).saveMenu(any());

        MenuValidationException exception = assertThrows(MenuValidationException.class, () -> {
            menuUserCase.updateMenu(1L, menu);
        });

        assertEquals(ExceptionResponse.MENU_VALIATION_OWNER.getMessage(), exception.getMessage());

    }

    @DisplayName("Should disable a menu")
    @Test
    void disableMenu(){

        menu.getRestaurant().setUserId(9L);
        Optional<Menu> menuFound = Optional.of(menu);

        Mockito.when(menuPersistencePort.findById(anyLong())).thenReturn(menuFound);
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);

        doNothing().when(menuPersistencePort).saveMenu(any());

        menuUserCase.disableMenu(1L, menu);

        verify(menuPersistencePort).saveMenu(menu);
    }

    @DisplayName("Should not disabled or enable a menu when user is not the restaurants owner")
    @Test
    void disableMenuValidationException(){

        menu.getRestaurant().setUserId(22L);
        Optional<Menu> menuFound = Optional.of(menu);

        user.setIdUser(1L);
        user.setRol("OWNER");

        Mockito.when(menuPersistencePort.findById(anyLong())).thenReturn(menuFound);
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);

        doNothing().when(menuPersistencePort).saveMenu(any());

        MenuValidationException exception = assertThrows(MenuValidationException.class, () ->
            menuUserCase.disableMenu(1L, menu));

        assertEquals(ExceptionResponse.MENU_VALIATION_OWNER.getMessage(), exception.getMessage());

    }

    @DisplayName("Should return menues by restaurant")
    @Test
    void getMenuByRestaurant(){
        Long idCategory = null;
        when(menuPersistencePort.getMenuByRestaurant(idRestaurant, idCategory, page, size, sortBy, "")).thenReturn(pageResultMenu);
        PageResult<Menu> menuPageReturn = menuUserCase.getMenuByRestaurant(idRestaurant, "", page, size, sortBy, "");
        assertEquals(2, menuPageReturn.getContent().size());
    }

    @DisplayName("Should return menues by restaurant and category")
    @Test
    void getMenuByRestaurantWhenCategoryIsNoNull(){
        Long idCategory = 1L;
        when(menuPersistencePort.getMenuByRestaurant(idRestaurant, idCategory, page, size, sortBy, "")).thenReturn(pageResultMenu);
        PageResult<Menu> menuPageReturn = menuUserCase.getMenuByRestaurant(idRestaurant, "1", page, size, sortBy, "");
        assertEquals(2, menuPageReturn.getContent().size());
    }

    @DisplayName("Save menu should not save if name is invalid")
    @Test
    void saveMenuWhenNameIsInvalid() {
        menu.setName("");
        MenuUserCaseValidationException exception = assertThrows(MenuUserCaseValidationException.class, () -> {
            menuUserCase.saveMenu(menu);
        });
        assertEquals(ExceptionResponse.MENU_VALIATION_NAME.getMessage(), exception.getMessage());
    }

    @DisplayName("Save menu should not save if price is invalid")
    @Test
    void saveMenuWhenPriceIsInvalid() {
        menu.setPrice(null);
        MenuUserCaseValidationException exception = assertThrows(MenuUserCaseValidationException.class, () -> {
            menuUserCase.saveMenu(menu);
        });
        assertEquals(ExceptionResponse.VALIDATION_PRICE.getMessage(), exception.getMessage());
    }

    @DisplayName("Save menu should not save if price is greater than 0")
    @Test
    void saveMenuWhenPriceIsNotPositive() {
        menu.setPrice(-1L);
        MenuUserCaseValidationException exception = assertThrows(MenuUserCaseValidationException.class, () -> {
            menuUserCase.saveMenu(menu);
        });
        assertEquals(ExceptionResponse.VALIDATION_PRICE.getMessage(), exception.getMessage());
    }

    @DisplayName("Save menu should not save if logo is null")
    @Test
    void saveMenuWhenLogoIsNotValid() {
        menu.setUrlLogo("");
        MenuUserCaseValidationException exception = assertThrows(MenuUserCaseValidationException.class, () -> {
            menuUserCase.saveMenu(menu);
        });
        assertEquals(ExceptionResponse.VALIDATION_LOGO.getMessage(), exception.getMessage());
    }

    @DisplayName("Save menu should not save if logo is null")
    @Test
    void saveMenuWhenDescriptionIsNotValid() {
        menu.setDescription("");
        MenuUserCaseValidationException exception = assertThrows(MenuUserCaseValidationException.class, () -> {
            menuUserCase.saveMenu(menu);
        });
        assertEquals(ExceptionResponse.VALIDATION_DESCRIPTION.getMessage(), exception.getMessage());
    }

    @DisplayName("Save menu should not save if logo is null")
    @Test
    void saveMenuWhenDescriptionIsNotAStringValid() {
        menu.setDescription("#$%$ASD@!231");
        MenuUserCaseValidationException exception = assertThrows(MenuUserCaseValidationException.class, () -> {
            menuUserCase.saveMenu(menu);
        });
        assertEquals(ExceptionResponse.VALIDATION_DESCRIPTION.getMessage(), exception.getMessage());
    }

    @DisplayName("Save menu should not save if category is null")
    @Test
    void saveMenuWhenCategoryIsNull() {
        menu.setCategory(null);
        MenuUserCaseValidationException exception = assertThrows(MenuUserCaseValidationException.class, () -> {
            menuUserCase.saveMenu(menu);
        });
        assertEquals(ExceptionResponse.VALIDATION_CATEGORY.getMessage(), exception.getMessage());
    }

    @DisplayName("Save menu should not save if restaurant is null")
    @Test
    void saveMenuWhenRestaurantIsNull() {
        menu.setRestaurant(null);
        MenuUserCaseValidationException exception = assertThrows(MenuUserCaseValidationException.class, () -> {
            menuUserCase.saveMenu(menu);
        });
        assertEquals(ExceptionResponse.VALIDATION_RESTAURANT.getMessage(), exception.getMessage());
    }

    @DisplayName("Update menu should not save if restaurant is null")
    @Test
    void updateMenuWhenPriceIsNull() {
        menu.setPrice(null);
        MenuUserCaseValidationException exception = assertThrows(MenuUserCaseValidationException.class, () -> {
            menuUserCase.updateMenu(1L,menu);
        });
        assertEquals(ExceptionResponse.VALIDATION_PRICE.getMessage(), exception.getMessage());
    }

    @DisplayName("Update menu should not save if price is not a positive number")
    @Test
    void updateMenuWhenPriceIsNotAPositiveNumber() {
        menu.setPrice(-1L);
        MenuUserCaseValidationException exception = assertThrows(MenuUserCaseValidationException.class, () -> {
            menuUserCase.updateMenu(1L,menu);
        });
        assertEquals(ExceptionResponse.VALIDATION_PRICE.getMessage(), exception.getMessage());
    }

    @DisplayName("Update menu should not save if description is null")
    @Test
    void updateMenuWhenDescriptionIsNull() {
        menu.setDescription("");
        MenuUserCaseValidationException exception = assertThrows(MenuUserCaseValidationException.class, () -> {
            menuUserCase.updateMenu(1L,menu);
        });
        assertEquals(ExceptionResponse.VALIDATION_DESCRIPTION.getMessage(), exception.getMessage());
    }
}