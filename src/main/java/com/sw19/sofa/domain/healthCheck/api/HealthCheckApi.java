package com.sw19.sofa.domain.healthCheck.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "✅ Health Check")
public interface HealthCheckApi {
    @Operation(summary = "Health Check", description = "Health Check 용 API")
    @ApiResponse(responseCode = "200", description = "Google 로그인 URL 반환 성공")
    ResponseEntity<String> healthCheck();
}
