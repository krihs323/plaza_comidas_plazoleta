package com.plaza.plazoleta.application.mapper;

import com.plaza.plazoleta.application.dto.OrderRequest;
import com.plaza.plazoleta.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderRequestMapper {

    @Mapping(source = "restaurantId", target = "restaurant.id")
    @Mapping(source = "detailListRequest", target = "orderDetailList")
    Order toOrder(OrderRequest orderRequest);

//    @Mapping(target = "restaurantId", source = "restaurant.id")
//    @Mapping(target = "detailListRequest", source = "orderDetailList")
//    OrderRequest toOrderRequest(Order order);
}
