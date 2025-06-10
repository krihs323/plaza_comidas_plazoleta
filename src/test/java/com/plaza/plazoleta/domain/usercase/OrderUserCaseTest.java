package com.plaza.plazoleta.domain.usercase;

import com.plaza.plazoleta.domain.exception.MenuNotFoundException;
import com.plaza.plazoleta.domain.exception.OrderUserCaseValidationException;
import com.plaza.plazoleta.domain.spi.*;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.domain.exception.OrderValidationException;
import com.plaza.plazoleta.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
    @Mock
    private ITraceabilityPersistencePort traceabilityPersistencePort;
    @Mock
    private IMenuPersistencePort menuPersistencePort;


    @InjectMocks
    private OrderUserCase orderUserCase;

    private User user;
    private int page;
    private int size;
    private String sortBy;
    private String sortDir;
    private Order order;

    private Restaurant restaurant;
    private PageResult<Order> pageResultOrder;
    private Menu menu;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User(Role.OWNER.name(), 9L, 19L, "+573155828235", "Cristian", "Botina");

        page = 0;
        size = 2;
        sortBy = "id";
        sortDir = "ASC";

        restaurant = new Restaurant();
        restaurant.setName("Restaurante");
        restaurant.setNumberId(1143826302L);
        restaurant.setUserId(9L);
        restaurant.setAddress("Avenida siempre viva");
        restaurant.setPhoneNumber("+573155763456");
        restaurant.setUrlLogo("hhpt:");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail(1L, 2);
        orderDetailList.add(orderDetail);
        order = new Order(1L, user, restaurant, Status.PENDIENTE, orderDetailList, 9L, null, null, null);

        List<Order> testOrders = new ArrayList<>();
        testOrders.add(order);
        testOrders.add(order);

        pageResultOrder = new PageResult<>();
        pageResultOrder.setContent(testOrders);
        pageResultOrder.setPageNumber(1);
        pageResultOrder.setPageSize(1);
        pageResultOrder.setTotalPages(1);
        pageResultOrder.setLast(true);
        pageResultOrder.setTotalElements(1);

        Category category = new Category();
        category.setId(1L);
        category.setName("Comida Tipica");

        menu = new Menu();
        menu.setId(1L);
        menu.setName("Pasta");
        menu.setPrice(2000L);
        menu.setDescription("Pasta al ajo");
        menu.setUrlLogo("http");
        menu.setCategory(category);
        menu.setRestaurant(restaurant);
        menu.setActive(true);
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

        List<Status> invalidStatusList = List.of(Status.EN_PREPARACION, Status.PENDIENTE, Status.LISTO);
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        Mockito.when(orderPersistencePort.findByCustomerAndStatusInvalid(user.getIdUser(), invalidStatusList)).thenReturn(Optional.empty());
        Mockito.when(menuPersistencePort.findById(anyLong())).thenReturn(Optional.of(menu));
        doNothing().when(orderPersistencePort).saveOrder(any());
        orderUserCase.saveOrder(order);
        verify(orderPersistencePort).saveOrder(order);
    }

    @Test
    void saveOrderWhenMenuIdNotExist() {

        List<Status> invalidStatusList = List.of(Status.EN_PREPARACION, Status.PENDIENTE, Status.LISTO);
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        Mockito.when(orderPersistencePort.findByCustomerAndStatusInvalid(user.getIdUser(), invalidStatusList)).thenReturn(Optional.empty());
        Mockito.when(menuPersistencePort.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(orderPersistencePort).saveOrder(any());
        MenuNotFoundException exception = assertThrows(MenuNotFoundException.class, () -> {
            orderUserCase.saveOrder(order);
        });
        assertEquals(ExceptionResponse.RESTAURANT_VALIDATION_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void getOrderByStatus() {
        Status statusEnum = Status.valueOf("PENDIENTE");
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        Mockito.when(orderPersistencePort.getOrderByStatus(user.getIdRestaurantEmployee(), statusEnum, page, size, sortBy, sortDir )).thenReturn(pageResultOrder);
        PageResult<Order> orderPageReturn = orderUserCase.getOrderByStatus(statusEnum.toString(), page, size, sortBy, sortDir);
        assertEquals(2, orderPageReturn.getContent().size());

    }


    @Test
    void updateOrderToCanceled() {

        Mockito.when(orderPersistencePort.finById(anyLong())).thenReturn(Optional.of(order));
        Mockito.when(userPersistencePort.getById(9L)).thenReturn(user);
        doNothing().when(orderPersistencePort).updateStatusOrder(any());
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        orderUserCase.updateOrderToCanceled(anyLong());

        verify(orderPersistencePort).updateStatusOrder(any());

    }

    @Test
    void updateOrderToCanceledWhenOrderIsDeliver() {
        order.setStatus(Status.ENTREGADO);
        Mockito.when(orderPersistencePort.finById(anyLong())).thenReturn(Optional.of(order));
        Mockito.when(userPersistencePort.getById(anyLong())).thenReturn(user);
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        doNothing().when(orderPersistencePort).updateStatusOrder(any());
        doNothing().when(notificatoinPersistencePort).sendMessage(any());

        OrderValidationException exception = assertThrows(OrderValidationException.class, () ->
            orderUserCase.updateOrderToCanceled(anyLong())
        );

        assertEquals(ExceptionResponse.ORDER_VALIDATION_NOT_IS_PENDING.getMessage(), exception.getMessage());

    }

    @Test
    void updateOrderToDelivered() {
        order.setStatus(Status.LISTO);
        order.setPin("123456");
        order.getRestaurant().setId(1L);
        order.setEmployeeAsignedId(2L);
        user.setIdRestaurantEmployee(1L);

        User userEmploye = user;
        userEmploye.setIdUser(2L);
        userEmploye.setRol(Role.EMPLOYEE.name());
        userEmploye.setIdRestaurantEmployee(1L);
        userEmploye.setName("pedro");
        userEmploye.setLastName("perez");
        userEmploye.setPhoneNumber("+573433434");
        Mockito.when(userPersistencePort.getById(9L)).thenReturn(user);
        Mockito.when(userPersistencePort.getById(2L)).thenReturn(userEmploye);
        Mockito.when(orderPersistencePort.finByPin(anyString())).thenReturn(Optional.of(order));
        doNothing().when(orderPersistencePort).updateStatusOrder(any());
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);

        orderUserCase.updateOrderToDelivered(order);
        verify(orderPersistencePort).updateStatusOrder(any());

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
        order.getRestaurant().setId(1L);
        order.setEmployeeAsignedId(9L);
        user.setIdRestaurantEmployee(1L);
        Mockito.when(orderPersistencePort.finById(anyLong())).thenReturn(Optional.of(order));
        Mockito.when(userPersistencePort.getById(anyLong())).thenReturn(user);
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        doNothing().when(orderPersistencePort).updateStatusOrder(any());
        doNothing().when(notificatoinPersistencePort).sendMessage(any());
        orderUserCase.updateOrderToReady(anyLong());
        verify(orderPersistencePort).updateStatusOrder(any());

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

        order.getRestaurant().setId(1L);
        order.setEmployeeAsignedId(9L);
        user.setIdRestaurantEmployee(1L);

        Mockito.when(orderPersistencePort.finById(anyLong())).thenReturn(Optional.of(order));
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);
        Mockito.when(userPersistencePort.getById(anyLong())).thenReturn(user);
        Mockito.when(traceabilityPersistencePort.insertTraceability(any())).thenReturn(true);

        orderUserCase.updateOrderToPreparation(1L);
        verify(orderPersistencePort).updateStatusOrder(any());

    }

    @Test
    void updateOrderToPreparationWhenOrderIsNotFound() {

        Mockito.when(orderPersistencePort.finById(1L)).thenReturn(Optional.empty());
        Mockito.when(userPersistencePort.getUseAuth()).thenReturn(user);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderUserCase.updateOrderToPreparation(1L);
        });
        assertEquals(ExceptionResponse.ORDER_VALIDATION_NOT_FOUND.getMessage(), exception.getMessage());

    }


    @Test
    void saveOrderWhenRestaurantIsEmpty() {
        order.setRestaurant(null);
        OrderUserCaseValidationException exception = assertThrows(OrderUserCaseValidationException.class, () -> {
            orderUserCase.saveOrder(order);
        });
        assertEquals(ExceptionResponse.ORDER_VALIATION_RESTAURANT.getMessage(), exception.getMessage());
    }

    @Test
    void saveOrderWhenOrderDetailIsEmpty() {
        order.setOrderDetailList(null);
        OrderUserCaseValidationException exception = assertThrows(OrderUserCaseValidationException.class, () -> {
            orderUserCase.saveOrder(order);
        });
        assertEquals(ExceptionResponse.ORDER_VALIATION_DETAIL.getMessage(), exception.getMessage());
    }


}