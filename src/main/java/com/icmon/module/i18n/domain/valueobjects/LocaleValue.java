package com.icmon.module.i18n.domain.valueobjects;

import lombok.Value;
import java.util.Locale;

@Value
public class LocaleValue {
    String languageCode;
    String country;
    String variant;

    public Locale toLocale() {
        if (variant != null) {
            return new Locale(languageCode, country, variant);
        }
        if (country != null) {
            return new Locale(languageCode, country);
        }
        return new Locale(languageCode);
    }

    public static LocaleValue fromLocale(Locale locale) {
        return new LocaleValue(
            locale.getLanguage(),
            locale.getCountry(),
            locale.getVariant()
        );
    }
}
