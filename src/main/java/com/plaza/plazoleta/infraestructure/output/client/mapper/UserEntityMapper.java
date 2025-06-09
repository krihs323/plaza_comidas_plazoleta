package com.plaza.plazoleta.infraestructure.output.client.mapper;

import com.plaza.plazoleta.domain.model.User;
import com.plaza.plazoleta.infraestructure.output.client.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    UserEntity toEntity(User user);

    User toUser(UserEntity userEntity);
}
