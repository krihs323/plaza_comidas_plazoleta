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

        long orderDetail = order.getOrderDetailList().stream().filter(detail -> detail.getIdMenu()==null).count();
        if (orderDetail>0) {
            throw new OrderUserCaseValidationException(ExceptionResponse.ORDER_VALIATION_DETAIL_MENU_IS_NULL.getMessage());
        }

        long totalAmount = order.getOrderDetailList().stream().filter(detail -> detail.getAmount()==null).count();
        if (totalAmount>0) {
            throw new OrderUserCaseValidationException(ExceptionResponse.ORDER_VALIATION_DETAIL_MENU_IS_NULL.getMessage());
        }
    }

    public static void getORderByStatus(String status) {
        if (status.isEmpty()) {
            throw new OrderUserCaseValidationException(ExceptionResponse.ORDER_LIST_STATUS_IS_EMPTY.getMessage());
        }
    }
}
