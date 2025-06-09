package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.Message;

public interface INotificationPersistencePort {
    void sendMessage(Message message);
}
