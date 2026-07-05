package com.icmon.module.i18n.application.usecase;

import com.icmon.module.i18n.infrastructure.repository.TranslationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetResourceBundleUseCase {

    private final MessageSource messageSource;
    private final TranslationRepository translationRepository;

    public Map<String, String> execute(String languageCode) {
        Map<String, String> result = new HashMap<>();

        translationRepository.findByLanguageCode(languageCode).forEach(translation -> {
            result.put(translation.getMessageKey(), translation.getMessageText());
        });

        return result;
    }
}
