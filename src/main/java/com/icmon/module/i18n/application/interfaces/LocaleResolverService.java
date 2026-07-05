package com.icmon.module.i18n.application.interfaces;

import java.util.Locale;

public interface LocaleResolverService {
    Locale resolveLocale(String languageCode);
    Locale getDefaultLocale();
}
