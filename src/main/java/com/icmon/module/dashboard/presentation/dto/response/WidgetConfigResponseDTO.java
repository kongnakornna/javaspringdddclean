package com.icmon.module.dashboard.presentation.dto.response;

import lombok.Data;
import java.util.UUID;

@Data
public class WidgetConfigResponseDTO {
    private UUID id;
    private UUID userId;
    private String widgetId;
    private String widgetType;
    private String widgetTitle;
    private Integer position;
    private Integer width;
    private Integer height;
    private Boolean isActive;
}
