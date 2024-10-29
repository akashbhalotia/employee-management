package com.cercli.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Locale;

/**
 * JPA Attribute Converter for Locale.
 */
@Converter(autoApply = true)
public class LocaleConverter implements AttributeConverter<Locale, String> {
    @Override
    public String convertToDatabaseColumn(Locale locale) {
        return locale != null ? locale.toLanguageTag() : null;
    }

    @Override
    public Locale convertToEntityAttribute(String dbData) {
        return dbData != null ? Locale.forLanguageTag(dbData) : null;
    }
}
