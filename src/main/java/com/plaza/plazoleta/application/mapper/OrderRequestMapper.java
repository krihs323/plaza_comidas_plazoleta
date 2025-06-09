package com.plaza.plazoleta.application.mapper;

import com.plaza.plazoleta.application.dto.*;
import com.plaza.plazoleta.domain.model.Order;
import com.plaza.plazoleta.domain.model.OrderReport;
import com.plaza.plazoleta.domain.model.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderRequestMapper {

    @Mapping(source = "restaurantId", target = "restaurant.id")
    @Mapping(source = "detailListRequest", target = "orderDetailList")
    Order toOrder(OrderRequest orderRequest);

    @Mapping(source = "customer.idUser", target = "customerId")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "orderDetailList", target = "detailListRequest")
    OrderResponse toOrderResponse(Order order);

    Order toOrder(OrderDeliverRequest orderDeliverRequest);

    PageResult<OrderResponse> toPageResultResponse(PageResult<Order> pageResult);

    List<OrderReportEmployeeResponse> toOrderReportEmployeeResponse(List<OrderReport> orderReportList);

    List<OrderReportResponse> toOrderReportResponse(List<OrderReport> orderReportList);
}
