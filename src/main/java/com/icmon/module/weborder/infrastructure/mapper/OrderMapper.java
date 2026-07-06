package com.icmon.module.weborder.infrastructure.mapper;

import com.icmon.module.weborder.domain.TWebOrder;
import com.icmon.module.weborder.infrastructure.entity.WebOrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    TWebOrder toDomain(WebOrderEntity entity);

    WebOrderEntity toEntity(TWebOrder domain);
}
