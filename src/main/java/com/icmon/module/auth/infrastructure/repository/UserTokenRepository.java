package com.icmon.module.auth.infrastructure.repository;

import com.icmon.module.auth.infrastructure.entity.UserTokenEntity;
import com.icmon.module.auth.domain.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTokenRepository extends JpaRepository<UserTokenEntity, UUID> {
    Optional<UserTokenEntity> findByToken(String token);
    List<UserTokenEntity> findByUserIdAndTokenType(UUID userId, TokenType tokenType);
    List<UserTokenEntity> findByUserId(UUID userId);
}
