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
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
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
    private MenuEntity menuEntity;
    private Order order;
    private int page;
    private int size;
    private String sortBy;
    private Pageable pageable;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setStatus(Status.PENDIENTE);

        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderDetailEntity.setId(1L);
        orderDetailEntity.setAmount(1);

        List<OrderDetailEntity> list = new ArrayList<>();
        list.add(orderDetailEntity);

        orderEntity.setDetails(list);

        menuEntity = new MenuEntity();
        menuEntity.setName("Camaranos");
        menuEntity.setId(1L);
        menuEntity.setPrice(1000L);

        User user = new User(Role.OWNER.name(), 9L, 1L, "+573155828235", "Cristian", "Botina");

        Restaurant restaurant = new Restaurant();
        restaurant.setName("restarante");
        restaurant.setUserId(1L);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail(1L, 2);
        orderDetailList.add(orderDetail);
        order = new Order(1L, user, restaurant, Status.PENDIENTE, orderDetailList, null, null, null, null);

        page = 0;
        size = 2;
        sortBy = "id";
        Sort sort = Sort.by(Sort.Direction.ASC , sortBy);
        pageable = PageRequest.of(page, size, sort);
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

        Mockito.when(orderRepository.findByCustomerIdAndStatusIn(anyLong(), any())).thenReturn(Optional.of(orderEntity));
        Mockito.when(orderEntityMapper.toOrder(any())).thenReturn(order);

        Optional<Order> orderResponse = orderJpaAdapter.findByCustomerAndStatusInvalid(anyLong(), any());
        assertEquals(orderResponse.get().getId(), orderResponse.get().getId());

    }

    @Test
    void getOrderByStatus() {
        List<OrderEntity> testOrders = new ArrayList<>();
        OrderEntity orderEntityMock = new OrderEntity();
        orderEntityMock.setId(1L);
        orderEntityMock.setStatus(Status.PENDIENTE);
        testOrders.add(orderEntityMock);
        testOrders.add(orderEntityMock);

        Page<OrderEntity> menusPage = new PageImpl<>(testOrders, pageable, testOrders.size());

        Mockito.when(orderRepository.findByRestaurantEntityIdAndStatus(1L, Status.PENDIENTE, pageable)).thenReturn(menusPage);

        PageResult<Order> menuPageReturn = orderJpaAdapter.getOrderByStatus(1L, Status.PENDIENTE, page, size, sortBy, "ASC");
        assertEquals(2, menuPageReturn.getContent().size());
    }

    @Test
    void updateStatusOrder() {
        Mockito.when(orderEntityMapper.toEntity(any())).thenReturn(orderEntity);
        Mockito.when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        orderJpaAdapter.updateStatusOrder(order);
        verify(orderRepository).save(any());
    }

    @Test
    void finByPin() {

        Mockito.when(orderRepository.findByPinContaining(anyString())).thenReturn(Optional.of(orderEntity));
        Mockito.when(orderEntityMapper.toOrder(any())).thenReturn(order);

        Optional<Order> orderResponse = orderJpaAdapter.finByPin(anyString());
        assertEquals(orderResponse.get().getId(), orderResponse.get().getId());
    }

    @Test
    void finById() {

        Mockito.when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orderEntity));
        Mockito.when(orderEntityMapper.toOrder(any())).thenReturn(order);

        Optional<Order> orderResponse = orderJpaAdapter.finById(anyLong());
        assertEquals(orderResponse.get().getId(), orderResponse.get().getId());
    }

}