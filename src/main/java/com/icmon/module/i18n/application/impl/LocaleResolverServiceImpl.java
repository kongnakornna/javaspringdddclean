package com.icmon.module.i18n.application.impl;

import com.icmon.module.i18n.application.interfaces.LocaleResolverService;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleResolverServiceImpl implements LocaleResolverService {

    @Override
    public Locale resolveLocale(String languageCode) {
        if (languageCode == null || languageCode.isEmpty()) {
            return getDefaultLocale();
        }
        return Locale.forLanguageTag(languageCode);
    }

    @Override
    public Locale getDefaultLocale() {
        return Locale.forLanguageTag("th");
    }
}
