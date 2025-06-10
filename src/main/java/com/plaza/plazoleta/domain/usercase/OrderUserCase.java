package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.constants.Constant;
import com.plaza.plazoleta.domain.spi.*;
import com.plaza.plazoleta.domain.api.IOrderServicePort;
import com.plaza.plazoleta.domain.model.*;
import com.plaza.plazoleta.domain.exception.OrderValidationException;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.domain.validation.OrderValidations;
import com.plaza.plazoleta.domain.exception.MenuNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderUserCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final INotificationPersistencePort notificationPersistencePort;
    private final ITraceabilityPersistencePort traceabilityPersistencePort;
    private final IMenuPersistencePort menuPersistencePort;

    public OrderUserCase(IOrderPersistencePort orderPersistencePort, IUserPersistencePort userPersistencePort, INotificationPersistencePort notificationPersistencePort, ITraceabilityPersistencePort traceabilityPersistencePort, IMenuPersistencePort menuPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.notificationPersistencePort = notificationPersistencePort;
        this.traceabilityPersistencePort = traceabilityPersistencePort;
        this.menuPersistencePort = menuPersistencePort;
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

        for (OrderDetail req : order.getOrderDetailList()) {
            //TODO Validacion de dominio de los platos - AJUSTADO
            Optional<Menu> menuToValidate = menuPersistencePort.findById(req.getIdMenu());
            if (menuToValidate.isEmpty()) {
                throw new MenuNotFoundException(ExceptionResponse.RESTAURANT_VALIDATION_NOT_FOUND.getMessage());
            }
        }

        order.setStatus(Status.PENDIENTE);
        order.setCustomer(user);
        orderPersistencePort.saveOrder(order);
    }

    @Override
    public PageResult<Order> getOrderByStatus(String status, int page, int size, String sortBy, String sortDir) {
        OrderValidations.getORderByStatus(status);
        User user = userPersistencePort.getUseAuth();
        Status statusEnum = Status.valueOf(status);
        return orderPersistencePort.getOrderByStatus(user.getIdRestaurantEmployee(), statusEnum, page, size, sortBy, sortDir);
    }

    @Override
    public void updateOrderToPreparation(Long id) {
        User user = userPersistencePort.getUseAuth();

        Optional<Order> orderToUpdateStatus = orderPersistencePort.finById(id);
        isOrderValid(orderToUpdateStatus);
        isUserAsignedToEmployee(user.getIdRestaurantEmployee(), orderToUpdateStatus.get().getRestaurant().getId());

        Date dateNow = new Date();
        Status statusBefore = orderToUpdateStatus.get().getStatus();
        orderToUpdateStatus.get().setEmployeeAsignedId(user.getIdUser());
        orderToUpdateStatus.get().setStatus(Status.EN_PREPARACION);
        orderToUpdateStatus.get().setStartDate(dateNow);
        orderPersistencePort.updateStatusOrder(orderToUpdateStatus.orElseThrow());

        saveTraceability(orderToUpdateStatus.get(), statusBefore, Status.EN_PREPARACION, dateNow);

    }

    @Override
    public void updateOrderToReady(Long id) {

        Optional<Order> orderToUpdateStatus = orderPersistencePort.finById(id);
        isOrderValid(orderToUpdateStatus);
        User userAuth = userPersistencePort.getUseAuth();
        //TODO Separar las validaciones repetidas como en utils, o metodo estatico - AJUSTADO
        isUserAsignedToEmployee(userAuth.getIdRestaurantEmployee(), orderToUpdateStatus.get().getRestaurant().getId());
        if (!userAuth.getIdUser().equals(orderToUpdateStatus.get().getEmployeeAsignedId())) {
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_ORDER_NOT_ASIGNED_TO_EMPLOYEE.getMessage());
        }
        Status statusBefore = orderToUpdateStatus.get().getStatus();
        orderToUpdateStatus.get().setStatus(Status.LISTO);
        orderToUpdateStatus.get().setPin(UUID.randomUUID().toString());
        orderPersistencePort.updateStatusOrder(orderToUpdateStatus.orElseThrow());

        sendNotification(orderToUpdateStatus.get(), Constant.MESSAGE_PIN.getValue() + orderToUpdateStatus.get().getPin());
        saveTraceability(orderToUpdateStatus.get(), statusBefore, Status.LISTO, new Date());

    }

    @Override
    public void updateOrderToDelivered(Order order) {

        Optional<Order> orderToUpdateStatus = orderPersistencePort.finByPin(order.getPin());
        isOrderValid(orderToUpdateStatus);
        if(!orderToUpdateStatus.get().getStatus().equals(Status.LISTO)){
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_IS_READY.getMessage());
        }
        User userAuth = userPersistencePort.getUseAuth();
        isUserAsignedToEmployee(userAuth.getIdRestaurantEmployee(), orderToUpdateStatus.get().getRestaurant().getId());
        Date dateNow = new Date();
        Status statusBefore = orderToUpdateStatus.get().getStatus();
        orderToUpdateStatus.get().setEndDate(dateNow);
        orderToUpdateStatus.get().setStatus(Status.ENTREGADO);
        orderPersistencePort.updateStatusOrder(orderToUpdateStatus.orElseThrow());

        saveTraceability(orderToUpdateStatus.get(), statusBefore, Status.ENTREGADO, dateNow);

    }

    @Override
    public void updateOrderToCanceled(Long id) {

        Optional<Order> orderToUpdateStatus = orderPersistencePort.finById(id);
        isOrderValid(orderToUpdateStatus);
        User userAuth = userPersistencePort.getUseAuth();
        if (!orderToUpdateStatus.get().getCustomer().getIdUser().equals(userAuth.getIdUser())) {
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_CUSTOMER_NOTALLOWED.getMessage());
        }
        if(!orderToUpdateStatus.get().getStatus().equals(Status.PENDIENTE)){
            sendNotification(orderToUpdateStatus.get(), Constant.MESSAGE_ORDER_IN_PREPARATION.getValue());
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_IS_PENDING.getMessage());
        }
        Status statusBefore = orderToUpdateStatus.get().getStatus();
        orderToUpdateStatus.get().setStatus(Status.CANCELADO);
        orderPersistencePort.updateStatusOrder(orderToUpdateStatus.orElseThrow());

        //TODO Codigo repetido, reacomodar a un metodo privado que resuelva -AJUSTADO
        saveTraceability(orderToUpdateStatus.get(), statusBefore, Status.CANCELADO, new Date());

    }

    private void saveTraceability(Order order, Status statusBefore, Status statusAfter, Date date) {

        User userCustomer = userPersistencePort.getById(order.getCustomer().getIdUser());
        User userEmployee = userPersistencePort.getById(order.getEmployeeAsignedId());
        String customerName = userCustomer.getName().concat(" ").concat(userCustomer.getLastName());
        String restaurantName = order.getRestaurant().getName();
        Long idRestaurant = order.getRestaurant().getId();
        String employeeName = userEmployee.getName().concat(" ").concat(userEmployee.getLastName());
        Long idEmployee = userEmployee.getIdUser();
        Traceability traceability = new Traceability(order.getId(), order.getCustomer().getIdUser(), statusBefore, statusAfter, date, customerName, restaurantName, idRestaurant, employeeName, idEmployee);
        traceabilityPersistencePort.insertTraceability(traceability);

    }

    private void isOrderValid(Optional<Order> order) {
        if (order.isEmpty()){
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_FOUND.getMessage());
        }
    }

    private void isUserAsignedToEmployee(Long employeeIdRestaurantAssigned, Long orderIdRestaurant) {
        if (!employeeIdRestaurantAssigned.equals(orderIdRestaurant)) {
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_ORDER_NOT_ASIGNED_TO_RESTAURANT_EMPLOYEE.getMessage());
        }
    }

    private void sendNotification(Order order, String messageString) {
        User user = userPersistencePort.getById(order.getCustomer().getIdUser());
        Message message = new Message(messageString, user.getPhoneNumber());
        notificationPersistencePort.sendMessage(message);

    }


}