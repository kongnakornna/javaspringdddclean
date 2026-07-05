package com.icmon.module.i18n.application.usecase;

import com.icmon.module.i18n.infrastructure.cache.TranslationCacheService;
import com.icmon.module.i18n.infrastructure.entity.TranslationEntity;
import com.icmon.module.i18n.infrastructure.mapper.TranslationMapper;
import com.icmon.module.i18n.infrastructure.repository.TranslationRepository;
import com.icmon.module.i18n.presentation.dto.request.TranslationUpdateRequestDTO;
import com.icmon.module.i18n.presentation.dto.response.TranslationResponseDTO;
import com.icmon.exception.SystemGlobalException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SaveTranslationUseCase {

    private final TranslationRepository translationRepository;
    private final TranslationMapper translationMapper;
    private final TranslationCacheService translationCacheService;

    public TranslationResponseDTO execute(TranslationUpdateRequestDTO request) throws SystemGlobalException {
        if (request.getId() != null) {
            TranslationEntity existing = translationRepository.findById(request.getId())
                    .orElseThrow(() -> new SystemGlobalException("Translation not found: " + request.getId(), null));
            existing.setMessageKey(request.getMessageKey());
            existing.setLanguageCode(request.getLanguageCode());
            existing.setMessageText(request.getMessageText());
            existing.setContext(request.getContext());
            existing.setDescription(request.getDescription());
            TranslationEntity saved = translationRepository.save(existing);
            translationCacheService.evictTranslation(saved.getMessageKey(), saved.getLanguageCode());
            return toResponseDTO(saved);
        }

        TranslationEntity entity = new TranslationEntity();
        entity.setMessageKey(request.getMessageKey());
        entity.setLanguageCode(request.getLanguageCode());
        entity.setMessageText(request.getMessageText());
        entity.setContext(request.getContext());
        entity.setDescription(request.getDescription());
        entity.setVersion(1);
        entity.setIsApproved(false);
        TranslationEntity saved = translationRepository.save(entity);
        translationCacheService.evictTranslation(saved.getMessageKey(), saved.getLanguageCode());
        return toResponseDTO(saved);
    }

    private TranslationResponseDTO toResponseDTO(TranslationEntity entity) {
        return TranslationResponseDTO.builder()
                .id(entity.getId())
                .messageKey(entity.getMessageKey())
                .languageCode(entity.getLanguageCode())
                .messageText(entity.getMessageText())
                .context(entity.getContext())
                .description(entity.getDescription())
                .version(entity.getVersion())
                .isApproved(entity.getIsApproved())
                .approvedBy(entity.getApprovedBy())
                .approvedAt(entity.getApprovedAt())
                .build();
    }
}
