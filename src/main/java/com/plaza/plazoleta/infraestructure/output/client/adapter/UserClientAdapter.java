package com.plaza.plazoleta.infraestructure.output.client.adapter;

import com.plaza.plazoleta.domain.model.User;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.infraestructure.output.client.mapper.UserEntityMapper;
import com.plaza.plazoleta.infraestructure.output.client.repository.IUserFeignClient;
import com.plaza.plazoleta.infraestructure.security.JwtService;
import lombok.RequiredArgsConstructor;


public class UserClientAdapter implements IUserPersistencePort {

    private final IUserFeignClient userFeignClient;
    private final UserEntityMapper userEntityMapper;

    public UserClientAdapter(IUserFeignClient userFeignClient, UserEntityMapper userEntityMapper) {
        this.userFeignClient = userFeignClient;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public User getById(Long id, String authentizationHeader) {

        return userEntityMapper.toUser(userFeignClient.getById(id, authentizationHeader));

    }
}
