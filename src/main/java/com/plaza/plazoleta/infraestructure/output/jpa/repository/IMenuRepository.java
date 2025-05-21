package com.plaza.plazoleta.infraestructure.output.jpa.repository;

import com.plaza.plazoleta.infraestructure.output.jpa.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMenuRepository extends JpaRepository<MenuEntity, Long> {

}
