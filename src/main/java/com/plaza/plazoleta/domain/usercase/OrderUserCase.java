package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.constants.Constant;
import com.plaza.plazoleta.domain.spi.INotificationPersistencePort;
import com.plaza.plazoleta.domain.spi.IOrderPersistencePort;
import com.plaza.plazoleta.domain.api.IOrderServicePort;
import com.plaza.plazoleta.domain.model.*;
import com.plaza.plazoleta.domain.spi.ITraceabilityPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.domain.exception.OrderValidationException;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.domain.validation.OrderValidations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderUserCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final INotificationPersistencePort notificatoinPersistencePort;
    private final ITraceabilityPersistencePort traceabilityPersistencePort;

    public OrderUserCase(IOrderPersistencePort orderPersistencePort, IUserPersistencePort userPersistencePort, INotificationPersistencePort notificatoinPersistencePort, ITraceabilityPersistencePort traceabilityPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.notificatoinPersistencePort = notificatoinPersistencePort;
        this.traceabilityPersistencePort = traceabilityPersistencePort;
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

    //HU13
    @Override
    public void updateOrderToPreparation(Long id) {
        User user = userPersistencePort.getUseAuth();

        Optional<Order> orderToUpdateStatus = orderPersistencePort.finById(id);
        if (orderToUpdateStatus.isEmpty()){
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_FOUND.getMessage());
        }

        Status statusBeore = orderToUpdateStatus.get().getStatus();

        orderToUpdateStatus.get().setEmployeeAsignedId(user.getIdUser());
        orderToUpdateStatus.get().setStatus(Status.EN_PREPARACION);
        orderPersistencePort.UpdateStatusOrder(id, orderToUpdateStatus.orElseThrow());

        Traceability traceability = new Traceability(id, orderToUpdateStatus.get().getCustomer().getIdUser(), statusBeore, Status.EN_PREPARACION);
        traceabilityPersistencePort.insertTraceability(traceability);

    }

    //HU14
    @Override
    public void updateOrderToReady(Long id) {

        Optional<Order> orderToUpdateStatus = orderPersistencePort.finById(id);
        if (orderToUpdateStatus.isEmpty()){
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_FOUND.getMessage());
        }

        Status statusBeore = orderToUpdateStatus.get().getStatus();
        orderToUpdateStatus.get().setStatus(Status.LISTO);
        orderToUpdateStatus.get().setPin(UUID.randomUUID().toString());
        orderPersistencePort.UpdateStatusOrder(id, orderToUpdateStatus.orElseThrow());

        User user = userPersistencePort.getById(orderToUpdateStatus.get().getCustomer().getIdUser());
        Message message = new Message(Constant.MESSAGE_PIN.getValue() + orderToUpdateStatus.get().getPin(), user.getPhoneNumber());
        notificatoinPersistencePort.sendMessage(message);
        Traceability traceability = new Traceability(id, orderToUpdateStatus.get().getCustomer().getIdUser(), statusBeore, Status.LISTO);
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
        Status statusBeore = orderToUpdateStatus.get().getStatus();
        orderToUpdateStatus.get().setStatus(Status.ENTREGADO);
        Long id = orderToUpdateStatus.get().getId();
        orderPersistencePort.UpdateStatusOrder(id, orderToUpdateStatus.orElseThrow());

        Traceability traceability = new Traceability(id, orderToUpdateStatus.get().getCustomer().getIdUser(), statusBeore, Status.ENTREGADO);
        traceabilityPersistencePort.insertTraceability(traceability);
    }

    @Override
    public void updateOrderToCanceled(Long id) {

        Optional<Order> orderToUpdateStatus = orderPersistencePort.finById(id);
        if (orderToUpdateStatus.isEmpty()){
            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_FOUND.getMessage());
        }
        if(!orderToUpdateStatus.get().getStatus().equals(Status.PENDIENTE)){
            User user = userPersistencePort.getById(orderToUpdateStatus.get().getCustomer().getIdUser());
            Message message = new Message(Constant.MESSAGE_ORDER_IN_PREPARATION.getValue(), user.getPhoneNumber());
            notificatoinPersistencePort.sendMessage(message);

            throw new OrderValidationException(ExceptionResponse.ORDER_VALIDATION_NOT_IS_PENDING.getMessage());
        }
        Status statusBeore = orderToUpdateStatus.get().getStatus();
        orderToUpdateStatus.get().setStatus(Status.CANCELADO);
        orderPersistencePort.UpdateStatusOrder(id, orderToUpdateStatus.orElseThrow());

        Traceability traceability = new Traceability(id, orderToUpdateStatus.get().getCustomer().getIdUser(), statusBeore, Status.CANCELADO);
        traceabilityPersistencePort.insertTraceability(traceability);

    }

}