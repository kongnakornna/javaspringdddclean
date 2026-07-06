package com.icmon.module.inventory.infrastructure.mapper;

import com.icmon.module.inventory.domain.MPartMaster;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartMasterMapper {
    MPartMaster toDomain(PartMasterEntity entity);
    PartMasterEntity toEntity(MPartMaster domain);
}
