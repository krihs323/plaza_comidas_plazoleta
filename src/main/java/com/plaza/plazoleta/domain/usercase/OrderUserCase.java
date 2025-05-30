package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.api.IOrderPersistencePort;
import com.plaza.plazoleta.domain.api.IOrderServicePort;
import com.plaza.plazoleta.domain.model.*;
import com.plaza.plazoleta.domain.spi.ICategoryPersistencePort;
import com.plaza.plazoleta.domain.spi.IMenuPersistencePort;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.infraestructure.exception.MenuValidationException;
import com.plaza.plazoleta.infraestructure.exception.OrderValidationException;
import com.plaza.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;
import com.plaza.plazoleta.infraestructure.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public class OrderUserCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    //TODO Validar si son necesarios
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;
    private final IUserPersistencePort userPersistencePort;


    public OrderUserCase(IOrderPersistencePort orderPersistencePort, HttpServletRequest httpServletRequest, JwtService jwtService, IUserPersistencePort userPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.httpServletRequest = httpServletRequest;
        this.jwtService = jwtService;
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void saveOrder(Order order) {
        //Restaurant restaurant = restaurantPersistencePort.getRestaurantById(menu.getRestaurant().getId());
       // Long getIdUserLog = getIdUserLog();
//        if (getIdUserLog != restaurant.getUserId()) {
//            throw new MenuValidationException(ExceptionResponse.MENU_VALIATION_OWNER.getMessage());
//        }

        Long getIdUserLog = getIdUserLog();

        //Buscar un pedido diferente a estos estados en_preparacion,pendiente, listo
        List<Status> invalidStatusList = List.of(Status.EN_PREPARACION, Status.PENDIENTE, Status.LISTO);
        Optional<Order> orderNotAvailable = orderPersistencePort.findByCustomerAndStatusInvalid(getIdUserLog, invalidStatusList);
        if(orderNotAvailable.isPresent()){
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_VALID.getMessage());
        }

        //Crea la nueva orden
        order.setStatus(Status.PENDIENTE);
        User user = new User("",  getIdUserLog);
        order.setCustomer(user);
        orderPersistencePort.saveOrder(order);

        //Despues de crear el pedido, obtiene el id y crea el detalle a partir de la lista de platos
    }

    //Delegar aun servicio
    private Long getIdUserLog() {

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
}