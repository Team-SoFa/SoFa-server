package com.sw19.sofa.domain.member.entity.enums;

import com.sw19.sofa.global.common.converter.AbstractCodedEnumConverter;
import com.sw19.sofa.global.common.converter.CodedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority implements CodedEnum<Integer> {

    USER("ROLE_USER",1), ADMIN("ROLE_ADMIN",2);

    private final String key;
    private final Integer code;

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends AbstractCodedEnumConverter<Authority, Integer> {
        public Converter() {
            super(Authority.class);
        }
    }
}
