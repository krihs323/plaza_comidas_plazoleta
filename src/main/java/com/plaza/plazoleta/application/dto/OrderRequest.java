package com.plaza.plazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

        private Long restaurantId;

        private List<OrderDetailRequest> detailListRequest;
}
