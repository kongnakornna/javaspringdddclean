package com.icmon.module.i18n.application.usecase;

import com.icmon.module.i18n.infrastructure.mapper.TranslationMapper;
import com.icmon.module.i18n.infrastructure.repository.TranslationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslateTextUseCase {

    private final TranslationRepository translationRepository;
    private final TranslationMapper translationMapper;

    public String execute(String text, String sourceLang, String targetLang) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return translationRepository.findByMessageKeyAndLanguageCode(text, targetLang)
                .map(translation -> translation.getMessageText())
                .orElse(text);
    }
}
