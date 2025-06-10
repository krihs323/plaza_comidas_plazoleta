package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.api.IMenuServicePort;
import com.plaza.plazoleta.domain.exception.MenuUserCaseValidationException;
import com.plaza.plazoleta.domain.model.*;
import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.spi.IMenuPersistencePort;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.domain.exception.MenuValidationException;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.domain.validation.MenuValidations;

import java.util.Optional;

public class MenuUserCase implements IMenuServicePort {

    private final IMenuPersistencePort menuPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserPersistencePort userPersistencePort;

    public MenuUserCase(IMenuPersistencePort menuPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IUserPersistencePort userPersistencePort) {

        this.menuPersistencePort = menuPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userPersistencePort = userPersistencePort;
    }


    @Override
    public void saveMenu(Menu menu) {
        MenuValidations.saveMenu(menu);
        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(menu.getRestaurant().getId());
        User userAuth = userPersistencePort.getUseAuth();
        if (!userAuth.getIdUser().equals(restaurant.getUserId())) {
            throw new MenuValidationException(ExceptionResponse.MENU_VALIATION_OWNER.getMessage());
        }

        menuPersistencePort.saveMenu(menu);
    }

    @Override
    public void updateMenu(Long id, Menu menu) {
        MenuValidations.updateMenu(menu);
        Optional<Menu> menuFound = menuPersistencePort.findById(id);
        if (menuFound.isEmpty()) {
            throw new MenuUserCaseValidationException(ExceptionResponse.MENU_VALIATION_NOT_FOUND.getMessage());
        }
        Menu menuToUpdate = menuFound.get();
        User userAuth = userPersistencePort.getUseAuth();
        if (!userAuth.getIdUser().equals(menuToUpdate.getRestaurant().getUserId())) {
            throw new MenuValidationException(ExceptionResponse.MENU_VALIATION_OWNER.getMessage());
        }

        menuToUpdate.setDescription(menu.getDescription());
        menuToUpdate.setPrice(menu.getPrice());

        menuPersistencePort.saveMenu(menuToUpdate);
    }

    @Override
    public void disableMenu(Long id, Menu menu) {
        Optional<Menu> menuFound = menuPersistencePort.findById(id);
        if (menuFound.isEmpty()) {
            throw new MenuUserCaseValidationException(ExceptionResponse.MENU_VALIATION_NOT_FOUND.getMessage());
        }
        Menu menuToUpdate = menuFound.get();
        User userAuth = userPersistencePort.getUseAuth();
        if (!userAuth.getIdUser().equals(menuToUpdate.getRestaurant().getUserId())) {
            throw new MenuValidationException(ExceptionResponse.MENU_VALIATION_OWNER.getMessage());
        }
        menuToUpdate.setActive(menu.getActive());
        menuPersistencePort.saveMenu(menuToUpdate);
    }

    @Override
    public PageResult<Menu> getMenuByRestaurant(Long idRestaurant, String category, int page, int size, String sortBy, String sortDir) {
        Long idCategory = null;
        if (!category.isEmpty()) {
            idCategory = Long.parseLong(category);
        }
        return menuPersistencePort.getMenuByRestaurant(idRestaurant, idCategory, page, size, sortBy, sortDir);
    }


}