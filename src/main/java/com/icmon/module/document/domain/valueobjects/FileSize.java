package com.icmon.module.document.domain.valueobjects;

public record FileSize(Long bytes) {
    public FileSize {
        if (bytes == null) {
            throw new IllegalArgumentException("File size must not be null");
        }
        if (bytes < 0) {
            throw new IllegalArgumentException("File size must not be negative");
        }
    }

    public String toHumanReadable() {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024));
        return String.format("%.1f GB", bytes / (1024.0 * 1024 * 1024));
    }
}
