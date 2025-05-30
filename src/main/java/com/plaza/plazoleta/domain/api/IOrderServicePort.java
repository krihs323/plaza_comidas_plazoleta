package com.plaza.plazoleta.domain.api;

import com.plaza.plazoleta.domain.model.Order;

public interface IOrderServicePort {
    void saveOrder(Order order);
}
