package com.icmon.module.i18n.infrastructure.resource;

import com.icmon.module.i18n.infrastructure.cache.MessageCacheService;
import com.icmon.module.i18n.infrastructure.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class DatabaseMessageSource extends AbstractMessageSource {

    private final TranslationRepository translationRepository;
    private final MessageCacheService messageCacheService;

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String languageCode = locale.toLanguageTag();

        String cachedMessage = messageCacheService.getMessage(code, languageCode);
        if (cachedMessage != null) {
            return new MessageFormat(cachedMessage, locale);
        }

        return translationRepository.findByMessageKeyAndLanguageCode(code, languageCode)
                .map(translation -> new MessageFormat(translation.getMessageText(), locale))
                .orElse(null);
    }
}
