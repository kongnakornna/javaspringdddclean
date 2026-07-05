package com.icmon.module.quotation.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import com.icmon.module.quotation.domain.enums.QuotationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_quotation_status_history")
@EqualsAndHashCode(callSuper = true)
public class QuotationStatusHistoryEntity extends GenericBusinessEntity {

    @Column(name = "quotation_id", nullable = false)
    private UUID quotationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_status", length = 20)
    private QuotationStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_status", nullable = false, length = 20)
    private QuotationStatus toStatus;

    @Column(name = "changed_by", nullable = false)
    private UUID changedBy;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @Column(columnDefinition = "TEXT")
    private String reason;
}
