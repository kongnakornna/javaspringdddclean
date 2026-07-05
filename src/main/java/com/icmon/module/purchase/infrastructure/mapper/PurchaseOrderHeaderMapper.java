package com.icmon.module.purchase.infrastructure.mapper;

import com.icmon.module.purchase.domain.TPurchaseOrderHeader;
import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderHeaderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderHeaderMapper {
    TPurchaseOrderHeader toDomain(PurchaseOrderHeaderEntity entity);
    PurchaseOrderHeaderEntity toEntity(TPurchaseOrderHeader domain);
}
