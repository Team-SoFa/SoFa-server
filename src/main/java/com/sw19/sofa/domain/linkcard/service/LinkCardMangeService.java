package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.ai.service.AiService;
import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.article.service.ArticleService;
import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.folder.service.FolderService;
import com.sw19.sofa.domain.folder.service.FolderTagService;
import com.sw19.sofa.domain.linkcard.dto.LinkCardDto;
import com.sw19.sofa.domain.linkcard.dto.LinkCardFolderDto;
import com.sw19.sofa.domain.linkcard.dto.LinkCardTagSimpleDto;
import com.sw19.sofa.domain.linkcard.dto.LinkCardTagDto;
import com.sw19.sofa.domain.linkcard.dto.request.CreateLinkCardBasicInfoReq;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardInfoEditReq;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardReq;
import com.sw19.sofa.domain.linkcard.dto.response.*;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.tag.service.CustomTagService;
import com.sw19.sofa.domain.tag.service.TagService;
import com.sw19.sofa.global.common.dto.*;
import com.sw19.sofa.global.common.dto.enums.SortBy;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkCardMangeService {
    private final AiService aiService;
    private final TagService tagService;
    private final CustomTagService customTagService;
    private final FolderTagService folderTagService;
    private final LinkCardService linkCardService;
    private final FolderService folderService;
    private final ArticleService articleService;
    private final LinkCardTagService linkCardTagService;

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
        LinkCardDto linkCard = linkCardService.getLinkCardDto(linkCardId);

        List<LinkCardTagSimpleDto> linkCardTagSimpleDtoList = linkCardTagService.getLinkCardTagSimpleDtoListByLinkCardId(linkCardId);

        List<Long> tagIdList = linkCardTagSimpleDtoList.stream().filter(linkCardTagInfoDto -> linkCardTagInfoDto.tagType().equals(TagType.AI)).map(LinkCardTagSimpleDto::id).toList();
        List<Long> customIdList = linkCardTagSimpleDtoList.stream().filter(linkCardTagInfoDto -> linkCardTagInfoDto.tagType().equals(TagType.CUSTOM)).map(LinkCardTagSimpleDto::id).toList();

        List<LinkCardTagDto> linkCardTagDtoList = new ArrayList<>();
        linkCardTagDtoList.addAll(tagService.getTagDtoListByIdList(tagIdList).stream().map(LinkCardTagDto::new).toList());
        linkCardTagDtoList.addAll(customTagService.getCustomTagDtoListByIdList(customIdList).stream().map(LinkCardTagDto::new).toList());

        return new LinkCardRes(linkCard, linkCardTagDtoList);
    }

    @Transactional
    public void addLinkCard(LinkCardReq req) {
        Folder folder = folderService.findFolder(req.folderId());
        Article article = articleService.getArticleByUrl(req.url());

        LinkCard linkCard = linkCardService.addLinkCard(req, folder, article);

        linkCardTagService.addLinkCardTagList(linkCard, req.tagList());

    }

    @Transactional(readOnly = true)
    public ListRes<LinkCardSimpleRes> getLinkCardList(String encryptFolderId, SortBy sortBy, SortOrder sortOrder, String encryptLastId, int limit) {
        Long folderId = EncryptionUtil.decrypt(encryptFolderId);
        Long lastId = EncryptionUtil.decrypt(encryptLastId);

        return linkCardService.getLinkCardSimpleResListByFolderIdAndSortCondition(folderId, sortBy, sortOrder, limit, lastId);
    }

    @Transactional
    public LinkCardInfoRes editLinkCardInfo(String encryptId, LinkCardInfoEditReq req) {
        Long linkCardId = EncryptionUtil.decrypt(encryptId);

        return linkCardService.editLinkCardInfo(linkCardId, req.title(),req.memo(),req.summary());
    }

    @Transactional
    public LinkCardTagListRes addLinkCardTag(String encryptId, List<LinkCardTagSimpleDto> tagList) {
        Long linkCardId = EncryptionUtil.decrypt(encryptId);
        LinkCard linkCard = linkCardService.getLinkCard(linkCardId);
        List<LinkCardTagSimpleDto> linkCardTagSimpleDtoList = linkCardTagService.addLinkCardTagList(linkCard, tagList).stream().map(LinkCardTagSimpleDto::new).toList();

        List<Long> tagIdList = linkCardTagSimpleDtoList.stream().filter(linkCardTagInfoDto -> linkCardTagInfoDto.tagType().equals(TagType.AI)).map(LinkCardTagSimpleDto::id).toList();
        List<Long> customIdList = linkCardTagSimpleDtoList.stream().filter(linkCardTagInfoDto -> linkCardTagInfoDto.tagType().equals(TagType.CUSTOM)).map(LinkCardTagSimpleDto::id).toList();

        List<LinkCardTagDto> linkCardTagDtoList = new ArrayList<>();
        linkCardTagDtoList.addAll(tagService.getTagDtoListByIdList(tagIdList).stream().map(LinkCardTagDto::new).toList());
        linkCardTagDtoList.addAll(customTagService.getCustomTagDtoListByIdList(customIdList).stream().map(LinkCardTagDto::new).toList());

        return new LinkCardTagListRes(linkCardTagDtoList);
    }

    @Transactional
    public void deleteLinkCardTag(String encryptLinkCardId, String encryptTagId, TagType tagType) {
        Long linkCardId = EncryptionUtil.decrypt(encryptLinkCardId);
        Long tagId = EncryptionUtil.decrypt(encryptTagId);

        linkCardTagService.deleteLinkCardTag(linkCardId, tagId, tagType);
    }

    @Transactional
    public LinkCardFolderRes editLinkCardFolder(String encryptLinkCardId, String encryptFolderId) {
        Long linkCardId = EncryptionUtil.decrypt(encryptLinkCardId);

        Folder folder = folderService.findFolder(encryptFolderId);
        linkCardService.editLinkCardFolder(linkCardId,folder);
        return new LinkCardFolderRes(folder);
    }

    public void enterLinkCard(String encryptId) {
        Long linkCardId = EncryptionUtil.decrypt(encryptId);

        linkCardService.enterLinkCard(linkCardId);

    }
}
