package com.icmon.module.i18n.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "m_language")
@Getter
@Setter
public class LanguageEntity extends GenericBusinessEntity {

    @Column(name = "language_code", unique = true, nullable = false, length = 10)
    private String languageCode;

    @Column(name = "language_name", nullable = false, length = 100)
    private String languageName;

    @Column(name = "language_name_en", length = 100)
    private String languageNameEn;

    @Column(name = "flag_emoji", length = 10)
    private String flagEmoji;

    @Column(name = "is_rtl")
    private Boolean isRtl;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(length = 20)
    private String locale;

    @Column(name = "date_format", length = 50)
    private String dateFormat;

    @Column(name = "time_format", length = 50)
    private String timeFormat;

    @Column(name = "number_format", length = 50)
    private String numberFormat;

    @Column(name = "currency_symbol", length = 10)
    private String currencySymbol;
}
