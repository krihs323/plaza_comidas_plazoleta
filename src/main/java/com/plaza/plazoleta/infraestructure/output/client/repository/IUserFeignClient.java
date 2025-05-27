package com.plaza.plazoleta.infraestructure.output.client.repository;

import com.plaza.plazoleta.infraestructure.output.client.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "usuarios", url = "http://localhost:8080")
public interface IUserFeignClient {

    @GetMapping("/api/user/owner/rol/{id}")
    UserEntity getById(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorizationHeader);
}
