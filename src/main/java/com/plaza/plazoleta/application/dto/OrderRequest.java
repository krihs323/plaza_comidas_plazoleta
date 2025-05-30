package com.plaza.plazoleta.application.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

        @NotNull
        private Long restaurantId;

        @NotNull
        private List<OrderDetailRequest> detailListRequest;
}
