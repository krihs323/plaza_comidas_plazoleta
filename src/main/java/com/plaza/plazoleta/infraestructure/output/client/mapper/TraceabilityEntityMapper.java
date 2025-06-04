package com.plaza.plazoleta.infraestructure.output.client.mapper;

import com.plaza.plazoleta.domain.model.Traceability;
import com.plaza.plazoleta.infraestructure.output.client.entity.TraceabilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TraceabilityEntityMapper {

    Traceability toTraeability(TraceabilityEntityMapper traceabilityEntityMapper);

    TraceabilityEntity toTrceabilityEntity(Traceability traceability);
}
