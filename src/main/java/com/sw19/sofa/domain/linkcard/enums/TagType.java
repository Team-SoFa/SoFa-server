package com.sw19.sofa.domain.linkcard.enums;

import com.sw19.sofa.global.common.converter.AbstractCodedEnumConverter;
import com.sw19.sofa.global.common.converter.CodedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagType implements CodedEnum<Integer> {
    AI(10),CUSTOM(20);
    private final Integer code;

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends AbstractCodedEnumConverter<TagType, Integer> {
        public Converter() {
            super(TagType.class);
        }
    }
}
