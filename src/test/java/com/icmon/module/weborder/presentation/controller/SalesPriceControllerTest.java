package com.icmon.module.weborder.presentation.controller;

import com.icmon.module.weborder.application.interfaces.SalesPriceService;
import com.icmon.module.weborder.presentation.dto.response.SalesPriceResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SalesPriceController.class)
class SalesPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalesPriceService salesPriceService;

    @Test
    void testGetPricesByItem() throws Exception {
        when(salesPriceService.getPricesByItem(any(UUID.class))).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/wos/sales-prices/item/{itemId}", UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}
