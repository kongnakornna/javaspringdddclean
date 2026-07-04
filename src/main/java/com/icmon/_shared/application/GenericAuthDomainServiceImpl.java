package com.icmon._shared.application;

import org.apache.coyote.BadRequestException;
import org.slf4j.MDC;

import com.icmon._shared.infrastructure.RepositoryAuth;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.ApplicationException;
import com.icmon.exception.models.FailedRequestException;

import java.util.UUID;

abstract class GenericAuthDomainServiceImpl {
    protected UUID getUserId() throws SystemGlobalException {
        try {
            String userIdStr = MDC.get("userId");

            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                throw new BadRequestException("UserId in request is null!", null);
            }

            return UUID.fromString(userIdStr);
        } catch (BadRequestException | IllegalArgumentException e) {
            throw new FailedRequestException("Invalid userId format!", e);
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong checking for user business info.", e);
        }
    }

    protected UUID getWhitelabelId() throws SystemGlobalException {
        try {
            String userIdStr = MDC.get("whitelabelId");

            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                throw new BadRequestException("WhitelabelId in request is null!", null);
            }

            return UUID.fromString(userIdStr);
        } catch (BadRequestException | IllegalArgumentException e) {
            throw new FailedRequestException("Invalid whitelabelId format!", e);
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong checking for whitelabel business info.", e);
        }
    }

    protected UUID getRequestId() throws SystemGlobalException {
        try {
            String requestIdStr = MDC.get("requestId");
            return UUID.fromString(requestIdStr);
        } catch (IllegalArgumentException e) {
            throw new FailedRequestException("Invalid request ID format.", e);
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong checking for web request info.", e);
        }
    }

    protected RepositoryAuth getRepositoryAuth() throws SystemGlobalException {
        return new RepositoryAuth(getUserId(), getWhitelabelId(), getRequestId());
    }
}
