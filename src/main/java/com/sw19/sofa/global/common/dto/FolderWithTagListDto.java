package com.sw19.sofa.global.common.dto;

import java.util.List;

public record FolderWithTagListDto(Long folderId, String FolderName, List<TagDto> TagList) {
}
