package com.icmon.module.dashboard.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.dashboard.application.interfaces.WidgetService;
import com.icmon.module.dashboard.infrastructure.entity.DashboardWidgetEntity;
import com.icmon.module.dashboard.infrastructure.repository.WidgetConfigRepository;
import com.icmon.module.dashboard.presentation.dto.request.WidgetConfigRequestDTO;
import com.icmon.module.dashboard.presentation.dto.response.WidgetConfigResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WidgetServiceImpl extends GenericAuthDomainServiceImpl implements WidgetService {

    private final WidgetConfigRepository widgetConfigRepository;

    @Override
    public List<WidgetConfigResponseDTO> getUserWidgets(UUID userId) throws FailedRequestException {
        return widgetConfigRepository.findByUserIdAndIsActiveTrueOrderByPositionAsc(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public WidgetConfigResponseDTO saveWidget(WidgetConfigRequestDTO request) throws FailedRequestException {
        DashboardWidgetEntity entity = new DashboardWidgetEntity();
        entity.setUserId(request.getUserId());
        entity.setWidgetId(request.getWidgetId());
        entity.setWidgetType(request.getWidgetType());
        entity.setWidgetTitle(request.getWidgetTitle());
        entity.setPosition(request.getPosition());
        entity.setWidth(request.getWidth());
        entity.setHeight(request.getHeight());
        entity.setIsActive(true);
        entity = widgetConfigRepository.save(entity);
        return toResponse(entity);
    }

    @Override
    public void deleteWidget(UUID userId, String widgetId) throws FailedRequestException {
        widgetConfigRepository.deleteByUserIdAndWidgetId(userId, widgetId);
    }

    @Override
    public List<WidgetConfigResponseDTO> reorderWidgets(UUID userId, List<String> widgetIds) throws FailedRequestException {
        List<DashboardWidgetEntity> widgets = widgetConfigRepository.findByUserIdOrderByPositionAsc(userId);
        List<WidgetConfigResponseDTO> result = new ArrayList<>();
        for (int i = 0; i < widgetIds.size(); i++) {
            String wid = widgetIds.get(i);
            int pos = i;
            widgets.stream()
                    .filter(w -> w.getWidgetId().equals(wid))
                    .findFirst()
                    .ifPresent(w -> {
                        w.setPosition(pos);
                        widgetConfigRepository.save(w);
                    });
        }
        return getUserWidgets(userId);
    }

    private WidgetConfigResponseDTO toResponse(DashboardWidgetEntity entity) {
        WidgetConfigResponseDTO dto = new WidgetConfigResponseDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setWidgetId(entity.getWidgetId());
        dto.setWidgetType(entity.getWidgetType());
        dto.setWidgetTitle(entity.getWidgetTitle());
        dto.setPosition(entity.getPosition());
        dto.setWidth(entity.getWidth());
        dto.setHeight(entity.getHeight());
        dto.setIsActive(entity.getIsActive());
        return dto;
    }
}
