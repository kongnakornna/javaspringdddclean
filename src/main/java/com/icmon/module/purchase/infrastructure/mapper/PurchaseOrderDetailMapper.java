package com.icmon.module.purchase.infrastructure.mapper;

import com.icmon.module.purchase.domain.TPurchaseOrderDetail;
import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderDetailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderDetailMapper {
    TPurchaseOrderDetail toDomain(PurchaseOrderDetailEntity entity);
    PurchaseOrderDetailEntity toEntity(TPurchaseOrderDetail domain);
}
