package com.icmon.module.job.infrastructure.mapper;

import com.icmon.module.job.domain.TJob;
import com.icmon.module.job.infrastructure.entity.JobEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {
    TJob toDomain(JobEntity entity);
    JobEntity toEntity(TJob domain);
}
