package com.icmon.module.auth.infrastructure.repository;

import com.icmon.module.auth.infrastructure.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, RolePermissionEntity.RolePermissionId> {
    List<RolePermissionEntity> findByIdRoleId(UUID roleId);
    void deleteByIdRoleIdAndIdPermissionId(UUID roleId, UUID permissionId);
}
