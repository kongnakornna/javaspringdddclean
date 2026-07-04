package com.icmon.module.job.infrastructure.mapper;

import com.icmon.module.job.domain.TJobPartSales;
import com.icmon.module.job.infrastructure.entity.JobPartSalesEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobPartSalesMapper {
    TJobPartSales toDomain(JobPartSalesEntity entity);
    JobPartSalesEntity toEntity(TJobPartSales domain);
}
