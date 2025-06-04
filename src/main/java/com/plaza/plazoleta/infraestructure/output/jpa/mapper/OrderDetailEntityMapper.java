package com.plaza.plazoleta.infraestructure.output.jpa.mapper;

import com.plaza.plazoleta.domain.model.Order;
import com.plaza.plazoleta.domain.model.OrderDetail;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderDetailEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderDetailEntityMapper {


    //OrderDetailEntity toOrderEntity(OrderDetail orderDetail);

    @Mapping(source = "menuEntity.id", target = "idMenu")
    OrderDetail toOrderDetail(OrderDetailEntity orderDetailEntity);

}
