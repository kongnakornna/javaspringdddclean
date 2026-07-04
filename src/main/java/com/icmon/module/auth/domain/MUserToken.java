package com.icmon.module.auth.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.auth.domain.enums.TokenType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class MUserToken extends GenericBusinessClass {
    private UUID tokenUserId;
    private String token;
    private TokenType tokenType;
    private LocalDateTime expiryDate;
    private boolean revoked;
    private LocalDateTime revokedAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }

    public boolean isValid() {
        return !revoked && !isExpired();
    }

    public void revoke() {
        this.revoked = true;
        this.revokedAt = LocalDateTime.now();
    }
}
