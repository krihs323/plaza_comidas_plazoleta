package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.model.Category;
import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.CategoryEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.MenuEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.RestaurantEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.MenuEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuJpaAdapterTest {


    @Mock
    private IMenuRepository menuRepository;
    @Mock
    private MenuEntityMapper menuEntityMapper;
    @InjectMocks
    private MenuJpaAdapter menuJpaAdapter;

    //Setup
    private Long idRestaurant;
    private int page;
    private int size;
    private String sortBy;
    private Pageable pageable;
    private Sort sort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        idRestaurant = 1L;
        page = 0;
        size = 2;
        sortBy = "id";
        sort = Sort.by(Sort.Direction.ASC , sortBy);
        pageable = PageRequest.of(page, size, sort);
    }

    private static Menu getMenu() {

        Long id = 1L;
        String name = "Arroz con pollo";
        Long price = 2000L;
        String description = "Arroz con pollo con salsa de la casa";
        String urlLogo = "url";
        Category category = new Category(1L, "Comida Tipica");

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

    private static MenuEntity getMenuEntity() {

        Long id = 1L;
        String name = "Arroz con pollo";
        Long price = 2000L;
        String description = "Arroz con pollo con salsa de la casa";
        String urlLogo = "url";
        CategoryEntity category = new CategoryEntity(1L, "Comida Tipica");

        //Objeto restaurante
        Long idRestaurante = 20L;
        String nameRestaurant = "El Forno";
        Long numberId = 31309170L;
        String address = "Avenida 1 calle 2";
        String phoneNumber = "+573158383";
        String urlLogoRestaurant="urlLogoRestaurant";

        Integer userId = 9;
        RestaurantEntity restaurant = new RestaurantEntity(idRestaurante, nameRestaurant, numberId, address, phoneNumber, urlLogoRestaurant, userId);
        //Finaliza objeto restaurante
        Boolean active = true;

        MenuEntity menu = new MenuEntity();
        menu.setId(id);
        menu.setName(name);
        menu.setPrice(price);
        menu.setDescription(description);
        menu.setUrlLogo(urlLogo);
        menu.setCategoryEntity(category);
        menu.setRestaurantEntity(restaurant);
        menu.setActive(active);


        return menu;

    }

    @Test
    void saveMenu() {

        Menu menu = getMenu();
        MenuEntity menuEntity = menuEntityMapper.toEntity(menu);

        Mockito.when(menuEntityMapper.toEntity(any())).thenReturn(menuEntity);
        menuJpaAdapter.saveMenu(menu);

        verify(menuRepository).save(menuEntity);

    }

    @Test
    void updateMenu() {
        Menu menu = getMenu();
        MenuEntity menuEntity = menuEntityMapper.toEntity(menu);

        Mockito.when(menuEntityMapper.toEntity(any())).thenReturn(menuEntity);
        menuJpaAdapter.updateMenu(anyLong(), menu);

        verify(menuRepository).save(menuEntity);
    }

    @Test
    void findById() {
        Menu menuMock = getMenu();
        MenuEntity menuEntity = getMenuEntity();
        Optional<MenuEntity> menuEntityFound = Optional.of(menuEntity);

        Mockito.when(menuRepository.findById(anyLong())).thenReturn(menuEntityFound);
        Mockito.when(menuEntityMapper.toMenu(any())).thenReturn(menuMock);

       Optional<Menu> menuExpected = menuJpaAdapter.findById(anyLong());

        assertEquals(menuExpected.get().getId(), menuMock.getId());

    }

    @Test
    void getMenuByRestaurant(){

        List<MenuEntity> testMenus = new ArrayList<>();
        MenuEntity menuEntityMock = new MenuEntity();
        menuEntityMock.setName("Pasta");
        menuEntityMock.setUrlLogo("http");
        testMenus.add(menuEntityMock);
        testMenus.add(menuEntityMock);

        Menu menu = new Menu();
        menu.setName("Pasta");
        menu.setUrlLogo("http");

        Page<MenuEntity> menusPage = new PageImpl<>(testMenus, pageable, testMenus.size());

        when(menuRepository.findByRestaurantEntityId(idRestaurant, pageable)).thenReturn(menusPage);
        when(menuEntityMapper.toMenu(any())).thenReturn(menu);

        //Page<Menu> menuPageReturn = menuJpaAdapter.getMenuByRestaurant(idRestaurant, null, page, size, sortBy, "");

        //assertEquals(2, menuPageReturn.getContent().size());


    }

    @Test
    void getMenuByRestaurantAndCategory(){

        Long idCategory = 1L;

        List<MenuEntity> testMenus = new ArrayList<>();
        MenuEntity menuEntityMock = new MenuEntity();
        menuEntityMock.setName("Pasta");
        menuEntityMock.setUrlLogo("http");
        testMenus.add(menuEntityMock);
        testMenus.add(menuEntityMock);

        Menu menu = new Menu();
        menu.setName("Pasta");
        menu.setUrlLogo("http");

        Page<MenuEntity> menusPage = new PageImpl<>(testMenus, pageable, testMenus.size());

        when(menuRepository.findByRestaurantEntityIdAndCategoryEntityId(idRestaurant, idCategory, pageable)).thenReturn(menusPage);
        when(menuEntityMapper.toMenu(any())).thenReturn(menu);

        //Page<Menu> menuPageReturn = menuJpaAdapter.getMenuByRestaurant(idRestaurant, idCategory, page, size, sortBy, "");

        //assertEquals(2, menuPageReturn.getContent().size());
    }
}