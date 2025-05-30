package com.plaza.plazoleta.infraestructure.output.jpa.repository;

import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

}
