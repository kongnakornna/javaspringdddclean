package com.icmon._shared.application.usecase;

import com.icmon.exception.SystemGlobalException;

public abstract class NullOutputUseCase<IN, SERVICE> {
    protected final SERVICE service;
    protected NullOutputUseCase(SERVICE service) {
        this.service = service;
    }

    public abstract void execute(IN input) throws SystemGlobalException;
}
