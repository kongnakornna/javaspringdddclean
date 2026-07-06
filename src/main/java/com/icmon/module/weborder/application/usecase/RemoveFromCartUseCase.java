package com.icmon.module.weborder.application.usecase;

import com.icmon.module.weborder.application.interfaces.CartService;
import com.icmon.module.weborder.presentation.dto.response.CartResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RemoveFromCartUseCase {
    private final CartService cartService;

    public CartResponseDTO execute(String cartId, UUID itemId) throws SystemGlobalException {
        return cartService.removeFromCart(cartId, itemId);
    }
}
