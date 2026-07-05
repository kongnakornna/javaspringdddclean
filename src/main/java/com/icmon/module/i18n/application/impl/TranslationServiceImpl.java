package com.icmon.module.i18n.application.impl;

import com.icmon.module.i18n.application.interfaces.TranslationService;
import com.icmon.module.i18n.infrastructure.cache.TranslationCacheService;
import com.icmon.module.i18n.infrastructure.entity.TranslationEntity;
import com.icmon.module.i18n.infrastructure.mapper.TranslationMapper;
import com.icmon.module.i18n.infrastructure.repository.TranslationRepository;
import com.icmon.module.i18n.presentation.dto.request.BulkImportRequestDTO;
import com.icmon.module.i18n.presentation.dto.request.TranslationUpdateRequestDTO;
import com.icmon.module.i18n.presentation.dto.response.BulkImportResponseDTO;
import com.icmon.module.i18n.presentation.dto.response.TranslationResponseDTO;
import com.icmon.exception.SystemGlobalException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private final TranslationRepository translationRepository;
    private final TranslationMapper translationMapper;
    private final TranslationCacheService translationCacheService;

    @Override
    public Page<TranslationResponseDTO> listTranslations(String languageCode, String messageKey, Pageable pageable) throws SystemGlobalException {
        if (languageCode != null && !languageCode.isEmpty()) {
            return translationRepository.findByLanguageCodeAndContext(languageCode, messageKey, pageable)
                    .map(this::toResponseDTO);
        }
        if (messageKey != null && !messageKey.isEmpty()) {
            return translationRepository.findByMessageKeyContaining(messageKey, pageable)
                    .map(this::toResponseDTO);
        }
        return translationRepository.findAll(pageable).map(this::toResponseDTO);
    }

    @Override
    @Transactional
    public TranslationResponseDTO updateTranslation(UUID id, TranslationUpdateRequestDTO request) throws SystemGlobalException {
        TranslationEntity entity = translationRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Translation not found: " + id, null));
        entity.setMessageKey(request.getMessageKey());
        entity.setLanguageCode(request.getLanguageCode());
        entity.setMessageText(request.getMessageText());
        entity.setContext(request.getContext());
        entity.setDescription(request.getDescription());
        TranslationEntity saved = translationRepository.save(entity);
        translationCacheService.evictTranslation(saved.getMessageKey(), saved.getLanguageCode());
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public TranslationResponseDTO createTranslation(TranslationUpdateRequestDTO request) throws SystemGlobalException {
        TranslationEntity entity = new TranslationEntity();
        entity.setMessageKey(request.getMessageKey());
        entity.setLanguageCode(request.getLanguageCode());
        entity.setMessageText(request.getMessageText());
        entity.setContext(request.getContext());
        entity.setDescription(request.getDescription());
        entity.setVersion(1);
        TranslationEntity saved = translationRepository.save(entity);
        translationCacheService.evictTranslation(saved.getMessageKey(), saved.getLanguageCode());
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public void deleteTranslation(UUID id) throws SystemGlobalException {
        TranslationEntity entity = translationRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Translation not found: " + id, null));
        translationCacheService.evictTranslation(entity.getMessageKey(), entity.getLanguageCode());
        translationRepository.delete(entity);
    }

    @Override
    @Transactional
    public BulkImportResponseDTO bulkImportTranslations(BulkImportRequestDTO request) throws SystemGlobalException {
        int imported = 0;
        int updated = 0;
        int failed = 0;
        List<String> errors = new ArrayList<>();

        for (TranslationUpdateRequestDTO item : request.getTranslations()) {
            try {
                if (item.getId() != null && translationRepository.findById(item.getId()).isPresent()) {
                    updateTranslation(item.getId(), item);
                    updated++;
                } else {
                    createTranslation(item);
                    imported++;
                }
            } catch (Exception e) {
                failed++;
                errors.add("Failed to process translation for key: " + item.getMessageKey() + " - " + e.getMessage());
            }
        }

        return BulkImportResponseDTO.builder()
                .imported(imported)
                .updated(updated)
                .failed(failed)
                .errors(errors)
                .build();
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
