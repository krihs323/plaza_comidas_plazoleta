package com.plaza.plazoleta.infraestructure.output.jpa.repository;

import com.plaza.plazoleta.infraestructure.output.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

}
