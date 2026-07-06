package com.icmon.module.weborder.infrastructure.mapper;

import com.icmon.module.weborder.domain.MSalesPrice;
import com.icmon.module.weborder.infrastructure.entity.SalesPriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalesPriceMapper {
    MSalesPrice toDomain(SalesPriceEntity entity);

    SalesPriceEntity toEntity(MSalesPrice domain);
}
