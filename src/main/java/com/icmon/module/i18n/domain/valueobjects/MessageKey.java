package com.icmon.module.i18n.domain.valueobjects;

import lombok.Value;

@Value
public class MessageKey {
    String key;

    public MessageKey(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Message key must not be blank");
        }
        this.key = key;
    }
}
