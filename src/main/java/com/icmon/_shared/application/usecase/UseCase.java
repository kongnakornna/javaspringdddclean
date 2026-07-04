package com.icmon._shared.application.usecase;

import com.icmon.exception.SystemGlobalException;

public abstract class UseCase<IN, OUT, SERVICE>{
    protected final SERVICE service;

    public UseCase(SERVICE service) {
        this.service = service;
    }

    public abstract OUT execute(IN input) throws SystemGlobalException;
}