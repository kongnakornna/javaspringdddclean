package com.icmon.module.i18n.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.i18n.domain.enums.LanguageCode;
import java.util.Locale;

public class MLanguage extends GenericBusinessClass {

    private String languageCode;
    private String languageName;
    private String languageNameEn;
    private String flagEmoji;
    private Boolean isRtl;
    private Boolean isActive;
    private Boolean isDefault;
    private Integer sortOrder;
    private String locale;
    private String dateFormat;
    private String timeFormat;
    private String numberFormat;
    private String currencySymbol;

    public boolean isDefault() {
        return Boolean.TRUE.equals(isDefault);
    }

    public boolean isUsable() {
        return Boolean.TRUE.equals(isActive) && languageCode != null;
    }

    public Locale toLocale() {
        if (locale != null && locale.contains("_")) {
            String[] parts = locale.split("_");
            if (parts.length >= 2) {
                return new Locale(parts[0], parts[1]);
            }
        }
        if (languageCode != null) {
            return new Locale(languageCode);
        }
        return Locale.ENGLISH;
    }

    public String getLanguageCode() { return languageCode; }
    public void setLanguageCode(String languageCode) { this.languageCode = languageCode; }
    public String getLanguageName() { return languageName; }
    public void setLanguageName(String languageName) { this.languageName = languageName; }
    public String getLanguageNameEn() { return languageNameEn; }
    public void setLanguageNameEn(String languageNameEn) { this.languageNameEn = languageNameEn; }
    public String getFlagEmoji() { return flagEmoji; }
    public void setFlagEmoji(String flagEmoji) { this.flagEmoji = flagEmoji; }
    public Boolean getIsRtl() { return isRtl; }
    public void setRtl(Boolean rtl) { isRtl = rtl; }
    public Boolean getIsActive() { return isActive; }
    public void setActive(Boolean active) { isActive = active; }
    public Boolean getIsDefault() { return isDefault; }
    public void setDefault(Boolean aDefault) { isDefault = aDefault; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public String getLocale() { return locale; }
    public void setLocale(String locale) { this.locale = locale; }
    public String getDateFormat() { return dateFormat; }
    public void setDateFormat(String dateFormat) { this.dateFormat = dateFormat; }
    public String getTimeFormat() { return timeFormat; }
    public void setTimeFormat(String timeFormat) { this.timeFormat = timeFormat; }
    public String getNumberFormat() { return numberFormat; }
    public void setNumberFormat(String numberFormat) { this.numberFormat = numberFormat; }
    public String getCurrencySymbol() { return currencySymbol; }
    public void setCurrencySymbol(String currencySymbol) { this.currencySymbol = currencySymbol; }
}
