package com.plaza.plazoleta.application.mapper;

import com.plaza.plazoleta.application.dto.MenuRequest;
import com.plaza.plazoleta.domain.model.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MenuRequestMapper {

    Menu toMenu(MenuRequest menuRequest);

}
