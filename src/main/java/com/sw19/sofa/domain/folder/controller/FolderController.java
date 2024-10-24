package com.sw19.sofa.domain.folder.controller;

import com.sw19.sofa.domain.folder.api.FolderApi;
import com.sw19.sofa.domain.folder.dto.response.FolderListRes;
import com.sw19.sofa.domain.folder.service.FolderService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.BaseResponse;
import com.sw19.sofa.security.jwt.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folder")
public class FolderController implements FolderApi {

    private final FolderService folderService;

    @Override
    @GetMapping
    public ResponseEntity<FolderListRes> getFolderList(@AuthMember Member member) {
        FolderListRes res = folderService.getFolderList(member);
        return BaseResponse.ok(res);
    }
}
