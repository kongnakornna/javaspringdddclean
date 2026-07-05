package com.icmon.module.dashboard.presentation.dto.request;

import lombok.Data;
import java.util.UUID;

@Data
public class WidgetConfigRequestDTO {
    private UUID userId;
    private String widgetId;
    private String widgetType;
    private String widgetTitle;
    private Integer position;
    private Integer width;
    private Integer height;
    private String config;
}
