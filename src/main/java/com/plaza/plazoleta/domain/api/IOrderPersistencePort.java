package com.plaza.plazoleta.domain.api;

import com.plaza.plazoleta.domain.model.Order;
import com.plaza.plazoleta.domain.model.Status;

import java.util.List;
import java.util.Optional;

public interface IOrderPersistencePort {
    void saveOrder(Order order);

    Optional<Order> findByCustomerAndStatusInvalid(Long idCustomer, List<Status> invalidStatusList);
}
