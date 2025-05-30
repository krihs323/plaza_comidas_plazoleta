package com.plaza.plazoleta.infraestructure.output.jpa.mapper;

import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.Order;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.MenuEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderEntityMapper {

    @Mapping(source = "restaurant", target = "restaurantEntity")
    @Mapping(source = "customer.idUser", target = "customerId")
    OrderEntity toEntity(Order order);

    @Mapping(target = "restaurant", source = "restaurantEntity")
    @Mapping(target = "customer.idUser", source = "customerId")
    Order toOrder(OrderEntity orderEntity);

}
