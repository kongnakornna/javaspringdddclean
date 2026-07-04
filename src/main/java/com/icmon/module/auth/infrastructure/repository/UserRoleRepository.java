package com.icmon.module.auth.infrastructure.repository;

import com.icmon.module.auth.infrastructure.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleEntity.UserRoleId> {
    List<UserRoleEntity> findByIdUserId(UUID userId);
    void deleteByIdUserIdAndIdRoleId(UUID userId, UUID roleId);
}
