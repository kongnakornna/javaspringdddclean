package com.icmon.module.dashboard.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.dashboard.application.interfaces.ExportService;
import com.icmon.module.dashboard.presentation.dto.request.ExportRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExportPDFUseCase {
    private final ExportService exportService;

    public byte[] execute(ExportRequestDTO request) throws SystemGlobalException {
        return exportService.exportToPDF(request);
    }
}
