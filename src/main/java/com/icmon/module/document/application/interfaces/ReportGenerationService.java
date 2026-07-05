package com.icmon.module.document.application.interfaces;

import com.icmon.exception.SystemGlobalException;

public interface ReportGenerationService {
    byte[] generatePDF(String templateCode, String parameters) throws SystemGlobalException;
    byte[] generateExcel(String templateCode, String parameters) throws SystemGlobalException;
}
