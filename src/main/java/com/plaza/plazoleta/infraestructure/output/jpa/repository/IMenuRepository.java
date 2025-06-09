package com.plaza.plazoleta.infraestructure.output.jpa.repository;

import com.plaza.plazoleta.infraestructure.output.jpa.entity.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMenuRepository extends JpaRepository<MenuEntity, Long> {

    Page<MenuEntity> findByRestaurantEntityIdAndActive(Long restaurantId, Boolean status, Pageable pageable);

    Page<MenuEntity> findByRestaurantEntityIdAndCategoryEntityIdAndActive(Long restaurantId, Long categoryId, Boolean status, Pageable pageable);
}
