package com.icmon.module.quotation.infrastructure.mapper;

import com.icmon.module.quotation.domain.TQuotationService;
import com.icmon.module.quotation.infrastructure.entity.QuotationServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuotationServiceItemMapper {
    TQuotationService toDomain(QuotationServiceEntity entity);
    QuotationServiceEntity toEntity(TQuotationService domain);
}
