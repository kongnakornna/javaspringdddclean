package com.icmon.module.payment.infrastructure.mapper;

import com.icmon.module.payment.domain.TPayment;
import com.icmon.module.payment.infrastructure.entity.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    TPayment toDomain(PaymentEntity entity);
    PaymentEntity toEntity(TPayment domain);
}
