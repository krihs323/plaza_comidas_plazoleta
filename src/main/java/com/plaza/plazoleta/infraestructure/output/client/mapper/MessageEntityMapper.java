package com.plaza.plazoleta.infraestructure.output.client.mapper;

import com.plaza.plazoleta.domain.model.Message;
import com.plaza.plazoleta.infraestructure.output.client.entity.MessageModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MessageEntityMapper {

    MessageModel toMessageEntity(Message message);
}
