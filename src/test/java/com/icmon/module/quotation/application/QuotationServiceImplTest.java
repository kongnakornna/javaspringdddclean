package com.icmon.module.quotation.application;

import com.icmon.module.quotation.application.impl.QuotationServiceImpl;
import com.icmon.module.quotation.domain.enums.QuotationStatus;
import com.icmon.module.quotation.infrastructure.cache.QuotationCacheService;
import com.icmon.module.quotation.infrastructure.entity.QuotationEntity;
import com.icmon.module.quotation.infrastructure.entity.QuotationStatusHistoryEntity;
import com.icmon.module.quotation.infrastructure.mapper.QuotationMapper;
import com.icmon.module.quotation.infrastructure.repository.QuotationRepository;
import com.icmon.module.quotation.infrastructure.repository.QuotationStatusHistoryRepository;
import com.icmon.module.quotation.presentation.dto.request.QuotationApproveRequestDTO;
import com.icmon.module.quotation.presentation.dto.request.QuotationCreateRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationResponseDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuotationServiceImplTest {

    @Mock
    private QuotationRepository quotationRepository;
    @Mock
    private QuotationStatusHistoryRepository statusHistoryRepository;
    @Mock
    private QuotationMapper quotationMapper;
    @Mock
    private QuotationCacheService cacheService;

    private QuotationServiceImpl quotationService;

    private UUID testUserId;
    private UUID testWhitelabelId;

    @BeforeEach
    void setUp() {
        quotationService = new QuotationServiceImpl(
                quotationRepository, statusHistoryRepository, quotationMapper, cacheService
        );
        testUserId = UUID.randomUUID();
        testWhitelabelId = UUID.randomUUID();
        MDC.put("userId", testUserId.toString());
        MDC.put("whitelabelId", testWhitelabelId.toString());
        MDC.put("requestId", UUID.randomUUID().toString());
    }

    @Test
    void testCreateQuotationSuccess() throws SystemGlobalException {
        QuotationCreateRequestDTO request = new QuotationCreateRequestDTO();
        UUID jobId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        request.setJobId(jobId);
        request.setCustomerId(customerId);

        QuotationEntity savedEntity = new QuotationEntity();
        UUID entityId = UUID.randomUUID();
        savedEntity.setId(entityId);
        savedEntity.setQuotationNo("QT-2026-0001");
        savedEntity.setJobId(jobId);
        savedEntity.setCustomerId(customerId);
        savedEntity.setStatus(QuotationStatus.DRAFT);
        savedEntity.setSubtotal(BigDecimal.ZERO);
        savedEntity.setTaxRate(new BigDecimal("7.00"));
        savedEntity.setCurrency("THB");
        savedEntity.setTotal(BigDecimal.ZERO);

        when(quotationRepository.save(any(QuotationEntity.class))).thenReturn(savedEntity);

        com.icmon.module.quotation.domain.TQuotation domain = new com.icmon.module.quotation.domain.TQuotation();
        domain.setId(entityId);
        domain.setQuotationNo("QT-2026-0001");
        domain.setJobId(jobId);
        domain.setCustomerId(customerId);
        domain.setStatus(QuotationStatus.DRAFT);
        domain.setCreatedAt(LocalDateTime.now());

        when(quotationMapper.toDomain(any(QuotationEntity.class))).thenReturn(domain);

        QuotationResponseDTO response = quotationService.createQuotation(request);

        assertNotNull(response);
        assertEquals("QT-2026-0001", response.getQuotationNo());
        assertEquals(QuotationStatus.DRAFT, response.getStatus());
        verify(quotationRepository).save(any(QuotationEntity.class));
    }

    @Test
    void testGetQuotationNotFound() {
        UUID id = UUID.randomUUID();
        when(quotationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(FailedRequestException.class, () -> quotationService.getQuotation(id));
    }

    @Test
    void testDeleteQuotationSuccess() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        QuotationEntity entity = new QuotationEntity();
        entity.setId(id);
        entity.setStatus(QuotationStatus.DRAFT);

        when(quotationRepository.findById(id)).thenReturn(Optional.of(entity));

        quotationService.deleteQuotation(id);

        assertTrue(entity.getDeleted());
        assertNotNull(entity.getDeletedAt());
        verify(quotationRepository).save(entity);
    }

    @Test
    void testDeleteQuotationFailsWhenApproved() {
        UUID id = UUID.randomUUID();
        QuotationEntity entity = new QuotationEntity();
        entity.setId(id);
        entity.setStatus(QuotationStatus.APPROVED);

        when(quotationRepository.findById(id)).thenReturn(Optional.of(entity));

        assertThrows(FailedRequestException.class, () -> quotationService.deleteQuotation(id));
    }

    @Test
    void testApproveQuotationSuccess() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        QuotationEntity entity = new QuotationEntity();
        entity.setId(id);
        entity.setStatus(QuotationStatus.PENDING);
        entity.setExpiryDate(LocalDateTime.now().plusDays(7));

        when(quotationRepository.findById(id)).thenReturn(Optional.of(entity));
        when(quotationRepository.save(any(QuotationEntity.class))).thenAnswer(i -> i.getArgument(0));

        com.icmon.module.quotation.domain.TQuotation domain = new com.icmon.module.quotation.domain.TQuotation();
        domain.setId(id);
        domain.setStatus(QuotationStatus.APPROVED);
        domain.setApprovedBy(testUserId);
        domain.setApprovedAt(LocalDateTime.now());

        when(quotationMapper.toDomain(any(QuotationEntity.class))).thenReturn(domain);

        QuotationApproveRequestDTO approveRequest = new QuotationApproveRequestDTO();
        approveRequest.setAutoCreatePo(false);

        QuotationResponseDTO response = quotationService.approveQuotation(id, approveRequest);

        assertNotNull(response);
        assertEquals(QuotationStatus.APPROVED, domain.getStatus());
        verify(statusHistoryRepository).save(any(QuotationStatusHistoryEntity.class));
    }

    @Test
    void testRejectQuotationSuccess() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        QuotationEntity entity = new QuotationEntity();
        entity.setId(id);
        entity.setStatus(QuotationStatus.PENDING);

        when(quotationRepository.findById(id)).thenReturn(Optional.of(entity));
        when(quotationRepository.save(any(QuotationEntity.class))).thenAnswer(i -> i.getArgument(0));

        com.icmon.module.quotation.domain.TQuotation domain = new com.icmon.module.quotation.domain.TQuotation();
        domain.setId(id);
        domain.setStatus(QuotationStatus.REJECTED);
        domain.setRejectedReason("Customer not interested");

        when(quotationMapper.toDomain(any(QuotationEntity.class))).thenReturn(domain);

        QuotationResponseDTO response = quotationService.rejectQuotation(id, "Customer not interested");

        assertNotNull(response);
        assertEquals(QuotationStatus.REJECTED, domain.getStatus());
        assertEquals("Customer not interested", domain.getRejectedReason());
        verify(statusHistoryRepository).save(any(QuotationStatusHistoryEntity.class));
    }
}
