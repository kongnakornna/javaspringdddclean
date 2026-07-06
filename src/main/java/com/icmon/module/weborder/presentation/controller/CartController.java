package com.icmon.module.weborder.presentation.controller;

import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.weborder.application.interfaces.CartService;
import com.icmon.module.weborder.presentation.dto.request.AddToCartRequestDTO;
import com.icmon.module.weborder.presentation.dto.request.UpdateCartRequestDTO;
import com.icmon.module.weborder.presentation.dto.response.CartResponseDTO;
import com.icmon.exception.SystemGlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wos/cart")
@Tag(name = "Web Order - Cart", description = "Shopping Cart APIs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Add item to cart")
    public ResponseEntity<CartResponseDTO> addToCart(
            @Valid @RequestBody AddToCartRequestDTO request,
            HttpServletRequest httpRequest) throws SystemGlobalException {
        String cartId = getOrCreateCartId(httpRequest);
        CartResponseDTO response = cartService.addToCart(cartId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Update cart item quantity")
    public ResponseEntity<CartResponseDTO> updateCartItem(
            @Valid @RequestBody UpdateCartRequestDTO request,
            HttpServletRequest httpRequest) throws SystemGlobalException {
        String cartId = getOrCreateCartId(httpRequest);
        CartResponseDTO response = cartService.updateCartItem(cartId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove/{itemId}")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Remove item from cart")
    public ResponseEntity<CartResponseDTO> removeFromCart(
            @PathVariable UUID itemId,
            HttpServletRequest httpRequest) throws SystemGlobalException {
        String cartId = getOrCreateCartId(httpRequest);
        CartResponseDTO response = cartService.removeFromCart(cartId, itemId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    @Operation(summary = "View current cart")
    public ResponseEntity<CartResponseDTO> getCart(HttpServletRequest httpRequest)
            throws SystemGlobalException {
        String cartId = getOrCreateCartId(httpRequest);
        CartResponseDTO response = cartService.getCart(cartId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear")
    @RateLimit(limit = 10, duration = 300, keyType = "USER_ID")
    @Operation(summary = "Clear cart")
    public ResponseEntity<Void> clearCart(HttpServletRequest httpRequest)
            throws SystemGlobalException {
        String cartId = getOrCreateCartId(httpRequest);
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/promotion")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Apply promotion code to cart")
    public ResponseEntity<CartResponseDTO> applyPromotion(
            @RequestParam String promotionCode,
            HttpServletRequest httpRequest) throws SystemGlobalException {
        String cartId = getOrCreateCartId(httpRequest);
        CartResponseDTO response = cartService.applyPromotion(cartId, promotionCode);
        return ResponseEntity.ok(response);
    }

    private String getOrCreateCartId(HttpServletRequest request) {
        String cartId = request.getHeader("X-Cart-Id");
        if (cartId == null || cartId.isBlank()) {
            cartId = "cart_" + System.currentTimeMillis();
        }
        return cartId;
    }
}
