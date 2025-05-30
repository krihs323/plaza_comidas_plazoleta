package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.OrderRequest;
import com.plaza.plazoleta.application.mapper.OrderRequestMapper;
import com.plaza.plazoleta.domain.api.IOrderServicePort;
import com.plaza.plazoleta.domain.model.Order;
import com.plaza.plazoleta.domain.model.OrderDetail;
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
        System.out.println("order.toString() = " + order.toString());

        orderServicePort.saveOrder(order);
    }

}
