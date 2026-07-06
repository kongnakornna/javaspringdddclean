package com.icmon.module.weborder.infrastructure.repository;

import com.icmon.module.weborder.infrastructure.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<ShoppingCartEntity, UUID> {
    Optional<ShoppingCartEntity> findByCartId(String cartId);

    Optional<ShoppingCartEntity> findByCustomerId(UUID customerId);
}
