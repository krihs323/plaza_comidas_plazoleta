package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.model.Category;
import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.PageResult;
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

    private Long idRestaurant;
    private int page;
    private int size;
    private String sortBy;
    private Pageable pageable;

    private Menu menu;
    private MenuEntity menuEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        idRestaurant = 1L;
        page = 0;
        size = 2;
        sortBy = "id";
        Sort sort = Sort.by(Sort.Direction.ASC , sortBy);
        pageable = PageRequest.of(page, size, sort);

        Long id = 1L;
        String name = "Arroz con pollo";
        Long price = 2000L;
        String description = "Arroz con pollo con salsa de la casa";
        String urlLogo = "url";
        Category category = new Category(1L, "Comida Tipica");

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

        CategoryEntity categoryEntity = new CategoryEntity(1L, "Comida Tipica");
        RestaurantEntity restaurantEntity = new RestaurantEntity(idRestaurante, nameRestaurant, numberId, address, phoneNumber, urlLogoRestaurant, 9);

        menuEntity = new MenuEntity();
        menuEntity.setId(id);
        menuEntity.setName(name);
        menuEntity.setPrice(price);
        menuEntity.setDescription(description);
        menuEntity.setUrlLogo(urlLogo);
        menuEntity.setCategoryEntity(categoryEntity);
        menuEntity.setRestaurantEntity(restaurantEntity);
        menuEntity.setActive(active);
    }



    @Test
    void saveMenu() {

        Mockito.when(menuEntityMapper.toEntity(any())).thenReturn(menuEntity);
        menuJpaAdapter.saveMenu(menu);

        verify(menuRepository).save(menuEntity);

    }


    @Test
    void findById() {
        Optional<MenuEntity> menuEntityFound = Optional.of(menuEntity);

        Mockito.when(menuRepository.findById(anyLong())).thenReturn(menuEntityFound);
        Mockito.when(menuEntityMapper.toMenu(any())).thenReturn(menu);

       Optional<Menu> menuExpected = menuJpaAdapter.findById(anyLong());

        assertEquals(menuExpected.get().getId(), menu.getId());

    }

    @Test
    void getMenuByRestaurant(){

        List<MenuEntity> testMenus = new ArrayList<>();

        testMenus.add(menuEntity);
        testMenus.add(menuEntity);
        Page<MenuEntity> menusPage = new PageImpl<>(testMenus, pageable, testMenus.size());

        when(menuRepository.findByRestaurantEntityIdAndActive(idRestaurant, true, pageable)).thenReturn(menusPage);
        when(menuEntityMapper.toMenu(any())).thenReturn(menu);

        PageResult<Menu> menuPageReturn = menuJpaAdapter.getMenuByRestaurant(idRestaurant, null, page, size, sortBy, "");
        assertEquals(2, menuPageReturn.getContent().size());


    }

    @Test
    void getMenuByRestaurantAndCategory(){

        Long idCategory = 1L;

        List<MenuEntity> testMenus = new ArrayList<>();
        testMenus.add(menuEntity);
        testMenus.add(menuEntity);

        Page<MenuEntity> menusPage = new PageImpl<>(testMenus, pageable, testMenus.size());

        when(menuRepository.findByRestaurantEntityIdAndCategoryEntityIdAndActive(idRestaurant, idCategory, true, pageable)).thenReturn(menusPage);
        when(menuEntityMapper.toMenu(any())).thenReturn(menu);

        PageResult<Menu> menuPageReturn = menuJpaAdapter.getMenuByRestaurant(idRestaurant, idCategory, page, size, sortBy, "");

        assertEquals(2, menuPageReturn.getContent().size());
    }
}