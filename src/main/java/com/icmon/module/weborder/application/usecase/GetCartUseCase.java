package com.icmon.module.weborder.application.usecase;

import com.icmon.module.weborder.application.interfaces.CartService;
import com.icmon.module.weborder.presentation.dto.response.CartResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCartUseCase {
    private final CartService cartService;

    public CartResponseDTO execute(String cartId) throws SystemGlobalException {
        return cartService.getCart(cartId);
    }
}
