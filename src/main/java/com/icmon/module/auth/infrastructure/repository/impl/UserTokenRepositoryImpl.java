package com.icmon.module.auth.infrastructure.repository.impl;

import com.icmon.module.auth.domain.enums.TokenType;
import com.icmon.module.auth.infrastructure.entity.UserTokenEntity;
import com.icmon.module.auth.infrastructure.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserTokenRepositoryImpl {
    private final UserTokenRepository userTokenRepository;

    public UserTokenEntity save(UserTokenEntity entity) {
        return userTokenRepository.save(entity);
    }

    public Optional<UserTokenEntity> findByToken(String token) {
        return userTokenRepository.findByToken(token);
    }

    public List<UserTokenEntity> findByUserIdAndTokenType(UUID userId, TokenType tokenType) {
        return userTokenRepository.findByUserIdAndTokenType(userId, tokenType);
    }
}
