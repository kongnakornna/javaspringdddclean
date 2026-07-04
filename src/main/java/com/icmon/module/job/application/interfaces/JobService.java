package com.icmon.module.job.application.interfaces;

import com.icmon.module.job.presentation.dto.request.JobCreateRequestDTO;
import com.icmon.module.job.presentation.dto.request.JobPartRequestDTO;
import com.icmon.module.job.presentation.dto.request.JobServiceRequestDTO;
import com.icmon.module.job.presentation.dto.request.JobStatusChangeRequestDTO;
import com.icmon.module.job.presentation.dto.request.JobUpdateRequestDTO;
import com.icmon.module.job.presentation.dto.response.JobDetailResponseDTO;
import com.icmon.module.job.presentation.dto.response.JobPartResponseDTO;
import com.icmon.module.job.presentation.dto.response.JobResponseDTO;
import com.icmon.module.job.presentation.dto.response.JobServiceResponseDTO;
import com.icmon.module.job.presentation.dto.response.JobStatusHistoryDTO;
import com.icmon.exception.SystemGlobalException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface JobService {
    JobResponseDTO createJob(JobCreateRequestDTO request) throws SystemGlobalException;
    JobResponseDTO getJob(UUID id) throws SystemGlobalException;
    JobDetailResponseDTO getJobDetail(UUID id) throws SystemGlobalException;
    Page<JobResponseDTO> listJobs(String status, String startDate, String endDate, Pageable pageable) throws SystemGlobalException;
    JobResponseDTO updateJob(UUID id, JobUpdateRequestDTO request) throws SystemGlobalException;
    JobResponseDTO changeStatus(UUID id, JobStatusChangeRequestDTO request) throws SystemGlobalException;
    void deleteJob(UUID id) throws SystemGlobalException;
    byte[] generateJobReport(UUID id) throws SystemGlobalException;
    List<JobStatusHistoryDTO> getStatusHistory(UUID id) throws SystemGlobalException;
    JobServiceResponseDTO addService(UUID jobId, JobServiceRequestDTO request) throws SystemGlobalException;
    JobPartResponseDTO addPart(UUID jobId, JobPartRequestDTO request) throws SystemGlobalException;
}
