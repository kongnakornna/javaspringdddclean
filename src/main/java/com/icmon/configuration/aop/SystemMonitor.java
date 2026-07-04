package com.icmon.configuration.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.icmon.logging.LogService;
import com.icmon.logging.infrastrutcture.ErrorLogSchema;
import com.icmon.logging.infrastrutcture.MethodCallLogSchema;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Aspect
@Component
@Profile({"dev", "prod"})
public class SystemMonitor {

    private static final Logger log = LoggerFactory.getLogger(SystemMonitor.class);

    private final LogService logService;
    private final LoggingProperties loggingProperties;

    public SystemMonitor(LogService logService, LoggingProperties loggingProperties) {
        this.logService = logService;
        this.loggingProperties = loggingProperties;
    }

    @Around("execution(* com.icmon.module..application..*(..)) || "
            + "execution(* com.icmon.module..infrastructure..*(..))")
    public Object domainMonitor(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!loggingProperties.isEnabled()) {
            return joinPoint.proceed();
        }

        String requestId = MDC.get("requestId");
        String userId    = MDC.get("userId");
        String companyId = MDC.get("whitelabelId");

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();

            if (loggingProperties.isLogMethodCalls()) {
                long elapsed = System.currentTimeMillis() - start;
                MethodCallLogSchema methodCallLog = buildMethodCallLog(joinPoint, requestId, userId, companyId);
                methodCallLog.setError(false);
                methodCallLog.setReturnType(String.valueOf(elapsed) + "ms");
                log.info("[CALL] {}.{}() - {}ms",
                        methodCallLog.getClassName(), methodCallLog.getMethodName(), elapsed);
                logService.saveMethodCallLogAsync(methodCallLog);
            }

            return result;
        } catch (Exception ex) {
            MethodCallLogSchema methodCallLog = buildMethodCallLog(joinPoint, requestId, userId, companyId);
            methodCallLog.setError(true);
            log.error("[ERROR] {}.{}() - {}",
                    methodCallLog.getClassName(), methodCallLog.getMethodName(), ex.getMessage());

            if (loggingProperties.isLogMethodCalls()) {
                logService.saveMethodCallLogAsync(methodCallLog);
            }
            if (loggingProperties.isLogErrors()) {
                ErrorLogSchema errorLog = buildErrorLog(ex, requestId, userId, companyId, methodCallLog.getId());
                logService.saveErrorLogAsync(errorLog);
            }

            MDC.put("method_id", methodCallLog.getId());
            throw ex;
        }
    }

    private MethodCallLogSchema buildMethodCallLog(
            ProceedingJoinPoint joinPoint,
            String requestId,
            String userId,
            String companyId
    ) {
        MethodCallLogSchema m = new MethodCallLogSchema();
        m.setMethodName(joinPoint.getSignature().getName());
        m.setClassName(joinPoint.getTarget().getClass().getSimpleName());
        m.setArguments(formatArguments(joinPoint.getArgs()));
        // แก้ไขตรงนี้: เพิ่ม default value ", "" " เข้าไป
        m.setRequestId(defaultIfNull(requestId, ""));
        m.setUserId(defaultIfNull(userId, ""));
        m.setCompanyId(defaultIfNull(companyId, ""));
        m.setTimestamp(new Date());
        return m;
    }

    private ErrorLogSchema buildErrorLog(
            Exception ex,
            String requestId,
            String userId,
            String companyId,
            String methodId
    ) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));

        ErrorLogSchema e = new ErrorLogSchema();
        e.setErrorMessage(ex.getMessage());
        e.setErrorStackTrace(sw.toString());
        e.setErrorCause(ex.getCause() != null ? ex.getCause().getMessage() : null);
        // แก้ไขตรงนี้: เพิ่ม default value ", "" " เข้าไป
        e.setRequestId(defaultIfNull(requestId, ""));
        e.setUserId(defaultIfNull(userId, ""));
        e.setCompanyId(defaultIfNull(companyId, ""));
        e.setMethodId(defaultIfNull(methodId, ""));
        e.setTimestamp(new Date());
        return e;
    }

    private String formatArguments(Object[] args) {
        if (args == null || args.length == 0) return "[]";
        return Arrays.stream(args)
                .map(a -> a != null ? a.toString() : "null")
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }
}