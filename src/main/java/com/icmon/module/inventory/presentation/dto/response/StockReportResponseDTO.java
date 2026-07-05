package com.icmon.module.inventory.presentation.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class StockReportResponseDTO {
    private byte[] content;
    private String filename;
    private String contentType;

    public StockReportResponseDTO(byte[] content, String filename, String contentType) {
        this.content = content;
        this.filename = filename;
        this.contentType = contentType;
    }
}
