package com.icmon.module.quotation.infrastructure.mapper;

import com.icmon.module.quotation.domain.TQuotation;
import com.icmon.module.quotation.infrastructure.entity.QuotationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuotationMapper {
    TQuotation toDomain(QuotationEntity entity);
    QuotationEntity toEntity(TQuotation domain);
}
