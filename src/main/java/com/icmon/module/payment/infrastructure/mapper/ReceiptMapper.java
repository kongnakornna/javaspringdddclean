package com.icmon.module.payment.infrastructure.mapper;

import com.icmon.module.payment.domain.TReceipt;
import com.icmon.module.payment.infrastructure.entity.ReceiptEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReceiptMapper {
    TReceipt toDomain(ReceiptEntity entity);
    ReceiptEntity toEntity(TReceipt domain);
}
