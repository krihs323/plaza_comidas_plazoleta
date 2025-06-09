package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.constants.Constant;
import com.plaza.plazoleta.domain.spi.*;
import com.plaza.plazoleta.domain.api.IOrderServicePort;
import com.plaza.plazoleta.domain.model.*;
import com.plaza.plazoleta.domain.exception.OrderValidationException;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.domain.validation.OrderValidations;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderUserCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final INotificationPersistencePort notificationPersistencePort;
    private final ITraceabilityPersistencePort traceabilityPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public OrderUserCase(IOrderPersistencePort orderPersistencePort, IUserPersistencePort userPersistencePort, INotificationPersistencePort notificationPersistencePort, ITraceabilityPersistencePort traceabilityPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.notificationPersistencePort = notificationPersistencePort;
        this.traceabilityPersistencePort = traceabilityPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveOrder(Order order) {
        OrderValidations.saveOrder(order);
        User user = userPersistencePort.getUseAuth();
        List<Status> invalidStatusList = List.of(Status.EN_PREPARACION, Status.PENDIENTE, Status.LISTO);
        Optional<Order> orderNotAvailable = orderPersistencePort.findByCustomerAndStatusInvalid(user.getIdUser(), invalidStatusList);
        if (orderNotAvailable.isPresent()) {
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_VALID.getMessage());
        }

        order.setStatus(Status.PENDIENTE);
        order.setCustomer(user);
        orderPersistencePort.saveOrder(order);
    }

    @Override
    public PageResult<Order> getOrderByStatus(String status, int page, int size, String sortBy, String sortDir) {
        User user = userPersistencePort.getUseAuth();
        Status statusEnum = Status.valueOf(status);
        return orderPersistencePort.getOrderByStatus(user.getIdRestaurantEmployee(), statusEnum, page, size, sortBy, sortDir);
    }

    @Override
    public void updateOrderToPreparation(Long id) {
        User user = userPersistencePort.getUseAuth();

        Optional<Order> orderToUpdateStatus = orderPersistencePort.finById(id);
        if (orderToUpdateStatus.isEmpty()){
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_FOUND.getMessage());
        }
        if (!user.getIdRestaurantEmployee().equals(orderToUpdateStatus.get().getRestaurant().getId())) {
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_ORDER_NOT_ASIGNED_TO_EMPLOYEE.getMessage());
        }

        Date dateNow = new Date();
        Status statusBeore = orderToUpdateStatus.get().getStatus();
        orderToUpdateStatus.get().setEmployeeAsignedId(user.getIdUser());
        orderToUpdateStatus.get().setStatus(Status.EN_PREPARACION);
        orderToUpdateStatus.get().setStartDate(dateNow);
        orderPersistencePort.updateStatusOrder(orderToUpdateStatus.orElseThrow());

        User userCustomer = userPersistencePort.getById(orderToUpdateStatus.get().getCustomer().getIdUser());
        User userEmployee = userPersistencePort.getById(orderToUpdateStatus.get().getEmployeeAsignedId());
        String customerName = userCustomer.getName().concat(" ").concat(userCustomer.getLastName());
        String restaurantName = orderToUpdateStatus.get().getRestaurant().getName();
        Long idRestaurant = orderToUpdateStatus.get().getRestaurant().getId();
        String employeeName = userEmployee.getName().concat(" ").concat(userEmployee.getLastName());
        Long idEmployee = userEmployee.getIdUser();
        Traceability traceability = new Traceability(id, orderToUpdateStatus.get().getCustomer().getIdUser(), statusBeore, Status.EN_PREPARACION, dateNow, customerName, restaurantName, idRestaurant, employeeName, idEmployee);
        traceabilityPersistencePort.insertTraceability(traceability);

    }

    @Override
    public void updateOrderToReady(Long id) {

        Optional<Order> orderToUpdateStatus = orderPersistencePort.finById(id);
        if (orderToUpdateStatus.isEmpty()){
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_FOUND.getMessage());
        }
        User userAuth = userPersistencePort.getUseAuth();
        if (!userAuth.getIdRestaurantEmployee().equals(orderToUpdateStatus.get().getRestaurant().getId())) {
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_ORDER_NOT_ASIGNED_TO_RESTAURANT_EMPLOYEE.getMessage());
        }
        if (!userAuth.getIdUser().equals(orderToUpdateStatus.get().getEmployeeAsignedId())) {
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_ORDER_NOT_ASIGNED_TO_EMPLOYEE.getMessage());
        }
        Status statusBeore = orderToUpdateStatus.get().getStatus();
        orderToUpdateStatus.get().setStatus(Status.LISTO);
        orderToUpdateStatus.get().setPin(UUID.randomUUID().toString());
        orderPersistencePort.updateStatusOrder(orderToUpdateStatus.orElseThrow());

        User user = userPersistencePort.getById(orderToUpdateStatus.get().getCustomer().getIdUser());
        Message message = new Message(Constant.MESSAGE_PIN.getValue() + orderToUpdateStatus.get().getPin(), user.getPhoneNumber());
        notificationPersistencePort.sendMessage(message);


        User userCustomer = userPersistencePort.getById(orderToUpdateStatus.get().getCustomer().getIdUser());
        User userEmployee = userPersistencePort.getById(orderToUpdateStatus.get().getEmployeeAsignedId());
        String customerName = userCustomer.getName().concat(" ").concat(userCustomer.getLastName());
        String restaurantName = orderToUpdateStatus.get().getRestaurant().getName();
        Long idRestaurant = orderToUpdateStatus.get().getRestaurant().getId();
        String employeeName = userEmployee.getName().concat(" ").concat(userEmployee.getLastName());
        Long idEmployee = userEmployee.getIdUser();
        Traceability traceability = new Traceability(id, orderToUpdateStatus.get().getCustomer().getIdUser(), statusBeore, Status.LISTO, new Date(), customerName, restaurantName, idRestaurant, employeeName, idEmployee);

        traceabilityPersistencePort.insertTraceability(traceability);

    }

    @Override
    public void updateOrderToDelivered(Order order) {

        Optional<Order> orderToUpdateStatus = orderPersistencePort.finByPin(order.getPin());
        if (orderToUpdateStatus.isEmpty()){
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_FOUND.getMessage());
        }
        if(!orderToUpdateStatus.get().getStatus().equals(Status.LISTO)){
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_IS_READY.getMessage());
        }
        User userAuth = userPersistencePort.getUseAuth();
        if (!userAuth.getIdRestaurantEmployee().equals(orderToUpdateStatus.get().getRestaurant().getId())) {
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_ORDER_NOT_ASIGNED_TO_RESTAURANT_EMPLOYEE.getMessage());
        }
        Date dateNow = new Date();
        Status statusBeore = orderToUpdateStatus.get().getStatus();
        orderToUpdateStatus.get().setEndDate(dateNow);
        orderToUpdateStatus.get().setStatus(Status.ENTREGADO);
        orderPersistencePort.updateStatusOrder(orderToUpdateStatus.orElseThrow());

        User userCustomer = userPersistencePort.getById(orderToUpdateStatus.get().getCustomer().getIdUser());
        User userEmployee = userPersistencePort.getById(orderToUpdateStatus.get().getEmployeeAsignedId());
        String customerName = userCustomer.getName().concat(" ").concat(userCustomer.getLastName());
        String restaurantName = orderToUpdateStatus.get().getRestaurant().getName();
        Long idRestaurant = orderToUpdateStatus.get().getRestaurant().getId();
        String employeeName = userEmployee.getName().concat(" ").concat(userEmployee.getLastName());
        Long idEmployee = userEmployee.getIdUser();
        Traceability traceability = new Traceability(orderToUpdateStatus.get().getId(), orderToUpdateStatus.get().getCustomer().getIdUser(), statusBeore, Status.ENTREGADO, dateNow, customerName, restaurantName, idRestaurant, employeeName, idEmployee);

        traceabilityPersistencePort.insertTraceability(traceability);
    }

    @Override
    public void updateOrderToCanceled(Long id) {

        Optional<Order> orderToUpdateStatus = orderPersistencePort.finById(id);
        if (orderToUpdateStatus.isEmpty()){
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_FOUND.getMessage());
        }
        User userAuth = userPersistencePort.getUseAuth();
        if (!orderToUpdateStatus.get().getCustomer().getIdUser().equals(userAuth.getIdUser())) {
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_CUSTOMER_NOTALLOWED.getMessage());
        }
        if(!orderToUpdateStatus.get().getStatus().equals(Status.PENDIENTE)){
            User user = userPersistencePort.getById(orderToUpdateStatus.get().getCustomer().getIdUser());
            Message message = new Message(Constant.MESSAGE_ORDER_IN_PREPARATION.getValue(), user.getPhoneNumber());
            notificationPersistencePort.sendMessage(message);

            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_IS_PENDING.getMessage());
        }
        Status statusBeore = orderToUpdateStatus.get().getStatus();
        orderToUpdateStatus.get().setStatus(Status.CANCELADO);
        orderPersistencePort.updateStatusOrder(orderToUpdateStatus.orElseThrow());

        User userCustomer = userPersistencePort.getById(orderToUpdateStatus.get().getCustomer().getIdUser());
        User userEmployee = userPersistencePort.getById(orderToUpdateStatus.get().getEmployeeAsignedId());
        String customerName = userCustomer.getName().concat(" ").concat(userCustomer.getLastName());
        String restaurantName = orderToUpdateStatus.get().getRestaurant().getName();
        Long idRestaurant = orderToUpdateStatus.get().getRestaurant().getId();
        String employeeName = userEmployee.getName().concat(" ").concat(userEmployee.getLastName());
        Long idEmployee = userEmployee.getIdUser();
        Traceability traceability = new Traceability(id, orderToUpdateStatus.get().getCustomer().getIdUser(), statusBeore, Status.CANCELADO, new Date(), customerName, restaurantName, idRestaurant, employeeName, idEmployee);

        traceabilityPersistencePort.insertTraceability(traceability);

    }


}