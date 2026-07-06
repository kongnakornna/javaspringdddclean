package com.icmon.module.inventory.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.infrastructure.cache.PartCacheService;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import com.icmon.module.inventory.infrastructure.mapper.PartMasterMapper;
import com.icmon.module.inventory.infrastructure.repository.PartMasterRepository;
import com.icmon.module.inventory.presentation.dto.request.PartCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PartResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Part Master Service Tests")
class PartMasterServiceImplTest {

    @Mock private PartMasterRepository partMasterRepository;
    @Mock private PartMasterMapper partMasterMapper;
    @Mock private PartCacheService partCacheService;
    @InjectMocks private PartMasterServiceImpl partMasterService;

    @Test
    @DisplayName("should create part successfully")
    void shouldCreatePart() throws SystemGlobalException {
        PartCreateRequestDTO request = new PartCreateRequestDTO();
        request.setPartCode("TEST-001");
        request.setPartName("Test Part");

        PartMasterEntity savedEntity = new PartMasterEntity();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setPartCode("TEST-001");
        savedEntity.setPartName("Test Part");

        when(partMasterRepository.save(any())).thenReturn(savedEntity);

        PartResponseDTO result = partMasterService.createPart(request);

        assertThat(result).isNotNull();
        assertThat(result.getPartCode()).isEqualTo("TEST-001");
        verify(partMasterRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("should get part by ID")
    void shouldGetPartById() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        PartMasterEntity entity = new PartMasterEntity();
        entity.setId(id);
        entity.setPartCode("TEST-001");

        when(partMasterRepository.findById(id)).thenReturn(Optional.of(entity));

        PartResponseDTO result = partMasterService.getPartById(id);

        assertThat(result).isNotNull();
        assertThat(result.getPartCode()).isEqualTo("TEST-001");
    }

    @Test
    @DisplayName("should throw exception when part not found")
    void shouldThrowExceptionWhenPartNotFound() {
        UUID id = UUID.randomUUID();
        when(partMasterRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> partMasterService.getPartById(id))
                .isInstanceOf(SystemGlobalException.class)
                .hasMessageContaining("Part not found");
    }

    @Test
    @DisplayName("should get all parts paginated")
    void shouldGetAllParts() throws SystemGlobalException {
        PartMasterEntity entity = new PartMasterEntity();
        entity.setId(UUID.randomUUID());
        entity.setPartCode("TEST-001");

        when(partMasterRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(entity)));

        Page<PartResponseDTO> result = partMasterService.getAllParts(0, 20);

        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("should soft delete part")
    void shouldDeletePart() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        PartMasterEntity entity = new PartMasterEntity();
        entity.setId(id);
        entity.setPartCode("TEST-001");

        when(partMasterRepository.findById(id)).thenReturn(Optional.of(entity));
        when(partMasterRepository.save(any())).thenReturn(entity);

        partMasterService.deletePart(id);

        verify(partMasterRepository, times(1)).save(any());
        verify(partCacheService, times(1)).evictPart(id);
    }
}
