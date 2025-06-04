package com.plaza.plazoleta.infraestructure.output.client.adapter;

import com.plaza.plazoleta.domain.model.Message;
import com.plaza.plazoleta.domain.spi.INotificationPersistencePort;
import com.plaza.plazoleta.infraestructure.output.client.mapper.MessageEntityMapper;
import com.plaza.plazoleta.infraestructure.output.client.repository.INotificationFeignClient;
import jakarta.servlet.http.HttpServletRequest;

public class NotificationClientAdapter implements INotificationPersistencePort {

    private final INotificationFeignClient notificationFeignClient;
    private final MessageEntityMapper messageEntityMapper;
    private final HttpServletRequest httpServletRequest;

    public NotificationClientAdapter(INotificationFeignClient notificationFeignClient, MessageEntityMapper messageEntityMapper, HttpServletRequest httpServletRequest) {
        this.notificationFeignClient = notificationFeignClient;
        this.messageEntityMapper = messageEntityMapper;
        this.httpServletRequest = httpServletRequest;
    }


    @Override
    public void sendMessage(Message message) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        notificationFeignClient.sendMessage(messageEntityMapper.toMessageEntity(message), authorizationHeader);

    }
}
