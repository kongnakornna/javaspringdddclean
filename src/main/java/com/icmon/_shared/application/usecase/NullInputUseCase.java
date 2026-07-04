package com.icmon._shared.application.usecase;

import com.icmon.exception.SystemGlobalException;

public abstract class NullInputUseCase<OUT, SERVICE> {
    protected final SERVICE service;

    public NullInputUseCase(SERVICE service) {
        this.service = service;
    }

    public abstract OUT execute() throws SystemGlobalException;
}