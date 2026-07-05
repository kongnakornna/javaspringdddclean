package com.icmon.module.i18n.domain.enums;

public enum LanguageCode {
    TH("th", "ภาษาไทย", "Thai"),
    EN("en", "English", "English"),
    ZH("zh", "中文", "Chinese"),
    JA("ja", "日本語", "Japanese"),
    KO("ko", "한국어", "Korean"),
    ES("es", "Español", "Spanish"),
    FR("fr", "Français", "French"),
    DE("de", "Deutsch", "German"),
    IT("it", "Italiano", "Italian"),
    PT("pt", "Português", "Portuguese"),
    RU("ru", "Русский", "Russian"),
    AR("ar", "العربية", "Arabic"),
    HI("hi", "हिन्दी", "Hindi"),
    ID("id", "Bahasa Indonesia", "Indonesian"),
    MS("ms", "Bahasa Melayu", "Malay"),
    VI("vi", "Tiếng Việt", "Vietnamese"),
    MY("my", "မြန်မာဘာသာ", "Burmese"),
    LO("lo", "ພາສາລາວ", "Lao");

    private final String code;
    private final String name;
    private final String nameEn;

    LanguageCode(String code, String name, String nameEn) {
        this.code = code; this.name = name; this.nameEn = nameEn;
    }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getNameEn() { return nameEn; }

    public static LanguageCode fromCode(String code) {
        for (LanguageCode lang : values()) {
            if (lang.code.equalsIgnoreCase(code)) return lang;
        }
        return TH;
    }
}
