package com.icmon.module.weborder.presentation.controller;

import com.icmon.module.weborder.application.interfaces.CartService;
import com.icmon.module.weborder.presentation.dto.response.CartResponseDTO;
import com.icmon.exception.SystemGlobalException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    void testGetCart() throws Exception {
        CartResponseDTO dto = CartResponseDTO.builder()
                .cartId("cart_test")
                .subtotal(BigDecimal.ZERO)
                .build();
        when(cartService.getCart(anyString())).thenReturn(dto);

        mockMvc.perform(get("/api/v1/wos/cart")
                        .header("X-Cart-Id", "cart_test"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddToCart() throws Exception {
        CartResponseDTO dto = CartResponseDTO.builder()
                .cartId("cart_test")
                .build();
        when(cartService.addToCart(anyString(), any())).thenReturn(dto);

        String requestBody = """
                {
                    "itemId": "%s",
                    "quantity": 2
                }
                """.formatted(UUID.randomUUID().toString());

        mockMvc.perform(post("/api/v1/wos/cart/add")
                        .header("X-Cart-Id", "cart_test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testClearCart() throws Exception {
        mockMvc.perform(delete("/api/v1/wos/cart/clear")
                        .header("X-Cart-Id", "cart_test"))
                .andExpect(status().isNoContent());
    }
}
