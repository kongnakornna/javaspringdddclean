package com.icmon.module.i18n.application.usecase;

import com.icmon.module.i18n.infrastructure.cache.MessageCacheService;
import com.icmon.module.i18n.infrastructure.repository.TranslationRepository;
import com.icmon.module.i18n.presentation.dto.response.MessageResponseDTO;
import com.icmon.exception.SystemGlobalException;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class GetMessageUseCase {

    private final MessageSource messageSource;
    private final TranslationRepository translationRepository;
    private final MessageCacheService messageCacheService;

    public MessageResponseDTO execute(String messageKey, String languageCode) throws SystemGlobalException {
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

    private MessageResponseDTO buildResponse(String messageKey, String languageCode, String messageText) {
        return MessageResponseDTO.builder()
                .messageKey(messageKey)
                .languageCode(languageCode)
                .messageText(messageText)
                .build();
    }
}
