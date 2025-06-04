package com.plaza.plazoleta.domain.spi;


import com.plaza.plazoleta.domain.model.Traceability;

public interface ITraceabilityPersistencePort {

    Boolean insertTraceability(Traceability traceability);
}
