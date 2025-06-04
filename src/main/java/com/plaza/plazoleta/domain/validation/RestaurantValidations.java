package com.plaza.plazoleta.domain.validation;

import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.domain.exception.RestaurantUserCaseValidationException;
import com.plaza.plazoleta.domain.model.Restaurant;

public class RestaurantValidations extends Validate {

    public static void saveRestaurant(Restaurant restaurant) {

        if (!validate(restaurant.getName(), ConstantValidation.PATTERN_NAME.getValue())) {
            throw new RestaurantUserCaseValidationException(ExceptionResponse.RESTAURANT_VALIATION_NAME.getMessage());
        }

        if (!validate(restaurant.getNumberId(), ConstantValidation.PATTERN_NUMBER.getValue())) {
            throw new RestaurantUserCaseValidationException(ExceptionResponse.VALIDATION_NUMBER_ID.getMessage());
        }

        if (restaurant.getAddress() == null || restaurant.getAddress().isEmpty()) {
            throw new RestaurantUserCaseValidationException(ExceptionResponse.VALIDATION_ADRESS.getMessage());
        }

        if (!validate(restaurant.getPhoneNumber(), ConstantValidation.PATTERN_TELEPHONE.getValue())) {
            throw new RestaurantUserCaseValidationException(ExceptionResponse.VALIDATION_PHONE_NUMBER.getMessage());
        }

        if (restaurant.getUrlLogo() == null || restaurant.getUrlLogo().isEmpty()) {
            throw new RestaurantUserCaseValidationException(ExceptionResponse.VALIDATION_LOGO.getMessage());
        }

        if (restaurant.getUserId() == null || restaurant.getUserId()==0) {
            throw new RestaurantUserCaseValidationException(ExceptionResponse.VALIDATION_USER_ID.getMessage());
        }

    }


}
