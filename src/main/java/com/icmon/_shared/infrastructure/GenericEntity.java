package com.icmon._shared.infrastructure;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(GenericEntity.EntityListener.class)
public abstract class GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private Boolean deleted = false;

    public static class EntityListener {

        @PrePersist
        public void prePersist(GenericEntity schema) {
            LocalDateTime now = LocalDateTime.now();
            schema.setCreatedAt(now);
            schema.setDeleted(false);
        }

        @PreUpdate
        public void preUpdate(GenericEntity schema) {
            schema.setUpdatedAt(LocalDateTime.now());
        }

        @PreRemove
        public void preRemove(GenericEntity entity) {
            entity.setDeleted(true);
            entity.setDeletedAt(LocalDateTime.now());
        }
    }
}