package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.model.*;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.MenuEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderDetailEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.OrderEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IMenuRepository;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IOrderDetailRepository;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

class OrderJpaAdapterTest {

    @Mock
    private IOrderRepository orderRepository;
    @Mock
    private OrderEntityMapper orderEntityMapper;
    @Mock
    private IOrderDetailRepository orderDetailRepository;
    @Mock
    private IMenuRepository menuRepository;
    @InjectMocks
    private OrderJpaAdapter orderJpaAdapter;

    private OrderEntity orderEntity;
    private OrderDetailEntity orderDetailEntity;
    private MenuEntity menuEntity;
    private Order order;
    private User user;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setStatus(Status.PENDIENTE);

        orderDetailEntity = new OrderDetailEntity();
        orderDetailEntity.setId(1L);
        orderDetailEntity.setAmount(1);

        List<OrderDetailEntity> list = new ArrayList<>();
        list.add(orderDetailEntity);

        orderEntity.setDetails(list);

        menuEntity = new MenuEntity();
        menuEntity.setName("Camaranos");
        menuEntity.setId(1L);
        menuEntity.setPrice(1000L);

        user = new User(Role.OWNER.name(), 9L, 19L, "+573155828235");


        Restaurant restaurant = new Restaurant();
        restaurant.setName("restarante");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail(1L, 2);
        orderDetailList.add(orderDetail);
        order = new Order(1L, user, restaurant, Status.PENDIENTE, orderDetailList, null, null);

    }

    @Test
    void saveOrder() {

        Mockito.when(orderEntityMapper.toEntity(any())).thenReturn(orderEntity);
        Mockito.when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        Mockito.when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menuEntity));

        orderJpaAdapter.saveOrder(order);

        verify(orderDetailRepository).saveAll(any());
    }

    @Test
    void findByCustomerAndStatusInvalid() {
    }

    @Test
    void getOrderByStatus() {
    }

    @Test
    void updateStatusOrder() {
    }

    @Test
    void finByPin() {
    }

    @Test
    void finById() {
    }
}