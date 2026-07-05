package com.icmon.module.i18n.application.impl;

import com.icmon.module.i18n.application.interfaces.LanguageService;
import com.icmon.module.i18n.application.usecase.GetLanguagesUseCase;
import com.icmon.module.i18n.application.usecase.SwitchLanguageUseCase;
import com.icmon.module.i18n.infrastructure.entity.LanguageEntity;
import com.icmon.module.i18n.infrastructure.repository.LanguageRepository;
import com.icmon.module.i18n.presentation.dto.response.LanguageResponseDTO;
import com.icmon.exception.SystemGlobalException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final GetLanguagesUseCase getLanguagesUseCase;
    private final SwitchLanguageUseCase switchLanguageUseCase;
    private final LanguageRepository languageRepository;

    @Override
    public List<LanguageResponseDTO> getAllLanguages() throws SystemGlobalException {
        return getLanguagesUseCase.execute();
    }

    @Override
    public LanguageResponseDTO getCurrentLanguage() throws SystemGlobalException {
        return languageRepository.findByIsDefaultTrue()
                .map(entity -> toResponseDTO(entity))
                .orElseThrow(() -> new SystemGlobalException("Default language not found", null));
    }

    @Override
    public LanguageResponseDTO switchLanguage(String languageCode, Locale locale) throws SystemGlobalException {
        return switchLanguageUseCase.execute(languageCode);
    }

    @Override
    public LanguageResponseDTO getDefaultLanguage() throws SystemGlobalException {
        LanguageEntity entity = languageRepository.findByIsDefaultTrue()
                .orElseThrow(() -> new SystemGlobalException("Default language not found", null));
        return switchLanguageUseCase.execute(entity.getLanguageCode());
    }

    private LanguageResponseDTO toResponseDTO(LanguageEntity entity) {
        return LanguageResponseDTO.builder()
                .languageCode(entity.getLanguageCode())
                .languageName(entity.getLanguageName())
                .languageNameEn(entity.getLanguageNameEn())
                .flagEmoji(entity.getFlagEmoji())
                .isRtl(entity.getIsRtl())
                .isActive(entity.getIsActive())
                .isDefault(entity.getIsDefault())
                .sortOrder(entity.getSortOrder())
                .locale(entity.getLocale())
                .dateFormat(entity.getDateFormat())
                .timeFormat(entity.getTimeFormat())
                .numberFormat(entity.getNumberFormat())
                .currencySymbol(entity.getCurrencySymbol())
                .build();
    }
}
