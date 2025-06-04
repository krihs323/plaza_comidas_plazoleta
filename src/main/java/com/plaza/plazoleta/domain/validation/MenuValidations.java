package com.plaza.plazoleta.domain.validation;

import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.domain.exception.MenuUserCaseValidationException;
import com.plaza.plazoleta.domain.model.Menu;

public class MenuValidations extends Validate {

    public static void saveMenu(Menu menu) {
        if (!validate(menu.getName(), ConstantValidation.PATTERN_NAME.getValue())) {
            throw new MenuUserCaseValidationException(ExceptionResponse.MENU_VALIATION_NAME.getMessage());
        }

        if (!validate(menu.getPrice(), ConstantValidation.PATTERN_NUMBER.getValue())) {
            throw new MenuUserCaseValidationException(ExceptionResponse.VALIDATION_PRICE.getMessage());
        }

        if (menu.getPrice()<0) {
            throw new MenuUserCaseValidationException(ExceptionResponse.VALIDATION_PRICE_POSITIVE.getMessage());
        }

        if (menu.getUrlLogo() == null || menu.getUrlLogo().isEmpty()) {
            throw new MenuUserCaseValidationException(ExceptionResponse.VALIDATION_LOGO.getMessage());
        }

        if (!validate(menu.getDescription(), ConstantValidation.PATTERN_DESCRIPTION.getValue())) {
            throw new MenuUserCaseValidationException(ExceptionResponse.VALIDATION_DESCRIPTION.getMessage());
        }

        if (menu.getCategory() == null) {
            throw new MenuUserCaseValidationException(ExceptionResponse.VALIDATION_CATEGORY.getMessage());
        }

        if (menu.getRestaurant() == null) {
            throw new MenuUserCaseValidationException(ExceptionResponse.VALIDATION_RESTAURANT.getMessage());
        }


    }

    public static void updateMenu(Menu menu) {
        if (!validate(menu.getPrice(), ConstantValidation.PATTERN_NUMBER.getValue())) {
            throw new MenuUserCaseValidationException(ExceptionResponse.VALIDATION_PRICE.getMessage());
        }

        if (menu.getPrice()<0) {
            throw new MenuUserCaseValidationException(ExceptionResponse.VALIDATION_PRICE_POSITIVE.getMessage());
        }

        if (!validate(menu.getDescription(), ConstantValidation.PATTERN_DESCRIPTION.getValue())) {
            throw new MenuUserCaseValidationException(ExceptionResponse.VALIDATION_DESCRIPTION.getMessage());
        }

    }




}
