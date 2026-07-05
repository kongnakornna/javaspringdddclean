package com.icmon.module.document.domain.valueobjects;

import java.util.UUID;

public record DocumentReference(String referenceType, UUID referenceId) {
    public DocumentReference {
        if (referenceType == null || referenceType.isBlank()) {
            throw new IllegalArgumentException("referenceType must not be null or blank");
        }
        if (referenceId == null) {
            throw new IllegalArgumentException("referenceId must not be null");
        }
    }
}
