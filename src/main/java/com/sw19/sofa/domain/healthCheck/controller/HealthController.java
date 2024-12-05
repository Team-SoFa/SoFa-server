package com.sw19.sofa.domain.healthCheck.controller;

import com.sw19.sofa.domain.healthCheck.api.HealthCheckApi;
import com.sw19.sofa.global.common.dto.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/health-check")
public class HealthController implements HealthCheckApi {

    @Override
    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return BaseResponse.ok("Health Check Ok!");
    }
}
