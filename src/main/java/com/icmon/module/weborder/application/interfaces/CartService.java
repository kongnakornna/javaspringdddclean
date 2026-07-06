package com.icmon.module.weborder.application.interfaces;

import com.icmon.module.weborder.presentation.dto.request.AddToCartRequestDTO;
import com.icmon.module.weborder.presentation.dto.request.UpdateCartRequestDTO;
import com.icmon.module.weborder.presentation.dto.response.CartResponseDTO;
import com.icmon.exception.SystemGlobalException;

import java.util.UUID;

public interface CartService {
    CartResponseDTO addToCart(String cartId, AddToCartRequestDTO request) throws SystemGlobalException;

    CartResponseDTO updateCartItem(String cartId, UpdateCartRequestDTO request) throws SystemGlobalException;

    CartResponseDTO removeFromCart(String cartId, UUID itemId) throws SystemGlobalException;

    CartResponseDTO getCart(String cartId) throws SystemGlobalException;

    void clearCart(String cartId) throws SystemGlobalException;

    CartResponseDTO applyPromotion(String cartId, String promotionCode) throws SystemGlobalException;
}
