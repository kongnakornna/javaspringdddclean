package com.icmon.module.customer.infrastructure.mapper;

import com.icmon.module.customer.domain.MCar;
import com.icmon.module.customer.infrastructure.entity.CarEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {
    MCar toDomain(CarEntity entity);
    CarEntity toEntity(MCar domain);
}
