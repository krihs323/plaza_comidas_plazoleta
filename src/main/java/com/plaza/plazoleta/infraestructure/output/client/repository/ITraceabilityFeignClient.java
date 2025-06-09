package com.plaza.plazoleta.infraestructure.output.client.repository;

import com.plaza.plazoleta.infraestructure.output.client.entity.TraceabilityEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "trazabilidad", url = "http://localhost:8084")
public interface ITraceabilityFeignClient {

    @PostMapping("/api/trazabilidad/create/")
    void saveTraceability(@RequestBody TraceabilityEntity traceabilityEntity, @RequestHeader("Authorization") String authorizationHeader);

}
