package com.icmon.module.auth.infrastructure.repository.impl;

import com.icmon.module.auth.infrastructure.entity.UserRoleEntity;
import com.icmon.module.auth.infrastructure.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRoleRepositoryImpl {
    private final UserRoleRepository userRoleRepository;

    public UserRoleEntity save(UserRoleEntity entity) {
        return userRoleRepository.save(entity);
    }

    public List<UserRoleEntity> findByUserId(UUID userId) {
        return userRoleRepository.findByIdUserId(userId);
    }

    public void deleteByUserIdAndRoleId(UUID userId, UUID roleId) {
        userRoleRepository.deleteByIdUserIdAndIdRoleId(userId, roleId);
    }
}
