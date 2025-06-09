package com.plaza.plazoleta.domain.api;

import com.plaza.plazoleta.domain.model.Order;
import com.plaza.plazoleta.domain.model.PageResult;


public interface IOrderServicePort {
    void saveOrder(Order order);

    PageResult<Order> getOrderByStatus(String status, int page, int size, String sortBy, String sortDir);

    void updateOrderToPreparation(Long id);

    void updateOrderToReady(Long id);

    void updateOrderToDelivered(Order order);

    void updateOrderToCanceled(Long id);

}
