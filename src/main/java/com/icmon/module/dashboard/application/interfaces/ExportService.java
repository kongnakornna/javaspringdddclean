package com.icmon.module.dashboard.application.interfaces;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.dashboard.presentation.dto.request.ExportRequestDTO;

public interface ExportService {
    byte[] exportToExcel(ExportRequestDTO request) throws FailedRequestException;
    byte[] exportToPDF(ExportRequestDTO request) throws FailedRequestException;
    byte[] exportToCSV(ExportRequestDTO request) throws FailedRequestException;
}
