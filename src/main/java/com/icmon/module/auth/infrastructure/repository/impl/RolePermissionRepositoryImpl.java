package com.icmon.module.auth.infrastructure.repository.impl;

import com.icmon.module.auth.infrastructure.entity.RolePermissionEntity;
import com.icmon.module.auth.infrastructure.repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RolePermissionRepositoryImpl {
    private final RolePermissionRepository rolePermissionRepository;

    public RolePermissionEntity save(RolePermissionEntity entity) {
        return rolePermissionRepository.save(entity);
    }

    public List<RolePermissionEntity> findByRoleId(UUID roleId) {
        return rolePermissionRepository.findByIdRoleId(roleId);
    }

    public void deleteByRoleIdAndPermissionId(UUID roleId, UUID permissionId) {
        rolePermissionRepository.deleteByIdRoleIdAndIdPermissionId(roleId, permissionId);
    }
}
