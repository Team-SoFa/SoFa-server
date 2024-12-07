package com.sw19.sofa.domain.ai.service;

import com.sw19.sofa.global.common.dto.FolderDto;
import com.sw19.sofa.global.common.dto.FolderWithTagListDto;
import com.sw19.sofa.global.common.dto.TagDto;
import com.sw19.sofa.global.common.dto.TitleAndSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiService {

    public List<String> createTagList(String url){
        List<String> testTagList = new ArrayList<>();
        testTagList.add("test1");
        testTagList.add("test2");
        testTagList.add("test3");

        return testTagList;
    }

    public FolderDto createFolder(String url, List<TagDto> tagList, List<FolderWithTagListDto> folderListWithTagList){
        String folderName = "test";
        return folderListWithTagList.stream()
                .filter(folderWithTagListDto -> folderWithTagListDto.FolderName().equals(folderName))
                .findFirst()
                .map(folderWithTagListDto -> new FolderDto(folderWithTagListDto.folderId(), folderWithTagListDto.FolderName()))
                .orElse(null);
    }

    public TitleAndSummaryDto createTitleAndStummaryDto(String url){
        return new TitleAndSummaryDto("testTitle", "testSummary");
    }
}
