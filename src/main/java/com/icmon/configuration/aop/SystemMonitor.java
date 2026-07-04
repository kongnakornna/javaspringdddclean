package com.icmon.configuration.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.logging.LogService;
import com.icmon.logging.infrastrutcture.MethodCallLogSchema;

import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
@Profile({"dev", "prod"})
public class SystemMonitor {

    private final LogService logService;

    public SystemMonitor(LogService logService) {
        this.logService = logService;
    }

    @Around("execution(* com.template.app.modules..application..*(..)) || "
            + "execution(* com.template.app.modules..entity..*(..))")
    public Object domainMonitor(ProceedingJoinPoint joinPoint) throws Throwable {
        String requestId = MDC.get("requestId");
        String userId = MDC.get("userId");
        String companyId = MDC.get("whitelabelId");

        if (companyId == null || userId == null) {
            throw new FailedRequestException("Unauthorized request", null);
        }
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            MethodCallLogSchema methodCallLog = buildMethodCallLog(joinPoint, requestId, userId, companyId);
            methodCallLog.setError(true);
            logService.saveMethodCallLogSync(methodCallLog);
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
        MethodCallLogSchema methodCallLog = new MethodCallLogSchema();
        methodCallLog.setMethodName(joinPoint.getSignature().getName());
        methodCallLog.setClassName(joinPoint.getTarget().getClass().getSimpleName());
        methodCallLog.setArguments(formatArguments(joinPoint.getArgs()));
        methodCallLog.setRequestId(defaultIfNull(requestId));
        methodCallLog.setUserId(defaultIfNull(userId));
        methodCallLog.setCompanyId(defaultIfNull(companyId));
        methodCallLog.setTimestamp(new Date());
        return methodCallLog;
    }

    private String formatArguments(Object[] args) {
        if (args == null || args.length == 0) {
            return "[]";
        }
        return Arrays.stream(args)
                .map(Object::toString)
                .reduce((arg1, arg2) -> arg1 + ", " + arg2)
                .orElse("");
    }

    private String defaultIfNull(String value) {
        return value != null ? value : "N/A";
    }
}
