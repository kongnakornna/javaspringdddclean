package com.icmon.module.i18n.application.interfaces;

import com.icmon.module.i18n.presentation.dto.response.MessageResponseDTO;
import com.icmon.exception.SystemGlobalException;

import java.util.Map;

public interface MessageService {
    MessageResponseDTO getMessage(String messageKey, String languageCode) throws SystemGlobalException;
    Map<String, String> getAllMessages(String languageCode) throws SystemGlobalException;
}
