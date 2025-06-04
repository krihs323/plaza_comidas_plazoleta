package com.plaza.plazoleta.infraestructure.output.jpa.mapper;

import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.Order;
import com.plaza.plazoleta.domain.model.OrderDetail;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.MenuEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderDetailEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderEntityMapper {

    @Mapping(source = "restaurant", target = "restaurantEntity")
    @Mapping(source = "customer.idUser", target = "customerId")
    OrderEntity toEntity(Order order);

    @Mapping(source = "restaurantEntity", target = "restaurant")
    @Mapping(source = "customerId", target = "customer.idUser")
    @Mapping(source = "status", target = "status")
    //@Mapping(source = "details", target = "orderDetailList")
    @Mapping(target = "orderDetailList", source = "details", qualifiedByName = "detalle")
    Order toOrder(OrderEntity orderDetailList);

    @Named("detalle")
    default  List<OrderDetail> detalle (List<OrderDetailEntity> orderDetailEntityList) {
        return orderDetailEntityList.stream().map(orderDetailEntity -> {
            OrderDetail orderDetail = new OrderDetail(orderDetailEntity.getMenuEntity().getId(), orderDetailEntity.getAmount());
            return orderDetail;
        }).toList();
    }

}
