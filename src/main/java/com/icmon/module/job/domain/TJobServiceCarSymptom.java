package com.icmon.module.job.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TJobServiceCarSymptom extends GenericBusinessClass {

    private UUID jobId;
    private String symptomCode;
    private String symptomDescription;
    // LOW, MEDIUM, HIGH, CRITICAL
    private String severity;
    private String reportedBy;
}
