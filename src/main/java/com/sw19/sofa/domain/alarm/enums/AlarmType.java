package com.sw19.sofa.domain.alarm.enums;

import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.global.common.converter.AbstractCodedEnumConverter;
import com.sw19.sofa.global.common.converter.CodedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType implements CodedEnum<Integer> {
    REMIND(10),RECOMMEND(10),UPDATE(30),NOTICE(40);
    private final Integer code;

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends AbstractCodedEnumConverter<TagType, Integer> {
        public Converter() {
            super(TagType.class);
        }
    }
}
