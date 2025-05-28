package com.plaza.plazoleta.infraestructure.output.client.adapter;

import com.plaza.plazoleta.domain.model.User;
import com.plaza.plazoleta.infraestructure.output.client.entity.UserEntity;
import com.plaza.plazoleta.infraestructure.output.client.mapper.UserEntityMapper;
import com.plaza.plazoleta.infraestructure.output.client.repository.IUserFeignClient;
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

    @InjectMocks
    private UserClientAdapter userClientAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById() {

        User userMock = new User();
        userMock.setRol("ADMIN");
        userMock.setIdUser(1L);

        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(1L);
        userEntity.setRol("ADMIN");


        Mockito.when(userFeignClient.getById(anyLong(), anyString())).thenReturn(userEntity);
        Mockito.when(userEntityMapper.toUser(any())).thenReturn(userMock);

        User userExpected = userClientAdapter.getById(anyLong(), anyString());
        assertEquals(userExpected.getIdUser(), userMock.getIdUser());

    }

    @Test
    void getByEmail() {

        User userMock = new User();
        userMock.setRol("ADMIN");
        userMock.setIdUser(1L);

        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(1L);
        userEntity.setRol("ADMIN");


        Mockito.when(userFeignClient.getByEmail(anyString(), anyString())).thenReturn(userEntity);
        Mockito.when(userEntityMapper.toUser(any())).thenReturn(userMock);

        User userExpected = userClientAdapter.getByEmail(anyString(), anyString());
        assertEquals(userExpected.getIdUser(), userMock.getIdUser());

    }
}