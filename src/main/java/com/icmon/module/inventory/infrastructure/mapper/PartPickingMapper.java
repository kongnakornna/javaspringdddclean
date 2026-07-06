package com.icmon.module.inventory.infrastructure.mapper;

import com.icmon.module.inventory.domain.TPartPickingRequest;
import com.icmon.module.inventory.infrastructure.entity.PartPickingRequestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartPickingMapper {
    TPartPickingRequest toDomain(PartPickingRequestEntity entity);
    PartPickingRequestEntity toEntity(TPartPickingRequest domain);
}
