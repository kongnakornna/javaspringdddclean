package com.icmon.module.i18n.application.usecase;

import com.icmon.module.i18n.domain.MLanguage;
import com.icmon.module.i18n.infrastructure.cache.LanguageCacheService;
import com.icmon.module.i18n.infrastructure.entity.LanguageEntity;
import com.icmon.module.i18n.infrastructure.mapper.LanguageMapper;
import com.icmon.module.i18n.infrastructure.repository.LanguageRepository;
import com.icmon.module.i18n.presentation.dto.response.LanguageResponseDTO;
import com.icmon.exception.SystemGlobalException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SwitchLanguageUseCase {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;
    private final LanguageCacheService languageCacheService;

    public LanguageResponseDTO execute(String languageCode) throws SystemGlobalException {
        LanguageEntity entity = languageRepository.findByLanguageCode(languageCode)
                .orElseThrow(() -> new SystemGlobalException("Language not found: " + languageCode, null));

        MLanguage language = languageMapper.toDomain(entity);
        languageCacheService.saveLanguage(language);

        return toResponseDTO(language);
    }

    private LanguageResponseDTO toResponseDTO(MLanguage lang) {
        return LanguageResponseDTO.builder()
                .languageCode(lang.getLanguageCode())
                .languageName(lang.getLanguageName())
                .languageNameEn(lang.getLanguageNameEn())
                .flagEmoji(lang.getFlagEmoji())
                .isRtl(lang.getIsRtl())
                .isActive(lang.getIsActive())
                .isDefault(lang.getIsDefault())
                .sortOrder(lang.getSortOrder())
                .locale(lang.getLocale())
                .dateFormat(lang.getDateFormat())
                .timeFormat(lang.getTimeFormat())
                .numberFormat(lang.getNumberFormat())
                .currencySymbol(lang.getCurrencySymbol())
                .build();
    }
}
