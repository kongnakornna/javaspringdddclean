package com.icmon.module.weborder.infrastructure.cache;

import com.icmon.module.weborder.domain.TShoppingCart;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartCacheService {

    @Cacheable(value = "cart", key = "#cartId")
    public TShoppingCart getCart(String cartId) {
        return null;
    }

    @Cacheable(value = "cart_customer", key = "#customerId")
    public TShoppingCart getCartByCustomer(UUID customerId) {
        return null;
    }

    @CachePut(value = "cart", key = "#cart.cartId")
    public TShoppingCart saveCart(TShoppingCart cart) {
        return cart;
    }

    @CacheEvict(value = {"cart", "cart_customer"}, key = "#cartId")
    public void evictCart(String cartId) {
    }
}
