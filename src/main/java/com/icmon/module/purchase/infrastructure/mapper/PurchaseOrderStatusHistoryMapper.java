package com.icmon.module.purchase.infrastructure.mapper;

import com.icmon.module.purchase.domain.TPurchaseOrderStatusHistory;
import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderStatusHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderStatusHistoryMapper {
    TPurchaseOrderStatusHistory toDomain(PurchaseOrderStatusHistoryEntity entity);
    PurchaseOrderStatusHistoryEntity toEntity(TPurchaseOrderStatusHistory domain);
}
