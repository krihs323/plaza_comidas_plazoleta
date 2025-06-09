package com.plaza.plazoleta.infraestructure.output.client.adapter;

import com.plaza.plazoleta.domain.model.User;
import com.plaza.plazoleta.infraestructure.output.client.entity.UserEntity;
import com.plaza.plazoleta.infraestructure.output.client.mapper.UserEntityMapper;
import com.plaza.plazoleta.infraestructure.output.client.repository.IUserFeignClient;
import com.plaza.plazoleta.infraestructure.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;


class UserClientAdapterTest {

    @Mock
    private IUserFeignClient userFeignClient;
    @Mock
    private UserEntityMapper userEntityMapper;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private JwtService jwtService;

    private User user;
    private UserEntity userEntity;

    @InjectMocks
    private UserClientAdapter userClientAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setRol("ADMIN");
        user.setIdUser(1L);

        userEntity = new UserEntity();
        userEntity.setIdUser(1L);
        userEntity.setRol("ADMIN");
    }

    @Test
    void getById() {



        Mockito.when(userFeignClient.getById(anyLong(), anyString())).thenReturn(userEntity);
        Mockito.when(userEntityMapper.toUser(any())).thenReturn(user);
        Mockito.when(httpServletRequest.getHeader(any())).thenReturn("Authorization");

        User userExpected = userClientAdapter.getById(anyLong());
        assertEquals(userExpected.getIdUser(), user.getIdUser());

    }

    @Test
    void getByEmail() {

        Mockito.when(userFeignClient.getByEmail(anyString(), anyString())).thenReturn(userEntity);
        Mockito.when(userEntityMapper.toUser(any())).thenReturn(user);

        User userExpected = userClientAdapter.getByEmail(anyString(), anyString());
        assertEquals(userExpected.getIdUser(), user.getIdUser());

    }

    @Test
    void getUserAuth() {
        Mockito.when(httpServletRequest.getHeader(any())).thenReturn("Authorization");
        Mockito.when(jwtService.extractUser(null)).thenReturn(user);

        User userResponse = userClientAdapter.getUseAuth();
        assertEquals(userResponse.getIdUser(), user.getIdUser());
    }

}