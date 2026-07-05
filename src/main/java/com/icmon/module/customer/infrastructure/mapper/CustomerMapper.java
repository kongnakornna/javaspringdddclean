package com.icmon.module.customer.infrastructure.mapper;

import com.icmon.module.customer.domain.MCustomer;
import com.icmon.module.customer.infrastructure.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    MCustomer toDomain(CustomerEntity entity);
    CustomerEntity toEntity(MCustomer domain);
}
