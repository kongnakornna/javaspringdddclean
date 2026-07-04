package com.icmon.module.auth.presentation.dto.response;

import com.icmon.module.auth.domain.enums.RoleType;
import com.icmon.module.auth.domain.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponseDTO {
    private UUID id;
    private String username;
    private String email;
    private String fullName;
    private UserStatus status;
    private String phoneNumber;
    private String profileImageUrl;
    private RoleType role;
}
