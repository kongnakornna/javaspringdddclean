package com.icmon.module.inventory.infrastructure.mapper;

import com.icmon.module.inventory.domain.PartPickingRequest;
import com.icmon.module.inventory.domain.PartPickingDetail;
import com.icmon.module.inventory.infrastructure.entity.PartPickingRequestEntity;
import com.icmon.module.inventory.infrastructure.entity.PartPickingDetailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PickingMapper {
    PartPickingRequest toDomain(PartPickingRequestEntity entity);
    PartPickingRequestEntity toEntity(PartPickingRequest domain);
    PartPickingDetail toDetailDomain(PartPickingDetailEntity entity);
    PartPickingDetailEntity toDetailEntity(PartPickingDetail domain);
}
