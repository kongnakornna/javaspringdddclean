package com.icmon.module.job.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TJobDiagTroubleCode extends GenericBusinessClass {

    private UUID jobId;
    // รหัส OBD2 เช่น P0300, P0420
    private String troubleCode;
    private String description;
    // ระบบที่เกี่ยวข้อง เช่น Engine, ABS
    private String system;
}
