package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.api.IMenuServicePort;
import com.plaza.plazoleta.domain.api.IMenuServicePort;
import com.plaza.plazoleta.domain.model.*;
import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.spi.ICategoryPersistencePort;
import com.plaza.plazoleta.domain.spi.IMenuPersistencePort;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.infraestructure.exception.MenuValidationException;
import com.plaza.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;
//import com.plaza.plazoleta.infraestructure.exception.MenuValidationException;

import java.util.Optional;

public class MenuUserCase implements IMenuServicePort {

    private final IMenuPersistencePort menuPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    //TODO
    private final IUserPersistencePort userPersistencePort;

    public MenuUserCase(IMenuPersistencePort menuPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IUserPersistencePort userPersistencePort, ICategoryPersistencePort categoryPersistencePort) {
        this.menuPersistencePort = menuPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }



    @Override
    public void saveMenu(Menu menu) {
        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(menu.getRestaurant().getId());

        long idUsuarioSistema = 2L;

        //TODO Aqui debe reemplazar con el id del usuario autenticado
        if (idUsuarioSistema != restaurant.getUserId()) {
            throw new MenuValidationException(ExceptionResponse.MENU_VALIATION.getMessage());
        }
        menuPersistencePort.saveMenu(menu);
    }

    @Override
    public void updateMenu(Long id, Menu menu) {

        //TODO dene salir de la autenticacion HU5
        long idUsuarioSistema = 2L;

        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(menu.getRestaurant().getId());

        if (idUsuarioSistema != restaurant.getUserId()) {
            throw new MenuValidationException(ExceptionResponse.MENU_VALIATION.getMessage());
        }

        Optional<Menu> menuFound = menuPersistencePort.findById(id);
        Category category = categoryPersistencePort.getCategoryById(menu.getCategory().getId());
        menu.setId(menuFound.get().getId());
        menu.setRestaurant(restaurant);
        menu.setCategory(category);

        menuPersistencePort.updateMenu(id, menu);
    }
}
