package com.sw19.sofa.domain.recycleBin.controller;

import com.sw19.sofa.domain.recycleBin.api.RecycleBinApi;
import com.sw19.sofa.domain.recycleBin.service.RecycleBinManageService;
import com.sw19.sofa.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recycleBin")
public class RecycleBinController implements RecycleBinApi {
    private final RecycleBinManageService recycleBinManageService;

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> permanentlyDelete(@PathVariable String id) {
        recycleBinManageService.permanentlyDelete(id);
        return BaseResponse.ok("링크 카드 영구 삭제 완료");
    }
}
