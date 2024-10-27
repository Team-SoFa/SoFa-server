package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.ai.service.AiService;
import com.sw19.sofa.domain.folder.service.FolderTagService;
import com.sw19.sofa.domain.linkcard.dto.LinkCardFolderDto;
import com.sw19.sofa.domain.linkcard.dto.LinkCardTagDto;
import com.sw19.sofa.domain.linkcard.dto.request.CreateLinkCardBasicInfoReq;
import com.sw19.sofa.domain.linkcard.dto.response.CreateLinkCardBasicInfoRes;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.tag.service.TagService;
import com.sw19.sofa.global.common.dto.FolderDto;
import com.sw19.sofa.global.common.dto.FolderWithTagListDto;
import com.sw19.sofa.global.common.dto.TagDto;
import com.sw19.sofa.global.common.dto.TitleAndSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkCardMangeService {
    private final AiService aiService;
    private final TagService tagService;
    private final FolderTagService folderTagService;

    @Transactional(readOnly = true)
    public CreateLinkCardBasicInfoRes createLinkCardBasicInfo(Member member, CreateLinkCardBasicInfoReq req) {

        TitleAndSummaryDto titleAndStringDto = aiService.createTitleAndStringDto(req.url());

        List<String> tagList = aiService.createTagList(req.url());
        List<TagDto> tagDtoList = tagService.getTagDtoList(tagList);

        List<FolderWithTagListDto> folderWithTagList = folderTagService.getFolderListWithTagListByFolderIdList(member);
        FolderDto folderDto = aiService.createFolder(req.url(), tagDtoList, folderWithTagList);

        List<LinkCardTagDto> linkCardTagDtoList = tagDtoList.stream().map(LinkCardTagDto::new).toList();
        LinkCardFolderDto linkCardFolderDto = new LinkCardFolderDto(folderDto);

        return new CreateLinkCardBasicInfoRes(titleAndStringDto.title(), titleAndStringDto.summary(), linkCardTagDtoList, linkCardFolderDto);
    }
}
