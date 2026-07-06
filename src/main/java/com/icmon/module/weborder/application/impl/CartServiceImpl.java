package com.icmon.module.weborder.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.module.weborder.application.interfaces.CartService;
import com.icmon.module.weborder.domain.TShoppingCart;
import com.icmon.module.weborder.infrastructure.cache.CartCacheService;
import com.icmon.module.weborder.infrastructure.entity.CatalogueItemEntity;
import com.icmon.module.weborder.infrastructure.entity.ShoppingCartEntity;
import com.icmon.module.weborder.infrastructure.entity.ShoppingCartItemEntity;
import com.icmon.module.weborder.infrastructure.mapper.CartMapper;
import com.icmon.module.weborder.infrastructure.repository.CartItemRepository;
import com.icmon.module.weborder.infrastructure.repository.CartRepository;
import com.icmon.module.weborder.infrastructure.repository.CatalogueItemRepository;
import com.icmon.module.weborder.infrastructure.repository.PromotionRepository;
import com.icmon.module.weborder.presentation.dto.request.AddToCartRequestDTO;
import com.icmon.module.weborder.presentation.dto.request.UpdateCartRequestDTO;
import com.icmon.module.weborder.presentation.dto.response.CartResponseDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl extends GenericAuthDomainServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CatalogueItemRepository catalogueItemRepository;
    private final PromotionRepository promotionRepository;
    private final CartMapper cartMapper;
    private final CartCacheService cacheService;

    @Override
    @Transactional
    public CartResponseDTO addToCart(String cartId, AddToCartRequestDTO request) throws SystemGlobalException {
        CatalogueItemEntity item = catalogueItemRepository.findById(request.getItemId())
                .orElseThrow(() -> new FailedRequestException("Item not found: " + request.getItemId(), null));

        ShoppingCartEntity cart = cartRepository.findByCartId(cartId)
                .orElseGet(() -> createNewCart(cartId));

        List<ShoppingCartItemEntity> existingItems = cartItemRepository.findByCartId(cart.getId());
        ShoppingCartItemEntity cartItem = existingItems.stream()
                .filter(i -> i.getItemId().equals(request.getItemId()))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItem.setTotalPrice(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemRepository.save(cartItem);
        } else {
            ShoppingCartItemEntity newItem = new ShoppingCartItemEntity();
            newItem.setCartId(cart.getId());
            newItem.setItemId(request.getItemId());
            newItem.setQuantity(request.getQuantity());
            newItem.setUnitPrice(BigDecimal.ZERO);
            newItem.setTotalPrice(BigDecimal.ZERO);
            newItem.setAttributes(request.getAttributes());
            cartItemRepository.save(newItem);
        }

        recalculateCart(cart);
        cacheService.saveCart(cartMapper.toDomain(cart));
        return toCartResponseDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO updateCartItem(String cartId, UpdateCartRequestDTO request) throws SystemGlobalException {
        ShoppingCartEntity cart = cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new FailedRequestException("Cart not found: " + cartId, null));

        List<ShoppingCartItemEntity> items = cartItemRepository.findByCartId(cart.getId());
        ShoppingCartItemEntity cartItem = items.stream()
                .filter(i -> i.getItemId().equals(request.getItemId()))
                .findFirst()
                .orElseThrow(() -> new FailedRequestException("Item not found in cart: " + request.getItemId(), null));

        if (request.getQuantity() <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(request.getQuantity());
            cartItem.setTotalPrice(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        recalculateCart(cart);
        cacheService.saveCart(cartMapper.toDomain(cart));
        return toCartResponseDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO removeFromCart(String cartId, UUID itemId) throws SystemGlobalException {
        ShoppingCartEntity cart = cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new FailedRequestException("Cart not found: " + cartId, null));

        List<ShoppingCartItemEntity> items = cartItemRepository.findByCartId(cart.getId());
        items.stream()
                .filter(i -> i.getItemId().equals(itemId))
                .findFirst()
                .ifPresent(cartItemRepository::delete);

        recalculateCart(cart);
        cacheService.saveCart(cartMapper.toDomain(cart));
        return toCartResponseDTO(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO getCart(String cartId) throws SystemGlobalException {
        ShoppingCartEntity cart = cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new FailedRequestException("Cart not found: " + cartId, null));
        return toCartResponseDTO(cart);
    }

    @Override
    @Transactional
    public void clearCart(String cartId) throws SystemGlobalException {
        ShoppingCartEntity cart = cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new FailedRequestException("Cart not found: " + cartId, null));
        cartItemRepository.deleteByCartId(cart.getId());
        cart.setSubtotal(BigDecimal.ZERO);
        cart.setDiscount(BigDecimal.ZERO);
        cart.setTax(BigDecimal.ZERO);
        cart.setShipping(BigDecimal.ZERO);
        cart.setTotal(BigDecimal.ZERO);
        cart.setPromotionCode(null);
        cartRepository.save(cart);
        cacheService.evictCart(cartId);
    }

    @Override
    @Transactional
    public CartResponseDTO applyPromotion(String cartId, String promotionCode) throws SystemGlobalException {
        ShoppingCartEntity cart = cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new FailedRequestException("Cart not found: " + cartId, null));

        promotionRepository.findByPromotionCode(promotionCode)
                .orElseThrow(() -> new FailedRequestException("Promotion not found: " + promotionCode, null));

        cart.setPromotionCode(promotionCode);
        cartRepository.save(cart);
        return toCartResponseDTO(cart);
    }

    private ShoppingCartEntity createNewCart(String cartId) {
        try {
            ShoppingCartEntity cart = new ShoppingCartEntity();
            cart.setCartId(cartId);
            cart.setExpiresAt(LocalDateTime.now().plusDays(30));
            cart.setSubtotal(BigDecimal.ZERO);
            cart.setDiscount(BigDecimal.ZERO);
            cart.setTax(BigDecimal.ZERO);
            cart.setShipping(BigDecimal.ZERO);
            cart.setTotal(BigDecimal.ZERO);
            cart.setUserId(getUserId());
            cart.setWhitelabelId(getWhitelabelId());
            return cartRepository.save(cart);
        } catch (SystemGlobalException e) {
            throw new RuntimeException("Failed to create new cart: " + e.getMessage(), e);
        }
    }

    private void recalculateCart(ShoppingCartEntity cart) {
        List<ShoppingCartItemEntity> items = cartItemRepository.findByCartId(cart.getId());
        BigDecimal subtotal = items.stream()
                .map(i -> i.getTotalPrice() != null ? i.getTotalPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setSubtotal(subtotal);
        BigDecimal discount = BigDecimal.ZERO;
        if (cart.getPromotionCode() != null) {
            discount = subtotal.multiply(new BigDecimal("0.1"));
        }
        cart.setDiscount(discount);
        BigDecimal tax = subtotal.subtract(discount).multiply(new BigDecimal("0.07"));
        cart.setTax(tax);
        cart.setTotal(subtotal.subtract(discount).add(tax).add(cart.getShipping() != null ? cart.getShipping() : BigDecimal.ZERO));
        cartRepository.save(cart);
    }

    private CartResponseDTO toCartResponseDTO(ShoppingCartEntity cart) {
        List<ShoppingCartItemEntity> items = cartItemRepository.findByCartId(cart.getId());
        List<CartResponseDTO.CartItemDTO> itemDTOs = items.stream().map(item -> {
            CatalogueItemEntity catItem = catalogueItemRepository.findById(item.getItemId()).orElse(null);
            return CartResponseDTO.CartItemDTO.builder()
                    .id(item.getId())
                    .itemId(item.getItemId())
                    .itemCode(catItem != null ? catItem.getItemCode() : null)
                    .itemName(catItem != null ? catItem.getItemName() : null)
                    .quantity(item.getQuantity())
                    .unitPrice(item.getUnitPrice())
                    .totalPrice(item.getTotalPrice())
                    .imageUrl(catItem != null ? catItem.getImageUrl() : null)
                    .build();
        }).collect(Collectors.toList());

        return CartResponseDTO.builder()
                .id(cart.getId())
                .cartId(cart.getCartId())
                .customerId(cart.getCustomerId())
                .subtotal(cart.getSubtotal())
                .discount(cart.getDiscount())
                .tax(cart.getTax())
                .shipping(cart.getShipping())
                .total(cart.getTotal())
                .promotionCode(cart.getPromotionCode())
                .itemCount(items.size())
                .items(itemDTOs)
                .createdAt(cart.getCreatedAt())
                .build();
    }
}
