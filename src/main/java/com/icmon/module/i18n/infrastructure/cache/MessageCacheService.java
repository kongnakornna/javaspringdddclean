package com.icmon.module.i18n.infrastructure.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageCacheService {

    @Cacheable(value = "messages", key = "#messageKey + ':' + #languageCode")
    public String getMessage(String messageKey, String languageCode) {
        return null;
    }

    @Cacheable(value = "messages_all", key = "#languageCode")
    public Map<String, String> getAllMessages(String languageCode) {
        return null;
    }

    @CacheEvict(value = {"messages", "messages_all"}, key = "#messageKey + ':' + #languageCode")
    public void evictMessage(String messageKey, String languageCode) {
    }

    @CacheEvict(value = {"messages", "messages_all"}, allEntries = true)
    public void evictAllMessages() {
    }
}
