package com.icmon.module.weborder.application.usecase;

import com.icmon.module.weborder.application.interfaces.CartService;
import com.icmon.module.weborder.presentation.dto.request.AddToCartRequestDTO;
import com.icmon.module.weborder.presentation.dto.response.CartResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddToCartUseCase {
    private final CartService cartService;

    public CartResponseDTO execute(String cartId, AddToCartRequestDTO request) throws SystemGlobalException {
        return cartService.addToCart(cartId, request);
    }
}
