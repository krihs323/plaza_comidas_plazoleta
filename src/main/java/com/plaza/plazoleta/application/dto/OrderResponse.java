package com.plaza.plazoleta.application.dto;

import com.plaza.plazoleta.domain.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private Long id;

    private Long customerId;

    private Long restaurantId;

    private Status status;

    private List<OrderDetailRequest> detailListRequest;
}


