package com.icmon.module.weborder.infrastructure.repository;

import com.icmon.module.weborder.infrastructure.entity.ShoppingCartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<ShoppingCartItemEntity, UUID> {
    List<ShoppingCartItemEntity> findByCartId(UUID cartId);

    void deleteByCartId(UUID cartId);
}
