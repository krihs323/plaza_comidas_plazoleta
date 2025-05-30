package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.MenuRequest;
import com.plaza.plazoleta.application.dto.OrderRequest;

public interface IOrderHandler {

    void saveOrder(OrderRequest orderRequest);
}
