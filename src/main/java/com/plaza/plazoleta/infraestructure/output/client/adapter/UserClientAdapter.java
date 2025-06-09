package com.plaza.plazoleta.infraestructure.output.client.adapter;

import com.plaza.plazoleta.domain.model.User;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.infraestructure.output.client.mapper.UserEntityMapper;
import com.plaza.plazoleta.infraestructure.output.client.repository.IUserFeignClient;
import com.plaza.plazoleta.infraestructure.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;

public class UserClientAdapter implements IUserPersistencePort {

    public static final String AUTHORIZATION = "Authorization";
    private final IUserFeignClient userFeignClient;
    private final UserEntityMapper userEntityMapper;

    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;

    public UserClientAdapter(IUserFeignClient userFeignClient, UserEntityMapper userEntityMapper, HttpServletRequest httpServletRequest, JwtService jwtService) {
        this.userFeignClient = userFeignClient;
        this.userEntityMapper = userEntityMapper;
        this.httpServletRequest = httpServletRequest;
        this.jwtService = jwtService;
    }

    @Override
    public User getById(Long id) {
        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);

        return userEntityMapper.toUser(userFeignClient.getById(id, authorizationHeader));

    }

    @Override
    public User getByEmail(String mail, String authentizationHeader) {

        return userEntityMapper.toUser(userFeignClient.getByEmail(mail, authentizationHeader));
    }

    @Override
    public User getUseAuth() {

        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
        String jwtAuthorizationHeader = null;
        if (authorizationHeader.startsWith("Bearer ")) {
            jwtAuthorizationHeader = authorizationHeader.substring(7);
        }
        return jwtService.extractUser(jwtAuthorizationHeader);
    }

    @Override
    public void updateUserRestaurantAsOwner(Long userId, HashMap<String, Long> restaurantEmployee) {
        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
        userFeignClient.updateUserRestaurant(userId, restaurantEmployee, authorizationHeader);

    }
}
