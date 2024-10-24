package com.sw19.sofa.domain.folder.controller;

import com.sw19.sofa.domain.folder.api.FolderApi;
import com.sw19.sofa.domain.folder.dto.request.FolderReq;
import com.sw19.sofa.domain.folder.dto.response.FolderListRes;
import com.sw19.sofa.domain.folder.dto.response.FolderRes;
import com.sw19.sofa.domain.folder.service.FolderService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.dto.BaseResponse;
import com.sw19.sofa.security.jwt.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Override
    @PostMapping
    public ResponseEntity<FolderListRes> addFolder(@AuthMember Member member, @RequestBody FolderReq req) {
        FolderListRes res = folderService.addFolder(member, req);
        return BaseResponse.ok(res);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delFolder(@PathVariable("id") String id) {
        folderService.delFolder(id);
        return BaseResponse.ok("폴더 삭제 완료");
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<FolderRes> editFolder(@PathVariable("id") String id, @RequestBody FolderReq req) {
        FolderRes res = folderService.editFolder(id, req);
        return BaseResponse.ok(res);
    }
}
