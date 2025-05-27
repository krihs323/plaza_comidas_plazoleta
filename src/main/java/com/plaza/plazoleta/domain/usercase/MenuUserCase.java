package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.api.IMenuServicePort;
import com.plaza.plazoleta.domain.api.IMenuServicePort;
import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.Restaurant;
import com.plaza.plazoleta.domain.model.User;
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

    //TODO remover
    private final IUserPersistencePort userPersistencePort;

    public MenuUserCase(IMenuPersistencePort menuPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IUserPersistencePort userPersistencePort) {
        this.menuPersistencePort = menuPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userPersistencePort = userPersistencePort;
    }



    @Override
    public void saveMenu(Menu menu) {

        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(menu.getRestaurantId());
        //Optional<User> rolByUserId = Optional.of(userPersistencePort.getById(restaurant.getUserId()));

        long idUsuarioSistema = 2L;

        //TODO Aqui debe reemplazar con el id del usuario autenticado
        if (idUsuarioSistema != restaurant.getUserId()) {
            throw new MenuValidationException(ExceptionResponse.MENU_VALIDATION_USER_NOT_VALID.getMessage());
        }
        menuPersistencePort.saveMenu(menu);
    }
}
