package com.icmon.module.auth.presentation.dto.request;

import com.icmon.module.auth.domain.enums.RoleType;
import com.icmon.module.auth.domain.enums.UserStatus;
import lombok.Data;

@Data
public class UserUpdateDTO {
    private String fullName;
    private String phoneNumber;
    private String profileImageUrl;
    private UserStatus status;
    private RoleType role;
}
