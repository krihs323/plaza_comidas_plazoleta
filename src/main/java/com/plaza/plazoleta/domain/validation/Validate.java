package com.plaza.plazoleta.domain.validation;

public class Validate {

    public static boolean validate(Object value, String pattern){
        String valueString = "";
        if (value == null) {
            return false;
        }

        if (value instanceof Long) {
            valueString = String.valueOf(value);
        } else if (value instanceof String) {
            valueString = ((String) value).trim();
        }

        return valueString.matches(pattern);
    }

}
