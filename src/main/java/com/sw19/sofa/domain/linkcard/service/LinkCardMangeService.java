package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.ai.service.AiService;
import com.sw19.sofa.domain.folder.service.FolderTagService;
import com.sw19.sofa.domain.linkcard.dto.LinkCardDto;
import com.sw19.sofa.domain.linkcard.dto.LinkCardFolderDto;
import com.sw19.sofa.domain.linkcard.dto.LinkCardTagDto;
import com.sw19.sofa.domain.linkcard.dto.request.CreateLinkCardBasicInfoReq;
import com.sw19.sofa.domain.linkcard.dto.response.CreateLinkCardBasicInfoRes;
import com.sw19.sofa.domain.linkcard.dto.response.LinkCardRes;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.member.service.MemberTagService;
import com.sw19.sofa.domain.tag.service.TagService;
import com.sw19.sofa.global.common.dto.*;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkCardMangeService {
    private final AiService aiService;
    private final TagService tagService;
    private final FolderTagService folderTagService;
    private final LinkCardService linkCardService;
    private final MemberTagService memberTagService;

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

    @Transactional(readOnly = true)
    public LinkCardRes getLinkCard(String encryptId){
        Long linkCardId = EncryptionUtil.decrypt(encryptId);
        LinkCardDto linkCard = linkCardService.getLinkCard(linkCardId);

        List<LinkCardTagDto> linkCardTagDtoList = new ArrayList<>();

        Long articleId = EncryptionUtil.decrypt(linkCard.article().id());
        List<TagDto> tagDtoList = tagService.getTagDtoListByArticleId(articleId);
        linkCardTagDtoList.addAll(tagDtoList.stream().map(LinkCardTagDto::new).toList());

        List<MemberTagDto> memberTagDtoList = memberTagService.getMemberTagDtoListByLinkCardId(linkCardId);
        linkCardTagDtoList.addAll(memberTagDtoList.stream().map(LinkCardTagDto::new).toList());

        return new LinkCardRes(linkCard, linkCardTagDtoList);
    }
}
