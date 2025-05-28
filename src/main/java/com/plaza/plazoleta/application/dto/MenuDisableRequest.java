package com.plaza.plazoleta.application.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDisableRequest {

        @NotNull
        private Boolean active;

}
