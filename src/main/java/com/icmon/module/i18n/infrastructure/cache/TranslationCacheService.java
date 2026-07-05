package com.icmon.module.i18n.infrastructure.cache;

import com.icmon.module.i18n.domain.MTranslation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslationCacheService {

    @Cacheable(value = "translations", key = "#messageKey + ':' + #languageCode")
    public MTranslation getTranslation(String messageKey, String languageCode) {
        return null;
    }

    @Cacheable(value = "translations_by_lang", key = "#languageCode")
    public List<MTranslation> getTranslationsByLanguage(String languageCode) {
        return null;
    }

    @CacheEvict(value = {"translations", "translations_by_lang"}, key = "#messageKey + ':' + #languageCode")
    public void evictTranslation(String messageKey, String languageCode) {
    }

    @CacheEvict(value = {"translations_by_lang"}, key = "#languageCode")
    public void evictTranslationsByLanguage(String languageCode) {
    }

    @CacheEvict(value = {"translations", "translations_by_lang"}, allEntries = true)
    public void evictAllTranslations() {
    }
}
