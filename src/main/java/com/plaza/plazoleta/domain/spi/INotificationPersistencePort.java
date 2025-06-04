package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.Message;
import com.plaza.plazoleta.domain.model.User;

public interface INotificationPersistencePort {


    void sendMessage(Message message);
}
