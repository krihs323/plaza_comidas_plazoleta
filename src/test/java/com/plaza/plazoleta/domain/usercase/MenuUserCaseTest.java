package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.model.Category;
import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.domain.model.User;
import com.plaza.plazoleta.domain.spi.ICategoryPersistencePort;
import com.plaza.plazoleta.domain.spi.IMenuPersistencePort;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.infraestructure.exception.MenuValidationException;
import com.plaza.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;
import com.plaza.plazoleta.infraestructure.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;

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

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private JwtService jwtService;


    @InjectMocks
    private MenuUserCase menuUserCase;

    //Setup
    private Long idRestaurant;
    private int page;
    private int size;
    private String sortBy;
    private Pageable pageable;
    private Sort sort;

    private String token;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        idRestaurant = 1L;
        page = 0;
        size = 2;
        sortBy = "id";
        sort = Sort.by(Sort.Direction.ASC , sortBy);
        pageable = PageRequest.of(page, size, sort);

        token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiT1dORVIiLCJzdWIiOiJvd25lckBob3RtYWlsLmNvbSIsImlhdCI6MTc0ODM5NjI4MCwiZXhwIjoxNzQ4Mzk3NzIwfQ.fqP_Uaelc2vOF448POHVqHb6F3UVzmIImt9ZQEZd1cc";

    }


    private static Menu getMenu() {

        Long id = 1L;
        String name = "Arroz con pollo";
        Long price = 2000L;
        String description = "Arroz con pollo con salsa de la casa";
        String urlLogo = "url";

        Category category = new Category();
        category.setId(1L);
        category.setName("Comida Tipica");


        //Objeto restaurante
        Long idRestaurante = 20L;

        String nameRestaurant = "El Forno";
        Long numberId = 31309170L;
        String address = "Avenida 1 calle 2";
        String phoneNumber = "+573158383";
        String urlLogoRestaurant="urlLogoRestaurant";

        Long userId = 9L;

        Restaurant restaurant = new Restaurant(idRestaurante, nameRestaurant, numberId, address, phoneNumber, urlLogoRestaurant, userId);
        //Finaliza objeto restaurante
        Boolean active = true;

        Menu menu = new Menu();
        menu.setId(id);
        menu.setName(name);
        menu.setPrice(price);
        menu.setDescription(description);
        menu.setUrlLogo(urlLogo);
        menu.setCategory(category);
        menu.setRestaurant(restaurant);
        menu.setActive(active);


        return menu;


    }

    @DisplayName("Save menu should save")
    @Test
    void saveMenu() {

        Menu menu = getMenu();

        User userMock = new User();
        userMock.setIdUser(9L);
        userMock.setRol("OWNER");

        Mockito.when(userPersistencePort.getByEmail(anyString(),anyString())).thenReturn(userMock);

        Mockito.when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(menu.getRestaurant());
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn(token);
        Mockito.when(jwtService.extractUsername(anyString())).thenReturn("owner@hotmail.com");

        doNothing().when(menuPersistencePort).saveMenu(any());

        menuUserCase.saveMenu(menu);

        verify(menuPersistencePort).saveMenu(menu);

    }

    @DisplayName("Udate menu should save")
    @Test
    void updateMenu() {

        Menu menuNew = getMenu();

        Menu menu = new Menu();

        //setear menu para dar covertura al domain
        menu.setId(menuNew.getId());
        menu.setActive(menuNew.getActive());
        menu.setRestaurant(menuNew.getRestaurant());
        menu.setDescription(menuNew.getDescription());
        menu.setCategory(menuNew.getCategory());
        menu.setName(menuNew.getName());
        menu.setPrice(menuNew.getPrice());
        menu.setUrlLogo(menuNew.getUrlLogo());

        User userMock = new User();
        userMock.setIdUser(9L);
        userMock.setRol("OWNER");


        Optional<Menu> menuFound = Optional.of(menu);
        Category categoryNew = new Category(2L, "prueba");

        Mockito.when(userPersistencePort.getByEmail(anyString(),anyString())).thenReturn(userMock);
        Mockito.when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(menu.getRestaurant());
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn(token);
        Mockito.when(jwtService.extractUsername(anyString())).thenReturn("owner@hotmail.com");
        Mockito.when(menuPersistencePort.findById(anyLong())).thenReturn(menuFound);
        Mockito.when(categoryPersistencePort.getCategoryById(anyLong())).thenReturn(categoryNew);

        doNothing().when(menuPersistencePort).updateMenu(anyLong(), any());

        menuUserCase.updateMenu(1L, menu);

        verify(menuPersistencePort).updateMenu(1L, menu);

    }

    @DisplayName("Should not create when user is not the restaurants owner")
    @Test
    void validationWhenIdUserIsNotOwner() {

        Menu menu = getMenu();
        menu.getRestaurant().setUserId(1L);

        User userMock = new User();
        userMock.setIdUser(9L);
        userMock.setRol("OWNER");

        Mockito.when(userPersistencePort.getByEmail(anyString(),anyString())).thenReturn(userMock);
        Mockito.when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(menu.getRestaurant());
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn(token);
        Mockito.when(jwtService.extractUsername(anyString())).thenReturn("owner@hotmail.com");
        doNothing().when(menuPersistencePort).saveMenu(any());

        MenuValidationException exception = assertThrows(MenuValidationException.class, () -> {
            menuUserCase.saveMenu(menu);
        });

        assertEquals(ExceptionResponse.MENU_VALIATION_OWNER.getMessage(), exception.getMessage());

    }




    @DisplayName("Should not create when user is not the restaurants owner")
    @Test
    void validationUpdateWhenIdUserIsNotOwner() {

        Menu menu = getMenu();
        menu.getRestaurant().setUserId(1L);

        Optional<Menu> menuFound = Optional.of(menu);
        Category categoryNew = new Category(2L, "prueba");

        User userMock = new User();
        userMock.setIdUser(9L);
        userMock.setRol("OWNER");


        Mockito.when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(menu.getRestaurant());
        Mockito.when(userPersistencePort.getByEmail(anyString(),anyString())).thenReturn(userMock);
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn(token);
        Mockito.when(jwtService.extractUsername(anyString())).thenReturn("owner@hotmail.com");

        Mockito.when(menuPersistencePort.findById(anyLong())).thenReturn(menuFound);
        Mockito.when(categoryPersistencePort.getCategoryById(anyLong())).thenReturn(categoryNew);

        doNothing().when(menuPersistencePort).updateMenu(anyLong(), any());

        MenuValidationException exception = assertThrows(MenuValidationException.class, () -> {
            menuUserCase.updateMenu(1L, menu);
        });

        assertEquals(ExceptionResponse.MENU_VALIATION_OWNER.getMessage(), exception.getMessage());

    }

    //HU07
    @DisplayName("Should disable a menu")
    @Test
    void disableMenu(){

        Menu menu = getMenu();
        menu.getRestaurant().setUserId(9L);
        Optional<Menu> menuFound = Optional.of(menu);

        User userMock = new User();
        userMock.setIdUser(9L);
        userMock.setRol("OWNER");

        Mockito.when(menuPersistencePort.findById(anyLong())).thenReturn(menuFound);
        Mockito.when(userPersistencePort.getByEmail(anyString(),anyString())).thenReturn(userMock);
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn(token);
        Mockito.when(jwtService.extractUsername(anyString())).thenReturn("owner@hotmail.com");

        doNothing().when(menuPersistencePort).updateMenu(anyLong(), any());

        menuUserCase.disableMenu(1L, menu);

        verify(menuPersistencePort).updateMenu(1L, menu);
    }

    @DisplayName("Should not disabled or enable a menu when user is not the restaurants owner")
    @Test
    void disableMenuValidationException(){

        Menu menu = getMenu();
        menu.getRestaurant().setUserId(22L);
        Optional<Menu> menuFound = Optional.of(menu);

        User userMock = new User();
        userMock.setIdUser(1L);
        userMock.setRol("OWNER");

        Mockito.when(menuPersistencePort.findById(anyLong())).thenReturn(menuFound);
        Mockito.when(userPersistencePort.getByEmail(anyString(),anyString())).thenReturn(userMock);
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn(token);
        Mockito.when(jwtService.extractUsername(anyString())).thenReturn("owner@hotmail.com");

        doNothing().when(menuPersistencePort).updateMenu(anyLong(), any());



        MenuValidationException exception = assertThrows(MenuValidationException.class, () ->
            menuUserCase.disableMenu(1L, menu));

        assertEquals(ExceptionResponse.MENU_VALIATION_OWNER.getMessage(), exception.getMessage());

    }

    @DisplayName("Should return menues by restaurant")
    @Test
    void getMenuByRestaurant(){
        Long idCategory = null;

        List<Menu> testMenus = new ArrayList<>();
        Menu menu = new Menu();

        testMenus.add(menu);
        testMenus.add(menu);

        Page<Menu> menusPage = new PageImpl<>(testMenus, pageable, testMenus.size());
        when(menuPersistencePort.getMenuByRestaurant(idRestaurant, idCategory, page, size, sortBy, "")).thenReturn(menusPage);
        Page<Menu> menuPageReturn = menuUserCase.getMenuByRestaurant(idRestaurant, "", page, size, sortBy, "");
        assertEquals(2, menuPageReturn.getContent().size());
    }

    @DisplayName("Should return menues by restaurant and category")
    @Test
    void getMenuByRestaurantAndCategory(){

        Long idCategory = 1L;

        List<Menu> testMenus = new ArrayList<>();
        Menu menu = new Menu();
        testMenus.add(menu);
        testMenus.add(menu);

        Page<Menu> menusPage = new PageImpl<>(testMenus, pageable, testMenus.size());
        when(menuPersistencePort.getMenuByRestaurant(idRestaurant, idCategory, page, size, sortBy, "")).thenReturn(menusPage);
        Page<Menu> menuPageReturn = menuUserCase.getMenuByRestaurant(idRestaurant, "1", page, size, sortBy, "");
        assertEquals(2, menuPageReturn.getContent().size());

    }

}