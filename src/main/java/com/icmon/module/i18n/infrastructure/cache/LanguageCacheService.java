package com.icmon.module.i18n.infrastructure.cache;

import com.icmon.module.i18n.domain.MLanguage;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageCacheService {

    @Cacheable(value = "languages", key = "#languageCode")
    public MLanguage getLanguage(String languageCode) {
        return null;
    }

    @Cacheable(value = "languages_active", key = "'all'")
    public List<MLanguage> getActiveLanguages() {
        return null;
    }

    @CachePut(value = "languages", key = "#language.languageCode")
    public MLanguage saveLanguage(MLanguage language) {
        return language;
    }

    @CacheEvict(value = {"languages", "languages_active"}, key = "#languageCode")
    public void evictLanguage(String languageCode) {
    }

    @CacheEvict(value = {"languages", "languages_active"}, allEntries = true)
    public void evictAllLanguages() {
    }
}
