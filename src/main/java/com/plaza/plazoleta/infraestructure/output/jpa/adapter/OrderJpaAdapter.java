package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.model.*;
import com.plaza.plazoleta.domain.spi.IOrderPersistencePort;
import com.plaza.plazoleta.infraestructure.exception.MenuNotFoundException;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.MenuEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderDetailEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.OrderEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IMenuRepository;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IOrderDetailRepository;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final IOrderDetailRepository orderDetailRepository;
    private final IMenuRepository menuRepository;


    public OrderJpaAdapter(IOrderRepository orderRepository, OrderEntityMapper orderEntityMapper, IOrderDetailRepository orderDetailRepository, IMenuRepository menuRepository) {
        this.orderRepository = orderRepository;
        this.orderEntityMapper = orderEntityMapper;
        this.orderDetailRepository = orderDetailRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public void saveOrder(Order order) {

        OrderEntity orderSaved = orderRepository.save(orderEntityMapper.toEntity(order));
        List<OrderDetailEntity> details = new java.util.ArrayList<>();
        for (OrderDetail req : order.getOrderDetailList()) {
            MenuEntity menuEntity = menuRepository.findById(req.getIdMenu())
                    .orElseThrow(() -> new MenuNotFoundException(ExceptionResponse.RESTAURANT_VALIDATION_NOT_FOUND.getMessage()));
            OrderDetailEntity detailEntity = new OrderDetailEntity();
            detailEntity.setOrderEntity(orderSaved);
            detailEntity.setMenuEntity(menuEntity);
            detailEntity.setAmount(req.getAmount());
            details.add(detailEntity);
        }

        orderDetailRepository.saveAll(details);

    }

    @Override
    public Optional<Order> findByCustomerAndStatusInvalid(Long idCustomer, List<Status> invalidStatusList) {
        Optional<OrderEntity> orderEntity = orderRepository.findByCustomerIdAndStatusIn(idCustomer, invalidStatusList);

        return Optional.ofNullable(orderEntityMapper.toOrder(orderEntity.orElse(null)));

    }

    @Override
    public PageResult<Order> getOrderByStatus(Long restaurantId, Status status, int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.ASC , sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OrderEntity> orderEntityListByStatus = orderRepository.findByRestaurantEntityIdAndStatus(restaurantId, status, pageable);

        List<Order> domainOrders = orderEntityListByStatus
                .getContent()
                .stream()
                .map(orderEntityMapper::toOrder)
                .toList();

        return new PageResult<>(
                domainOrders,
                orderEntityListByStatus.getNumber(),
                orderEntityListByStatus.getSize(),
                orderEntityListByStatus.getTotalElements(),
                orderEntityListByStatus.getTotalPages(),
                orderEntityListByStatus.isLast()
        );
    }

    @Override
    public void updateStatusOrder(Order order) {
        orderRepository.save(orderEntityMapper.toEntity(order));
    }

    @Override
    public Optional<Order> finByPin(String pin) {
        Optional<OrderEntity> orderEntity = orderRepository.findByPinContaining(pin);
        return orderEntity.map(orderEntityMapper::toOrder);
    }

    @Override
    public Optional<Order> finById(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        return orderEntity.map(orderEntityMapper::toOrder);
    }

}