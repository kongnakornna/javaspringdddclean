package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.request.InventoryReceiveRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.InventoryIssueRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.InventoryResponseDTO;
import java.util.List;
import java.util.UUID;

public interface InventoryService {
    InventoryResponseDTO receiveGoods(InventoryReceiveRequestDTO request) throws SystemGlobalException;
    InventoryResponseDTO issueGoods(InventoryIssueRequestDTO request) throws SystemGlobalException;
    List<InventoryResponseDTO> getInventoryByPartId(UUID partId) throws SystemGlobalException;
}
