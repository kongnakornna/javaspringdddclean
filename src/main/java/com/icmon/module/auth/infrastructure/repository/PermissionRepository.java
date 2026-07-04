package com.icmon.module.auth.infrastructure.repository;

import com.icmon.module.auth.infrastructure.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID> {
    Optional<PermissionEntity> findByName(String name);

    @Query("SELECT p FROM PermissionEntity p " +
           "JOIN RolePermissionEntity rp ON p.id = rp.id.permissionId " +
           "WHERE rp.id.roleId = :roleId")
    List<PermissionEntity> findByRoleId(UUID roleId);
}
