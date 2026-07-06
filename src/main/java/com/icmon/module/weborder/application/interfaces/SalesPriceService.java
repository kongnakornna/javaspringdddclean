package com.icmon.module.weborder.application.interfaces;

import com.icmon.module.weborder.presentation.dto.response.SalesPriceResponseDTO;
import com.icmon.exception.SystemGlobalException;

import java.util.List;
import java.util.UUID;

public interface SalesPriceService {
    List<SalesPriceResponseDTO> getPricesByItem(UUID itemId) throws SystemGlobalException;

    SalesPriceResponseDTO createPrice(SalesPriceResponseDTO request) throws SystemGlobalException;

    SalesPriceResponseDTO updatePrice(UUID id, SalesPriceResponseDTO request) throws SystemGlobalException;

    void deletePrice(UUID id) throws SystemGlobalException;
}
