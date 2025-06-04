package com.plaza.plazoleta.domain.validation;

import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.domain.exception.OrderUserCaseValidationException;
import com.plaza.plazoleta.domain.model.Order;

public class OrderValidations extends Validate {

    public static void saveOrder(Order order) {
        if (order.getRestaurant()==null) {
            throw new OrderUserCaseValidationException(ExceptionResponse.ORDER_VALIATION_RESTAURANT.getMessage());
        }

        if (order.getOrderDetailList()==null) {
            throw new OrderUserCaseValidationException(ExceptionResponse.ORDER_VALIATION_DETAIL.getMessage());
        }

    }

}
