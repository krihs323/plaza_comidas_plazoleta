package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.spi.INotificationPersistencePort;
import com.plaza.plazoleta.domain.spi.IOrderPersistencePort;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.domain.exception.OrderValidationException;
import com.plaza.plazoleta.domain.model.*;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class OrderUserCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;
    @Mock
    private IUserPersistencePort userPersistencePort;
    @Mock
    private INotificationPersistencePort notificatoinPersistencePort;
    @InjectMocks
    private OrderUserCase orderUserCase;

    private User user;
    private int page;
    private int size;
    private String sortBy;
    private Pageable pageable;
    private Sort sort;
    private String sortDir;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        //creacion del mock de usuarios
        user = new User(Role.OWNER.name(), 9L, 19L, "+573155828235");

        page = 0;
        size = 2;
        sortBy = "id";
        sort = Sort.by(Sort.Direction.ASC , sortBy);
        sortDir = "ASC";
        pageable = PageRequest.of(page, size, sort);

        Restaurant restaurant = new Restaurant();
        restaurant.setName("restarante");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail(1L, 2);
        orderDetailList.add(orderDetail);
        order = new Order(1L, user, restaurant, Status.PENDIENTE, orderDetailList, null, null);
    }

    @Test
    void saveOrderWhenThereIsAStatusPending() {

        Optional<Order> orderNotAvailable = Optional.of(order);

        List<Status> invalidStatusList = List.of(Status.EN_PREPARACION, Status.PENDIENTE, Status.LISTO);
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        Mockito.when(orderPersistencePort.findByCustomerAndStatusInvalid(user.getIdUser(), invalidStatusList)).thenReturn(orderNotAvailable);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderUserCase.saveOrder(order);
        });

        assertEquals(ExceptionResponse.ORDER_VALIDATION_NOT_VALID.getMessage(), exception.getMessage());


    }

    @Test
    void saveOrder() {

        Optional<Order> orderNotAvailable = Optional.of(order);

        List<Status> invalidStatusList = List.of(Status.EN_PREPARACION, Status.PENDIENTE, Status.LISTO);
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        Mockito.when(orderPersistencePort.findByCustomerAndStatusInvalid(user.getIdUser(), invalidStatusList)).thenReturn(Optional.empty());
        doNothing().when(orderPersistencePort).saveOrder(any());
        orderUserCase.saveOrder(order);
        verify(orderPersistencePort).saveOrder(order);
    }

    @Test
    void getOrderByStatus() {
        Status statusEnum = Status.valueOf("PENDIENTE");
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);

        List<Order> testOrders = new ArrayList<>();
        testOrders.add(order);
        testOrders.add(order);
        Page<Order> orderPage = new PageImpl<>(testOrders, pageable, testOrders.size());
        //Mockito.when(orderPersistencePort.getOrderByStatus(user.getIdRestaurantEmployee(), statusEnum, page, size, sortBy, sortDir )).thenReturn(orderPage);

        //Page<Order> orderPageReturn = orderUserCase.getOrderByStatus(statusEnum.toString(), page, size, sortBy, sortDir);

        //assertEquals(2, orderPageReturn.getContent().size());

    }


    @Test
    void updateOrderToCanceled() {

        Mockito.when(orderPersistencePort.finById(anyLong())).thenReturn(Optional.of(order));
        Mockito.when(userPersistencePort.getById(anyLong())).thenReturn(user);
        doNothing().when(orderPersistencePort).UpdateStatusOrder(anyLong(), any());

        orderUserCase.updateOrderToCanceled(anyLong());

        verify(orderPersistencePort).UpdateStatusOrder(anyLong(), any());

    }

    @Test
    void updateOrderToCanceledWhenOrderIsDeliver() {
        order.setStatus(Status.ENTREGADO);
        Mockito.when(orderPersistencePort.finById(anyLong())).thenReturn(Optional.of(order));
        Mockito.when(userPersistencePort.getById(anyLong())).thenReturn(user);
        doNothing().when(orderPersistencePort).UpdateStatusOrder(anyLong(), any());
        doNothing().when(notificatoinPersistencePort).sendMessage(any());

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderUserCase.updateOrderToCanceled(anyLong());
        });

        assertEquals(ExceptionResponse.ORDER_VALIDATION_NOT_IS_PENDING.getMessage(), exception.getMessage());

    }

    @Test
    void updateOrderToDelivered() {
        order.setStatus(Status.LISTO);
        order.setPin("123456");
        Mockito.when(orderPersistencePort.finByPin(anyString())).thenReturn(Optional.of(order));
        doNothing().when(orderPersistencePort).UpdateStatusOrder(anyLong(), any());
        orderUserCase.updateOrderToDelivered(order);
        verify(orderPersistencePort).UpdateStatusOrder(anyLong(), any());

    }

    @Test
    void updateOrderToDeliveredWhenStatusIsDeliver() {
        order.setStatus(Status.ENTREGADO);
        order.setPin("123456");
        Mockito.when(orderPersistencePort.finByPin(anyString())).thenReturn(Optional.of(order));
        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderUserCase.updateOrderToDelivered(order);
        });
        assertEquals(ExceptionResponse.ORDER_VALIDATION_NOT_IS_READY.getMessage(), exception.getMessage());
    }

    @Test
    void updateOrderToDeliveredWhenOrderIsNotFound() {
        order.setStatus(Status.ENTREGADO);
        order.setPin("123456");
        Mockito.when(orderPersistencePort.finByPin(anyString())).thenReturn(Optional.empty());
        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderUserCase.updateOrderToDelivered(order);
        });
        assertEquals(ExceptionResponse.ORDER_VALIDATION_NOT_FOUND.getMessage(), exception.getMessage());
    }


    @Test
    void updateOrderToReady() {
        order.setPin("123456");
        Mockito.when(orderPersistencePort.finById(anyLong())).thenReturn(Optional.of(order));
        Mockito.when(userPersistencePort.getById(anyLong())).thenReturn(user);
        doNothing().when(orderPersistencePort).UpdateStatusOrder(anyLong(), any());
        doNothing().when(notificatoinPersistencePort).sendMessage(any());
        orderUserCase.updateOrderToReady(anyLong());
        verify(orderPersistencePort).UpdateStatusOrder(anyLong(), any());

    }

    @Test
    void updateOrderToReadyWhenOrderIsNotFound() {
        order.setPin("123456");
        Mockito.when(orderPersistencePort.finById(anyLong())).thenReturn(Optional.empty());

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderUserCase.updateOrderToReady(anyLong());
        });
        assertEquals(ExceptionResponse.ORDER_VALIDATION_NOT_FOUND.getMessage(), exception.getMessage());

    }

    @Test
    void updateOrderToPreparation() {

        Mockito.when(orderPersistencePort.finById(anyLong())).thenReturn(Optional.of(order));
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);//

        orderUserCase.updateOrderToPreparation(1L);
        verify(orderPersistencePort).UpdateStatusOrder(anyLong(), any());

    }

    @Test
    void updateOrderToPreparationWhenOrderIsNotFound() {

        Mockito.when(orderPersistencePort.finById(1L)).thenReturn(Optional.empty());
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);//

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderUserCase.updateOrderToPreparation(1L);
        });
        assertEquals(ExceptionResponse.ORDER_VALIDATION_NOT_FOUND.getMessage(), exception.getMessage());

    }
}