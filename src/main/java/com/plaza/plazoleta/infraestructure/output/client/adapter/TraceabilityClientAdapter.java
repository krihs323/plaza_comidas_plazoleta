package com.plaza.plazoleta.infraestructure.output.client.adapter;

import com.plaza.plazoleta.domain.model.Traceability;
import com.plaza.plazoleta.domain.spi.ITraceabilityPersistencePort;
import com.plaza.plazoleta.infraestructure.output.client.mapper.TraceabilityEntityMapper;
import com.plaza.plazoleta.infraestructure.output.client.repository.ITraceabilityFeignClient;
import jakarta.servlet.http.HttpServletRequest;

public class TraceabilityClientAdapter implements ITraceabilityPersistencePort {

    private final ITraceabilityFeignClient traceabilityFeignClient;
    private final TraceabilityEntityMapper traceabilityEntityMapper;
    private final HttpServletRequest httpServletRequest;

    public TraceabilityClientAdapter(ITraceabilityFeignClient traceabilityFeignClient, TraceabilityEntityMapper traceabilityEntityMapper, HttpServletRequest httpServletRequest) {
        this.traceabilityFeignClient = traceabilityFeignClient;
        this.traceabilityEntityMapper = traceabilityEntityMapper;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public Boolean insertTraceability(Traceability traceability) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        traceabilityFeignClient.saveTraceability(traceabilityEntityMapper.toTrceabilityEntity(traceability), authorizationHeader);
        return true;
    }
}
