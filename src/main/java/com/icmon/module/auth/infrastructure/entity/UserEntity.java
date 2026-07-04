package com.icmon.module.auth.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import com.icmon.module.auth.domain.enums.UserStatus;
import com.icmon.module.auth.domain.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "m_user")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends GenericBusinessEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private RoleType role;
}
