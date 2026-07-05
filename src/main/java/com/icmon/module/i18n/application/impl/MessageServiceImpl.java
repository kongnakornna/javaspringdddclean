package com.icmon.module.i18n.application.impl;

import com.icmon.module.i18n.application.interfaces.MessageService;
import com.icmon.module.i18n.infrastructure.cache.MessageCacheService;
import com.icmon.module.i18n.infrastructure.repository.TranslationRepository;
import com.icmon.module.i18n.presentation.dto.response.MessageResponseDTO;
import com.icmon.exception.SystemGlobalException;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;
    private final TranslationRepository translationRepository;
    private final MessageCacheService messageCacheService;

    public MessageServiceImpl(MessageSource messageSource, TranslationRepository translationRepository, MessageCacheService messageCacheService) {
        this.messageSource = messageSource;
        this.translationRepository = translationRepository;
        this.messageCacheService = messageCacheService;
    }

    @Override
    public MessageResponseDTO getMessage(String messageKey, String languageCode) throws SystemGlobalException {
        String cached = messageCacheService.getMessage(messageKey, languageCode);
        if (cached != null) {
            return buildResponse(messageKey, languageCode, cached);
        }

        try {
            Locale locale = Locale.forLanguageTag(languageCode);
            String msg = messageSource.getMessage(messageKey, null, locale);
            return buildResponse(messageKey, languageCode, msg);
        } catch (NoSuchMessageException e) {
            // fall through to DB lookup
        }

        return translationRepository.findByMessageKeyAndLanguageCode(messageKey, languageCode)
                .map(translation -> buildResponse(messageKey, languageCode, translation.getMessageText()))
                .orElseThrow(() -> new SystemGlobalException(
                        "Message not found: " + messageKey + " for language: " + languageCode, null));
    }

    @Override
    public Map<String, String> getAllMessages(String languageCode) throws SystemGlobalException {
        Map<String, String> cached = messageCacheService.getAllMessages(languageCode);
        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        Map<String, String> result = new HashMap<>();
        translationRepository.findByLanguageCode(languageCode).forEach(translation -> {
            result.put(translation.getMessageKey(), translation.getMessageText());
        });
        return result;
    }

    private MessageResponseDTO buildResponse(String messageKey, String languageCode, String messageText) {
        return MessageResponseDTO.builder()
                .messageKey(messageKey)
                .languageCode(languageCode)
                .messageText(messageText)
                .build();
    }
}
