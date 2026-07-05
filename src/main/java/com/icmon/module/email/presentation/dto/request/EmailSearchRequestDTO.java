package com.icmon.module.email.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "คำค้นหาประวัติอีเมล // Email history search request")
public class EmailSearchRequestDTO {

    @Schema(description = "สถานะ // Status (PENDING, SENT, FAILED, BOUNCED)")
    private String status;

    @Schema(description = "รหัสเทมเพลต // Template code")
    private String templateCode;

    @Schema(description = "อีเมลผู้รับ // Recipient email")
    private String toEmail;
}
