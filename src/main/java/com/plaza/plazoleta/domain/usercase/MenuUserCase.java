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
import com.plaza.plazoleta.infraestructure.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;

//import com.plaza.plazoleta.infraestructure.exception.MenuValidationException;

import java.util.Optional;

public class MenuUserCase implements IMenuServicePort {

    private final IMenuPersistencePort menuPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;


    //TODO
    private final IUserPersistencePort userPersistencePort;

    public MenuUserCase(IMenuPersistencePort menuPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IUserPersistencePort userPersistencePort, ICategoryPersistencePort categoryPersistencePort, HttpServletRequest httpServletRequest, JwtService jwtService) {

        this.menuPersistencePort = menuPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.httpServletRequest = httpServletRequest;
        this.jwtService = jwtService;

    }

    private Long getIdUserLog(Menu menu) {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String jwtAuthorizationHeader = null;
        if (authorizationHeader.startsWith("Bearer ")) {
            jwtAuthorizationHeader = authorizationHeader.substring(7);
        }
        String userEmail = jwtService.extractUsername(jwtAuthorizationHeader);
        User user = userPersistencePort.getByEmail(userEmail, authorizationHeader);
        Optional<User> rolByUserEmail = Optional.ofNullable(user);

        return rolByUserEmail.orElseThrow().getIdUser();
    }


    @Override
    public void saveMenu(Menu menu) {
        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(menu.getRestaurant().getId());

        Long getIdUserLog = getIdUserLog(menu);

        //TODO Aqui debe reemplazar con el id del usuario autenticado
        if (getIdUserLog != restaurant.getUserId()) {
            throw new MenuValidationException(ExceptionResponse.MENU_VALIATION_OWNER.getMessage());

        }

        menuPersistencePort.saveMenu(menu);
    }

    @Override
    public void updateMenu(Long id, Menu menu) {

        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(menu.getRestaurant().getId());

        Long getIdUserLog = getIdUserLog(menu);

        //TODO Aqui debe reemplazar con el id del usuario autenticado
        if (getIdUserLog != restaurant.getUserId()) {
            throw new MenuValidationException(ExceptionResponse.MENU_VALIATION_OWNER.getMessage());

        }

        Optional<Menu> menuFound = menuPersistencePort.findById(id);
        Category category = categoryPersistencePort.getCategoryById(menu.getCategory().getId());
        menu.setId(menuFound.get().getId());
        menu.setRestaurant(restaurant);
        menu.setCategory(category);

        menuPersistencePort.updateMenu(id, menu);
    }
}