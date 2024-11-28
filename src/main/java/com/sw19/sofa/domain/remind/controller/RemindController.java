package com.sw19.sofa.domain.remind.controller;

import com.sw19.sofa.domain.remind.api.RemindApi;
import com.sw19.sofa.domain.remind.service.RemindManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RemindController implements RemindApi {
    private final RemindManageService remindManageService;
}
