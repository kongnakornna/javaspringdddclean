package com.icmon.module.i18n.infrastructure.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Component
public class CustomLocaleResolver implements LocaleResolver {

    private static final String COOKIE_NAME = "lang";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lang = request.getParameter(COOKIE_NAME);
        if (lang != null && !lang.isEmpty()) {
            return Locale.forLanguageTag(lang);
        }

        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    String value = cookie.getValue();
                    if (value != null && !value.isEmpty()) {
                        return Locale.forLanguageTag(value);
                    }
                }
            }
        }

        String acceptLanguage = request.getHeader("Accept-Language");
        if (acceptLanguage != null && !acceptLanguage.isEmpty()) {
            return Locale.LanguageRange.parse(acceptLanguage).stream()
                    .findFirst()
                    .map(range -> Locale.forLanguageTag(range.getRange()))
                    .orElse(Locale.forLanguageTag("th"));
        }

        return Locale.forLanguageTag("th");
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie(COOKIE_NAME, locale.toLanguageTag());
        cookie.setPath("/");
        cookie.setMaxAge(30 * 24 * 60 * 60);
        response.addCookie(cookie);
    }
}
