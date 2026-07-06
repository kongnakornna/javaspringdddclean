package com.icmon.module.inventory.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.infrastructure.entity.PartPickingRequestEntity;
import com.icmon.module.inventory.infrastructure.repository.PartPickingDetailRepository;
import com.icmon.module.inventory.infrastructure.repository.PartPickingRepository;
import com.icmon.module.inventory.presentation.dto.request.PickingCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PickingResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Part Picking Service Tests")
class PartPickingServiceImplTest {

    @Mock private PartPickingRepository pickingRepository;
    @Mock private PartPickingDetailRepository detailRepository;
    @InjectMocks private PartPickingServiceImpl partPickingService;

    @Test
    @DisplayName("should create picking request")
    void shouldCreatePicking() throws SystemGlobalException {
        PickingCreateRequestDTO request = new PickingCreateRequestDTO();
        request.setJobId(UUID.randomUUID());

        PartPickingRequestEntity savedEntity = new PartPickingRequestEntity();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setJobId(request.getJobId());
        savedEntity.setStatus("DRAFT");

        when(pickingRepository.save(any())).thenReturn(savedEntity);

        PickingResponseDTO result = partPickingService.createPicking(request);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("DRAFT");
        verify(pickingRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("should confirm picking request")
    void shouldConfirmPicking() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        PartPickingRequestEntity entity = new PartPickingRequestEntity();
        entity.setId(id);
        entity.setStatus("DRAFT");

        when(pickingRepository.findById(id)).thenReturn(java.util.Optional.of(entity));
        when(pickingRepository.save(any())).thenReturn(entity);

        PickingResponseDTO result = partPickingService.confirmPicking(id);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("CONFIRMED");
    }
}
