package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.request.StocktakeCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.StocktakeDetailRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StocktakeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface StocktakeService {
    StocktakeResponseDTO createStocktake(StocktakeCreateRequestDTO request) throws SystemGlobalException;
    StocktakeResponseDTO getStocktake(UUID id) throws SystemGlobalException;
    StocktakeResponseDTO getStocktakeByNo(String stocktakeNo) throws SystemGlobalException;
    Page<StocktakeResponseDTO> listStocktakes(String status, Pageable pageable) throws SystemGlobalException;
    StocktakeResponseDTO addDetail(UUID stocktakeId, StocktakeDetailRequestDTO request) throws SystemGlobalException;
    StocktakeResponseDTO completeStocktake(UUID id) throws SystemGlobalException;
    void deleteStocktake(UUID id) throws SystemGlobalException;
}
