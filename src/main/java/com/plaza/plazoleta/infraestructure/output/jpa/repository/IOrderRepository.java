package com.plaza.plazoleta.infraestructure.output.jpa.repository;

import com.plaza.plazoleta.domain.model.Status;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByCustomerIdAndStatusIn(Long idCustomer, List<Status> invalidStatusList);

    Page<OrderEntity> findByRestaurantEntityIdAndStatus(Long restaurantId, Status status, Pageable pageable);

    Optional<OrderEntity> findByPinContaining(String pin);
}
