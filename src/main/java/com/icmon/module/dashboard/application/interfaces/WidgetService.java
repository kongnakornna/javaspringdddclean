package com.icmon.module.dashboard.application.interfaces;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.dashboard.presentation.dto.request.WidgetConfigRequestDTO;
import com.icmon.module.dashboard.presentation.dto.response.WidgetConfigResponseDTO;

import java.util.List;
import java.util.UUID;

public interface WidgetService {
    List<WidgetConfigResponseDTO> getUserWidgets(UUID userId) throws FailedRequestException;
    WidgetConfigResponseDTO saveWidget(WidgetConfigRequestDTO request) throws FailedRequestException;
    void deleteWidget(UUID userId, String widgetId) throws FailedRequestException;
    List<WidgetConfigResponseDTO> reorderWidgets(UUID userId, List<String> widgetIds) throws FailedRequestException;
}
