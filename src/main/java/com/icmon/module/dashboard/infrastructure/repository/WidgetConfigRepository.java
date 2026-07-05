package com.icmon.module.dashboard.infrastructure.repository;

import com.icmon.module.dashboard.infrastructure.entity.DashboardWidgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WidgetConfigRepository extends JpaRepository<DashboardWidgetEntity, UUID> {
    List<DashboardWidgetEntity> findByUserIdOrderByPositionAsc(UUID userId);
    List<DashboardWidgetEntity> findByUserIdAndIsActiveTrueOrderByPositionAsc(UUID userId);
    void deleteByUserIdAndWidgetId(UUID userId, String widgetId);
}
