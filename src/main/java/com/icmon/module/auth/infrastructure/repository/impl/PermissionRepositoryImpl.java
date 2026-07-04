package com.icmon.module.auth.infrastructure.repository.impl;

import com.icmon.module.auth.infrastructure.entity.PermissionEntity;
import com.icmon.module.auth.infrastructure.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionRepositoryImpl {
    private final PermissionRepository permissionRepository;

    public PermissionEntity save(PermissionEntity entity) {
        return permissionRepository.save(entity);
    }

    public Optional<PermissionEntity> findById(UUID id) {
        return permissionRepository.findById(id);
    }

    public Optional<PermissionEntity> findByName(String name) {
        return permissionRepository.findByName(name);
    }

    public List<PermissionEntity> findByRoleId(UUID roleId) {
        return permissionRepository.findByRoleId(roleId);
    }
}
