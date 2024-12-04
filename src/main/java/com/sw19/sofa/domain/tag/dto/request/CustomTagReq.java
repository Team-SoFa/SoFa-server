package com.sw19.sofa.domain.tag.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomTagReq {
    @NotBlank(message = "태그 이름 추가 해주세요.")
    private String name;

    private String id;
}