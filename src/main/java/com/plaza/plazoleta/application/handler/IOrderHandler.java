package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.MenuRequest;
import com.plaza.plazoleta.application.dto.OrderDeliverRequest;
import com.plaza.plazoleta.application.dto.OrderRequest;
import com.plaza.plazoleta.application.dto.OrderResponse;
import com.plaza.plazoleta.domain.model.PageResult;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface IOrderHandler {

    void saveOrder(OrderRequest orderRequest);

    PageResult<OrderResponse> getOrderByStatus(String status, int page, int size, String sortBy, String sortDir);

    void updateOrderToPreparation(Long id);

    void updateOrderToReady(Long orderId);

    void updateOrderToDelivered(OrderDeliverRequest orderDeliverRequest);

    void updateOrderToCanceled(Long id);
}
