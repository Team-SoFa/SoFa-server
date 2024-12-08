package com.sw19.sofa.domain.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

import static com.sw19.sofa.global.common.constants.Constants.DEFAULT_FOLDER_CATEGORIES;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiFolderService {
    private final OpenAIService openAIService;

    public String recommendFolderName(String content, List<String> userFolders) {
        List<String> folderOptions = userFolders.isEmpty() ?
                DEFAULT_FOLDER_CATEGORIES :
                Stream.concat(userFolders.stream(), DEFAULT_FOLDER_CATEGORIES.stream())
                        .distinct()
                        .toList();

        String folderNames = String.join(", ", folderOptions);

        String prompt = String.format("""
                다음 내용을 분석하여 가장 적합한 폴더 하나만 선택해주세요.
                주어진 폴더 목록에서만 선택해야 하며, 폴더 이름만 정확히 답변해주세요.
                
                내용: %s
                
                폴더 이름: %s
                """, content, folderNames);

        return openAIService.sendPrompt(prompt, 50)
                .trim()
                .replace(" 폴더", "");
    }
}