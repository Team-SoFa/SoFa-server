package com.sw19.sofa.global.common.dto;

import org.springframework.http.ResponseEntity;

public class BaseResponse<T> {
    public static <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }
}
