package com.icmon.module.weborder.infrastructure.mapper;

import com.icmon.module.weborder.domain.TShoppingCart;
import com.icmon.module.weborder.infrastructure.entity.ShoppingCartEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    TShoppingCart toDomain(ShoppingCartEntity entity);

    ShoppingCartEntity toEntity(TShoppingCart domain);
}
