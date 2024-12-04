package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.ai.service.AiService;
import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.article.service.ArticleService;
import com.sw19.sofa.domain.article.service.ArticleTagService;
import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.folder.service.FolderService;
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
import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.domain.tag.service.CustomTagService;
import com.sw19.sofa.domain.tag.service.TagService;
import com.sw19.sofa.global.common.constants.Constants;
import com.sw19.sofa.global.common.dto.*;
import com.sw19.sofa.domain.linkcard.dto.enums.LinkCardSortBy;
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
    private final LinkCardService linkCardService;
    private final FolderService folderService;
    private final ArticleService articleService;
    private final LinkCardTagService linkCardTagService;
    private final ArticleTagService articleTagService;

    @Transactional
    public CreateLinkCardBasicInfoRes createLinkCardBasicInfo(Member member, CreateLinkCardBasicInfoReq req) {
        ArticleDto articleDto = articleService.getArticleDtoByUrlOrElseNull(req.url());
        List<TagDto> tagDtoList;
        TitleAndSummaryDto titleAndSummaryDto;

        // 아티클 및 태그 생성
        if(articleDto != null){
            List<ArticleTagDto> articleTagDtoList = articleTagService.getArticleTagDtoListByArticleId(articleDto.id());
            titleAndSummaryDto = new TitleAndSummaryDto(articleDto.title(), articleDto.summary());

            List<Long> tagIdList = articleTagDtoList.stream().map(ArticleTagDto::tagId).toList();
            tagDtoList = tagService.getTagDtoListByIdList(tagIdList);
        }else{
            titleAndSummaryDto = aiService.createTitleAndStummaryDto(req.url());

            List<String> tagNameList = aiService.createTagList(req.url());
            List<Tag> tagList = tagService.getTagList(tagNameList);
            tagDtoList = tagList.stream().map(TagDto::new).toList();

            Article article = articleService.addArticle(titleAndSummaryDto.title(), titleAndSummaryDto.summary(), req.imageUrl());
            articleTagService.addArticleTagListByArticleAndTagListIn(article ,tagList);
        }

        // 폴더 생성
        String folderName = aiService.createFolder(req.url());
        Folder folder = folderService.getFolderByNameAndMemberOrNull(folderName, member);
        if(folder == null){
            folder = folderService.addFolder(member, folderName);
        }

        //Dto 생성
        List<LinkCardTagDto> linkCardTagDtoList = tagDtoList.stream().map(LinkCardTagDto::new).toList();
        LinkCardFolderDto linkCardFolderDto = new LinkCardFolderDto(folder);

        return new CreateLinkCardBasicInfoRes(titleAndSummaryDto.title(), titleAndSummaryDto.summary(), linkCardTagDtoList, linkCardFolderDto);
    }

    @Transactional(readOnly = true)
    public LinkCardRes getLinkCard(String encryptId){
        Long linkCardId = EncryptionUtil.decrypt(encryptId);
        LinkCardDto linkCard = linkCardService.getLinkCardDto(linkCardId);

        List<LinkCardTagSimpleDto> linkCardTagSimpleDtoList = linkCardTagService.getLinkCardTagSimpleDtoListByLinkCardId(linkCardId);

        List<LinkCardTagDto> linkCardTagDtoList = getLinkCardTagDtoList(linkCardTagSimpleDtoList);

        return new LinkCardRes(linkCard, linkCardTagDtoList);
    }

    @Transactional
    public void addLinkCard(LinkCardReq req) {
        Long folderId = EncryptionUtil.decrypt(req.folderId());

        Folder folder = folderService.getFolder(folderId);
        Article article = articleService.getArticleByUrl(req.url());

        LinkCard linkCard = linkCardService.addLinkCard(req, folder, article);

        linkCardTagService.addLinkCardTagList(linkCard, req.tagList());

    }

    @Transactional(readOnly = true)
    public ListRes<LinkCardSimpleRes> getLinkCardList(String encryptFolderId, LinkCardSortBy linkCardSortBy, SortOrder sortOrder, String encryptLastId, int limit) {
        Long folderId = EncryptionUtil.decrypt(encryptFolderId);
        Long lastId = EncryptionUtil.decrypt(encryptLastId);
        ListRes<LinkCard> linkCardListRes = linkCardService.getLinkCardSimpleResListByFolderIdAndSortCondition(folderId, linkCardSortBy, sortOrder, limit, lastId);
        List<LinkCardSimpleRes> linkCardSimpleResList = linkCardListRes.data().stream().map(LinkCardSimpleRes::new).toList();

        return new ListRes<>(
                linkCardSimpleResList,
                linkCardListRes.limit(),
                linkCardListRes.size(),
                linkCardListRes.hasNext()
        );
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

        List<LinkCardTagDto> linkCardTagDtoList = getLinkCardTagDtoList(linkCardTagSimpleDtoList);

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
        Long folderId = EncryptionUtil.decrypt(encryptFolderId);

        Folder folder = folderService.getFolder(folderId);
        linkCardService.editLinkCardFolder(linkCardId,folder);
        return new LinkCardFolderRes(folder);
    }

    @Transactional
    public void enterLinkCard(String encryptId) {
        Long linkCardId = EncryptionUtil.decrypt(encryptId);

        LinkCard linkCard = linkCardService.getLinkCard(linkCardId);
        linkCardService.enterLinkCard(linkCard);
        articleService.enterArticle(linkCard.getArticle());
    }

    @Transactional
    public void moveLinkCardToRecycleBin(Member member, String encryptId) {
        Long linkCardId = EncryptionUtil.decrypt(encryptId);

        LinkCard linkCard = linkCardService.getLinkCard(linkCardId);
        Folder recycleBinFolder = folderService.getFolderByNameAndMemberOrElseThrow(Constants.recycleBinName, member);
        linkCard.editFolder(recycleBinFolder);
    }


    private List<LinkCardTagDto> getLinkCardTagDtoList(List<LinkCardTagSimpleDto> linkCardTagSimpleDtoList) {
        List<Long> tagIdList = linkCardTagSimpleDtoList.stream().filter(linkCardTagInfoDto -> linkCardTagInfoDto.tagType().equals(TagType.AI)).map(LinkCardTagSimpleDto::id).toList();
        List<Long> customIdList = linkCardTagSimpleDtoList.stream().filter(linkCardTagInfoDto -> linkCardTagInfoDto.tagType().equals(TagType.CUSTOM)).map(LinkCardTagSimpleDto::id).toList();

        List<LinkCardTagDto> linkCardTagDtoList = new ArrayList<>();
        linkCardTagDtoList.addAll(tagService.getTagDtoListByIdList(tagIdList).stream().map(LinkCardTagDto::new).toList());
        linkCardTagDtoList.addAll(customTagService.getCustomTagDtoListByIdList(customIdList).stream().map(LinkCardTagDto::new).toList());
        return linkCardTagDtoList;
    }
}
