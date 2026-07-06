package com.icmon.module.weborder.application.impl;

import com.icmon.module.weborder.infrastructure.entity.CatalogueItemEntity;
import com.icmon.module.weborder.infrastructure.entity.ShoppingCartEntity;
import com.icmon.module.weborder.infrastructure.repository.*;
import com.icmon.module.weborder.presentation.dto.request.AddToCartRequestDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private CatalogueItemRepository catalogueItemRepository;
    @Mock
    private PromotionRepository promotionRepository;

    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartServiceImpl(cartRepository, cartItemRepository, catalogueItemRepository,
                promotionRepository, null, null);
    }

    @Test
    void testGetCartNotFound() {
        when(cartRepository.findByCartId("invalid")).thenReturn(Optional.empty());
        assertThrows(FailedRequestException.class, () -> cartService.getCart("invalid"));
    }

    @Test
    void testGetCart() throws SystemGlobalException {
        ShoppingCartEntity cart = new ShoppingCartEntity();
        cart.setId(UUID.randomUUID());
        cart.setCartId("cart_test");
        cart.setSubtotal(java.math.BigDecimal.ZERO);

        when(cartRepository.findByCartId("cart_test")).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartId(cart.getId())).thenReturn(java.util.List.of());

        var result = cartService.getCart("cart_test");
        assertEquals("cart_test", result.getCartId());
    }

    @Test
    void testClearCart() throws SystemGlobalException {
        ShoppingCartEntity cart = new ShoppingCartEntity();
        cart.setId(UUID.randomUUID());
        cart.setCartId("cart_test");

        when(cartRepository.findByCartId("cart_test")).thenReturn(Optional.of(cart));

        cartService.clearCart("cart_test");
        verify(cartItemRepository).deleteByCartId(cart.getId());
        verify(cartRepository).save(any());
    }

    @Test
    void testAddToCartItemNotFound() {
        AddToCartRequestDTO request = new AddToCartRequestDTO();
        request.setItemId(UUID.randomUUID());
        request.setQuantity(1);

        when(catalogueItemRepository.findById(request.getItemId())).thenReturn(Optional.empty());

        assertThrows(FailedRequestException.class, () -> cartService.addToCart("cart_test", request));
    }
}
