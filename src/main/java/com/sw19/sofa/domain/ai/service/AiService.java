package com.sw19.sofa.domain.ai.service;

import com.sw19.sofa.global.common.dto.TitleAndSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiService {

    // TODO : ai 코드 추가하기
    public List<String> createTagList(String url){
        List<String> testTagList = new ArrayList<>();
        testTagList.add("test1");
        testTagList.add("test2");
        testTagList.add("test3");

        return testTagList;
    }

    public String createFolder(String url){
        String folderName = "test";
        return folderName;
    }

    public TitleAndSummaryDto createTitleAndStummaryDto(String url){
        return new TitleAndSummaryDto("testTitle", "testSummary");
    }
}
