package com.icmon.module.job.application.interfaces;

import com.icmon.module.job.presentation.dto.request.JobPartRequestDTO;
import com.icmon.module.job.presentation.dto.response.JobPartResponseDTO;
import com.icmon.exception.SystemGlobalException;

import java.util.List;
import java.util.UUID;

public interface JobPartSaleService {
    JobPartResponseDTO addPart(UUID jobId, JobPartRequestDTO request) throws SystemGlobalException;
    List<JobPartResponseDTO> getPartsByJobId(UUID jobId) throws SystemGlobalException;
    void removePart(UUID partSaleId) throws SystemGlobalException;
}
