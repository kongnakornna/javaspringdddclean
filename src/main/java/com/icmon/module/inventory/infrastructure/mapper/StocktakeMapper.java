package com.icmon.module.inventory.infrastructure.mapper;

import com.icmon.module.inventory.domain.StocktakeHeader;
import com.icmon.module.inventory.domain.StocktakeDetail;
import com.icmon.module.inventory.infrastructure.entity.StocktakeHeaderEntity;
import com.icmon.module.inventory.infrastructure.entity.StocktakeDetailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StocktakeMapper {
    StocktakeHeader toDomain(StocktakeHeaderEntity entity);
    StocktakeHeaderEntity toEntity(StocktakeHeader domain);
    StocktakeDetail toDetailDomain(StocktakeDetailEntity entity);
    StocktakeDetailEntity toDetailEntity(StocktakeDetail domain);
}
