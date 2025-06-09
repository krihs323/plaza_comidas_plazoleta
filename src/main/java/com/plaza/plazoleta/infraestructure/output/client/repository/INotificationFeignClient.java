package com.plaza.plazoleta.infraestructure.output.client.repository;

import com.plaza.plazoleta.infraestructure.output.client.entity.MessageModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notificacion", url = "http://localhost:8083")
public interface INotificationFeignClient {

    @PostMapping("/api/notification/send/")
    void sendMessage(@RequestBody MessageModel messageNotification, @RequestHeader("Authorization") String authorizationHeader);

}
