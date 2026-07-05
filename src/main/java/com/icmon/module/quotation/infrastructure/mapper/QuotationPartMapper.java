package com.icmon.module.quotation.infrastructure.mapper;

import com.icmon.module.quotation.domain.TQuotationPart;
import com.icmon.module.quotation.infrastructure.entity.QuotationPartEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuotationPartMapper {
    TQuotationPart toDomain(QuotationPartEntity entity);
    QuotationPartEntity toEntity(TQuotationPart domain);
}
