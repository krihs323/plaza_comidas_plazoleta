package com.plaza.plazoleta.domain.constants;

public enum ConstantNumber {

    VALIDATION_POSITIVE(1);

    private final Integer value;

    ConstantNumber(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
