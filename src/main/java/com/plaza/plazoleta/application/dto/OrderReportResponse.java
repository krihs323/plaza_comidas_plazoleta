package com.plaza.plazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReportResponse {

    private Integer orderId;
    private Integer customerId;
    private String status;
    private Integer employeeId;
    private Number time;

}


