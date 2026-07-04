package com.icmon._shared.infrastructure;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public record RepositoryAuth(@NotNull UUID userId, @NotNull UUID whitelabelId, @NotNull UUID requestId) {
}