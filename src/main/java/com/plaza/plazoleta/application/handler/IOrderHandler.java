package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.*;
import com.plaza.plazoleta.domain.model.PageResult;

public interface IOrderHandler {

    void saveOrder(OrderRequest orderRequest);

    PageResult<OrderResponse> getOrderByStatus(String status, int page, int size, String sortBy, String sortDir);

    void updateOrderToPreparation(Long id);

    void updateOrderToReady(Long orderId);

    void updateOrderToDelivered(OrderDeliverRequest orderDeliverRequest);

    void updateOrderToCanceled(Long id);


}
