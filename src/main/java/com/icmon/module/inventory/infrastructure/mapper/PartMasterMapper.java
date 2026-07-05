package com.icmon.module.inventory.infrastructure.mapper;

import com.icmon.module.inventory.domain.PartMaster;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartMasterMapper {
    PartMaster toDomain(PartMasterEntity entity);
    PartMasterEntity toEntity(PartMaster domain);
}
