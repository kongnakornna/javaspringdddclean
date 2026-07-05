package com.icmon.module.i18n.application.usecase;

import com.icmon.module.i18n.domain.MLanguage;
import com.icmon.module.i18n.infrastructure.cache.LanguageCacheService;
import com.icmon.module.i18n.infrastructure.mapper.LanguageMapper;
import com.icmon.module.i18n.infrastructure.repository.LanguageRepository;
import com.icmon.module.i18n.presentation.dto.response.LanguageResponseDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetLanguagesUseCase {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;
    private final LanguageCacheService languageCacheService;

    public List<LanguageResponseDTO> execute() {
        List<MLanguage> cached = languageCacheService.getActiveLanguages();
        if (cached != null && !cached.isEmpty()) {
            return cached.stream().map(this::toResponseDTO).collect(Collectors.toList());
        }

        return languageRepository.findAllByOrderBySortOrderAsc().stream()
                .map(languageMapper::toDomain)
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
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
