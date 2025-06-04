package com.plaza.plazoleta.domain.validation;

public enum ConstantValidation {

    PATTERN_NAME("^(?!\\d+$)[a-zA-ZñÑáÁéÉíÍóÓúÚ\\d ]+$"),
    PATTERN_NUMBER("\\d{1,15}"),
    PATTERN_TELEPHONE("^\\+?\\d{1,13}$"),
    PATTERN_DESCRIPTION("^[a-zA-ZñÑáÁéÉíÍóÓúÚ\\d ]+$");

    private String value;

    ConstantValidation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
