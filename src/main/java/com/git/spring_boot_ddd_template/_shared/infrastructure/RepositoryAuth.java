package com.git.spring_boot_ddd_template._shared.infrastructure;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public record RepositoryAuth(@NotNull UUID userId, @NotNull UUID whitelabelId, @NotNull UUID requestId) {
}