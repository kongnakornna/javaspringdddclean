package com.icmon.module.job.infrastructure.mapper;

import com.icmon.module.job.domain.TJobService;
import com.icmon.module.job.infrastructure.entity.JobServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobServiceMapper {
    TJobService toDomain(JobServiceEntity entity);
    JobServiceEntity toEntity(TJobService domain);
}
