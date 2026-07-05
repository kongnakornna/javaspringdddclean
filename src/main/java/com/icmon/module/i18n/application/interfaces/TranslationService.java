package com.icmon.module.i18n.application.interfaces;

import com.icmon.module.i18n.presentation.dto.request.BulkImportRequestDTO;
import com.icmon.module.i18n.presentation.dto.request.TranslationUpdateRequestDTO;
import com.icmon.module.i18n.presentation.dto.response.BulkImportResponseDTO;
import com.icmon.module.i18n.presentation.dto.response.TranslationResponseDTO;
import com.icmon.exception.SystemGlobalException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TranslationService {
    Page<TranslationResponseDTO> listTranslations(String languageCode, String messageKey, Pageable pageable) throws SystemGlobalException;
    TranslationResponseDTO updateTranslation(UUID id, TranslationUpdateRequestDTO request) throws SystemGlobalException;
    TranslationResponseDTO createTranslation(TranslationUpdateRequestDTO request) throws SystemGlobalException;
    void deleteTranslation(UUID id) throws SystemGlobalException;
    BulkImportResponseDTO bulkImportTranslations(BulkImportRequestDTO request) throws SystemGlobalException;
}
