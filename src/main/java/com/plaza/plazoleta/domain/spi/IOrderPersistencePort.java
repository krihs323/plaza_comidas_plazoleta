package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.Order;
import com.plaza.plazoleta.domain.model.OrderReport;
import com.plaza.plazoleta.domain.model.PageResult;
import com.plaza.plazoleta.domain.model.Status;

import java.util.List;
import java.util.Optional;

public interface IOrderPersistencePort {
    void saveOrder(Order order);

    Optional<Order> findByCustomerAndStatusInvalid(Long idCustomer, List<Status> invalidStatusList);

    PageResult<Order> getOrderByStatus(Long restaurantId, Status status, int page, int size, String sortBy, String sortDir);

    Optional<Order> finById(Long id);

    void updateStatusOrder(Order order);

    Optional<Order> finByPin(String pin);

}
