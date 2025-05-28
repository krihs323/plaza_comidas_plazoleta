package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.User;

public interface IUserPersistencePort {

    User getById(Long id, String authentizationHeader);

    User getByEmail(String mail, String authorizationHeader);
}
