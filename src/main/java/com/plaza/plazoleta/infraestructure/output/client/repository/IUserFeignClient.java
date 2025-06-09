package com.plaza.plazoleta.infraestructure.output.client.repository;

import com.plaza.plazoleta.infraestructure.output.client.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@FeignClient(name = "usuarios", url = "http://localhost:8080")
public interface IUserFeignClient {

    @GetMapping("/api/user/rol/id/{id}")
    UserEntity getById(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorizationHeader);

    @GetMapping("/api/user/rol/email/{email}")
    UserEntity getByEmail(@PathVariable("email") String email, @RequestHeader("Authorization") String authorizationHeader);

    @PutMapping("/api/user/owner/updateRestaurant/{userId}")
    void updateUserRestaurant(@PathVariable("userId") Long userId, @RequestBody HashMap<String, Long> restaurantEmployee, @RequestHeader("Authorization") String authorizationHeader);
}
