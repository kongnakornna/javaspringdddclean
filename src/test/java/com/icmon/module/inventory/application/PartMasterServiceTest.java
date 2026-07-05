package com.icmon.module.inventory.application;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.inventory.application.impl.PartMasterServiceImpl;
import com.icmon.module.inventory.infrastructure.cache.PartMasterCacheService;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import com.icmon.module.inventory.infrastructure.mapper.PartMasterMapper;
import com.icmon.module.inventory.infrastructure.repository.PartMasterRepository;
import com.icmon.module.inventory.presentation.dto.request.PartMasterCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PartMasterResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartMasterServiceTest {

    @Mock
    private PartMasterRepository repository;
    @Mock
    private PartMasterMapper mapper;
    @Mock
    private PartMasterCacheService cacheService;

    private PartMasterServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new PartMasterServiceImpl(repository, mapper, cacheService);
    }

    @Test
    void shouldCreatePartSuccessfully() throws Exception {
        PartMasterCreateRequestDTO request = new PartMasterCreateRequestDTO();
        request.setPartCode("BRK-001");
        request.setPartName("Brake Pad");

        when(repository.findByPartCode("BRK-001")).thenReturn(Optional.empty());
        when(repository.save(any(PartMasterEntity.class))).thenAnswer(invocation -> {
            PartMasterEntity entity = invocation.getArgument(0);
            entity.setId(UUID.randomUUID());
            return entity;
        });

        PartMasterResponseDTO result = service.createPart(request);
        assertNotNull(result);
        assertEquals("BRK-001", request.getPartCode());

        verify(repository).save(any(PartMasterEntity.class));
        verify(cacheService).cache(any(PartMasterEntity.class));
    }

    @Test
    void shouldThrowWhenPartCodeExists() {
        PartMasterCreateRequestDTO request = new PartMasterCreateRequestDTO();
        request.setPartCode("BRK-001");
        request.setPartName("Brake Pad");

        when(repository.findByPartCode("BRK-001")).thenReturn(Optional.of(new PartMasterEntity()));

        assertThrows(FailedRequestException.class, () -> service.createPart(request));
        verify(repository, never()).save(any());
    }

    @Test
    void shouldGetPartById() throws Exception {
        UUID id = UUID.randomUUID();
        PartMasterEntity entity = new PartMasterEntity();
        entity.setId(id);
        entity.setPartCode("BRK-001");
        entity.setPartName("Brake Pad");

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        PartMasterResponseDTO result = service.getPart(id);
        assertNotNull(result);
    }

    @Test
    void shouldThrowWhenPartNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(FailedRequestException.class, () -> service.getPart(id));
    }

    @Test
    void shouldDeletePart() throws Exception {
        UUID id = UUID.randomUUID();
        PartMasterEntity entity = new PartMasterEntity();
        entity.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        service.deletePart(id);
        assertTrue(entity.getDeleted());
        verify(repository).save(entity);
        verify(cacheService).evict(id);
    }
}
