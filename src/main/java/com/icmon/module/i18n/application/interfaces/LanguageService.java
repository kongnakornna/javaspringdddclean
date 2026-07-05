package com.icmon.module.i18n.application.interfaces;

import com.icmon.module.i18n.presentation.dto.response.LanguageResponseDTO;
import com.icmon.exception.SystemGlobalException;

import java.util.List;
import java.util.Locale;

public interface LanguageService {
    List<LanguageResponseDTO> getAllLanguages() throws SystemGlobalException;
    LanguageResponseDTO getCurrentLanguage() throws SystemGlobalException;
    LanguageResponseDTO switchLanguage(String languageCode, Locale locale) throws SystemGlobalException;
    LanguageResponseDTO getDefaultLanguage() throws SystemGlobalException;
}
