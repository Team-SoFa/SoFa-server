package com.sw19.sofa.domain.searchbox.dto.request;

import java.util.List;

public record SearchBoxReq(
        String keyword,           // 검색어
        String encryptedFolderId, // 선택된 폴더 ID (optional)
        List<String> encryptedTagIds  // 선택된 태그 ID 리스트 (optional)
) {}