package com.icmon.module.dashboard.presentation;

import com.icmon.module.dashboard.application.interfaces.DashboardService;
import com.icmon.module.dashboard.presentation.controller.DashboardController;
import com.icmon.module.dashboard.presentation.dto.response.DashboardOverviewResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DashboardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DashboardController(dashboardService)).build();
    }

    @Test
    void shouldGetDashboardOverview() throws Exception {
        DashboardOverviewResponseDTO response = new DashboardOverviewResponseDTO();
        response.setTotalInvoices(50L);
        response.setTotalRevenue(new BigDecimal("10000"));
        when(dashboardService.getDashboardOverview()).thenReturn(response);
        mockMvc.perform(get("/api/v1/dashboard/overview"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalInvoices").value(50));
    }

    @Test
    void shouldGetJobStatusSummary() throws Exception {
        mockMvc.perform(get("/api/v1/dashboard/job-status"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetTopParts() throws Exception {
        mockMvc.perform(get("/api/v1/dashboard/top-parts?limit=5"))
                .andExpect(status().isOk());
    }
}
