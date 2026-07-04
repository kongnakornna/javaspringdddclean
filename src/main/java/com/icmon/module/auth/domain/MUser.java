package com.icmon.module.auth.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.auth.domain.enums.UserStatus;
import com.icmon.module.auth.domain.enums.RoleType;
import com.icmon.module.auth.domain.valueobjects.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MUser extends GenericBusinessClass {
    private String username;
    private Email email;
    private String passwordHash;
    private String fullName;
    private UserStatus status;
    private String phoneNumber;
    private String profileImageUrl;
    private RoleType role;

    public void activate() {
        this.status = UserStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = UserStatus.INACTIVE;
    }

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }
}
