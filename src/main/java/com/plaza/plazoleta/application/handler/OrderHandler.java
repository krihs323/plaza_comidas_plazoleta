package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.*;
import com.plaza.plazoleta.application.mapper.OrderRequestMapper;
import com.plaza.plazoleta.domain.api.IOrderServicePort;
import com.plaza.plazoleta.domain.model.Order;
import com.plaza.plazoleta.domain.model.PageResult;
import com.plaza.plazoleta.domain.validation.OrderValidations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final OrderRequestMapper orderRequestMapper;

    public OrderHandler(IOrderServicePort orderServicePort, OrderRequestMapper orderRequestMapper) {
        this.orderServicePort = orderServicePort;
        this.orderRequestMapper = orderRequestMapper;
    }

    @Override
    public void saveOrder(OrderRequest orderRequest) {
        Order order = orderRequestMapper.toOrder(orderRequest);
        orderServicePort.saveOrder(order);
    }

    @Override
    public PageResult<OrderResponse> getOrderByStatus(String status, int page, int size, String sortBy, String sortDir) {
        OrderValidations.getORderByStatus(status);
        PageResult<Order> orderStatusList = orderServicePort.getOrderByStatus(status, page, size, sortBy, sortDir);

        return orderRequestMapper.toPageResultResponse(orderStatusList);
    }

    @Override
    public void updateOrderToPreparation(Long id) {
        orderServicePort.updateOrderToPreparation(id);
    }

    @Override
    public void updateOrderToReady(Long id) {
        orderServicePort.updateOrderToReady(id);
    }

    @Override
    public void updateOrderToDelivered(OrderDeliverRequest orderDeliverRequest) {
        Order order = orderRequestMapper.toOrder(orderDeliverRequest);
        orderServicePort.updateOrderToDelivered(order);
    }

    @Override
    public void updateOrderToCanceled(Long id) {

        orderServicePort.updateOrderToCanceled(id);
    }


}
