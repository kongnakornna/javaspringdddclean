package com.icmon.module.auth.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "m_role_permission")
public class RolePermissionEntity {

    @EmbeddedId
    private RolePermissionId id;

    @Column(name = "granted_at", nullable = false)
    private LocalDateTime grantedAt = LocalDateTime.now();

    @Embeddable
    @Data
    public static class RolePermissionId implements java.io.Serializable {
        @Column(name = "role_id")
        private UUID roleId;

        @Column(name = "permission_id")
        private UUID permissionId;
    }
}
