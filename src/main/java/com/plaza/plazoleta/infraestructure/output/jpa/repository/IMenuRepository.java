package com.plaza.plazoleta.infraestructure.output.jpa.repository;

import com.plaza.plazoleta.infraestructure.output.jpa.entity.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMenuRepository extends JpaRepository<MenuEntity, Long> {

    Page<MenuEntity> findByRestaurantEntityId(Long restaurantId, Pageable pageable);

    Page<MenuEntity> findByRestaurantEntityIdAndCategoryEntityId(Long restaurantId, Long categoryId, Pageable pageable);
}
