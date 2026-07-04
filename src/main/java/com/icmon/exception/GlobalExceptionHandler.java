package com.icmon.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icmon.exception.models.*;
import com.icmon.logging.LogService;
import com.icmon.logging.infrastrutcture.ErrorLogSchema;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestControllerAdvice
@Profile({"dev", "prod"})
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final LogService logService;

    @ExceptionHandler(AdapterException.class)
    public ResponseEntity<Object> handleAdapterException(AdapterException ex, WebRequest request) {
        buildErrorLog(ex,
                MDC.get("userId"),
                MDC.get("companyId"),
                MDC.get("requestId"),
                MDC.get("methodId"));

        logger.error(String.valueOf(ex.getCause()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException ex, WebRequest request) {
        buildErrorLog(ex,
                MDC.get("userId"),
                MDC.get("companyId"),
                MDC.get("requestId"),
                MDC.get("methodId"));

        logger.error(String.valueOf(ex.getCause()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException ex, WebRequest request) {
        buildErrorLog(ex,
                MDC.get("userId"),
                MDC.get("companyId"),
                MDC.get("requestId"),
                MDC.get("methodId"));

        logger.error(String.valueOf(ex.getCause()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<Object> handleInfraException(InfrastructureException ex, WebRequest request) {
        buildErrorLog(ex,
                MDC.get("userId"),
                MDC.get("companyId"),
                MDC.get("requestId"),
                MDC.get("methodId"));

        logger.error(String.valueOf(ex.getCause()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(FailedRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(FailedRequestException ex, WebRequest request) {
        buildErrorLog(ex,
                MDC.get("userId"),
                MDC.get("companyId"),
                MDC.get("requestId"),
                MDC.get("methodId"));

        logger.error(String.valueOf(ex.getCause()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Object> handleJacksonException(JsonProcessingException ex, WebRequest request) {
        buildErrorLog(ex,
                MDC.get("userId"),
                MDC.get("companyId"),
                MDC.get("requestId"),
                MDC.get("methodId"));

        logger.error(ex.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpException(HttpMessageNotReadableException ex, WebRequest request) {
        buildErrorLog(ex,
                MDC.get("userId"),
                MDC.get("companyId"),
                MDC.get("requestId"),
                MDC.get("methodId"));

        logger.error(ex.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        buildErrorLog(ex,
                MDC.get("userId"),
                MDC.get("companyId"),
                MDC.get("requestId"),
                MDC.get("methodId"));

        logger.error(ex.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        buildErrorLog(ex,
                MDC.get("userId"),
                MDC.get("companyId"),
                MDC.get("requestId"),
                MDC.get("methodId"));

        logger.error(String.valueOf(ex.getCause()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong. Error is likely unmapped, please contact support.");
    }

    @Async
    protected void buildErrorLog(Exception ex,
                                 String userId,
                                 String companyId,
                                 String requestId,
                                 String methodId) {
        ErrorLogSchema errorLog = new ErrorLogSchema();
        errorLog.setErrorMessage(ex.getMessage());
        errorLog.setErrorStackTrace(formatStackTrace(ex.getStackTrace()));
        errorLog.setTimestamp(new Date());

        errorLog.setUserId(userId);
        errorLog.setCompanyId(companyId);
        errorLog.setRequestId(requestId);
        errorLog.setMethodId(methodId);

        logService.saveErrorLogAsync(errorLog);
    }

    private String formatStackTrace(StackTraceElement[] stackTrace) {
        return Arrays.stream(stackTrace)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
    }
}