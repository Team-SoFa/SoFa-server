package com.sw19.sofa.global.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Objects;

@Converter
public abstract class AbstractCodedEnumConverter<T extends Enum<T> & CodedEnum<E>, E> implements AttributeConverter<T, E> {
    private final Class<T> data;

    public AbstractCodedEnumConverter(Class<T> data) {
        this.data = data;
    }

    @Override
    public E convertToDatabaseColumn(T attribute) { 
        return attribute.getCode();
    }

    @Override
    public T convertToEntityAttribute(E code) {
        if (Objects.isNull(code)) {
            return null;
        }
        return Arrays.stream(data.getEnumConstants())
            .filter(e -> e.getCode().equals(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + code));
    }
}