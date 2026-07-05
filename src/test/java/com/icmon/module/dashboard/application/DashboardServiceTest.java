package com.icmon.module.dashboard.application;

import com.icmon.module.dashboard.application.impl.DashboardServiceImpl;
import com.icmon.module.dashboard.infrastructure.cache.DashboardCacheService;
import com.icmon.module.dashboard.infrastructure.repository.DashboardRepository;
import com.icmon.module.dashboard.presentation.dto.response.DashboardOverviewResponseDTO;
import com.icmon.module.dashboard.presentation.dto.response.InventoryOverviewResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private DashboardRepository dashboardRepository;
    @Mock
    private DashboardCacheService cacheService;

    private DashboardServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new DashboardServiceImpl(dashboardRepository, cacheService);
    }

    @Test
    void shouldCreateService() {
        assertNotNull(service);
    }
}
