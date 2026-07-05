package com.icmon.module.dashboard.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "d_widget_config")
@Getter
@Setter
public class DashboardWidgetEntity extends GenericBusinessEntity {

    @Column(name = "user_id", nullable = false)
    private java.util.UUID userId;

    @Column(name = "widget_id", nullable = false, length = 50)
    private String widgetId;

    @Column(name = "widget_type", nullable = false, length = 20)
    private String widgetType;

    @Column(name = "widget_title", length = 100)
    private String widgetTitle;

    @Column(name = "position")
    private Integer position;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(columnDefinition = "jsonb")
    private String config;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "user_id", insertable = false, updatable = false)
    private java.util.UUID userIdRef;
}
