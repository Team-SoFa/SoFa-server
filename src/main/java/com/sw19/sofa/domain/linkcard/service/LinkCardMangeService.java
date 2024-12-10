package com.sw19.sofa.domain.linkcard.service;

import com.sw19.sofa.domain.ai.service.AiManageService;
import com.sw19.sofa.domain.article.entity.Article;
import com.sw19.sofa.domain.article.service.ArticleService;
import com.sw19.sofa.domain.article.service.ArticleTagService;
import com.sw19.sofa.domain.folder.dto.response.FolderRes;
import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.folder.service.FolderService;
import com.sw19.sofa.domain.linkcard.dto.LinkCardFolderDto;
import com.sw19.sofa.domain.linkcard.dto.LinkCardTagDto;
import com.sw19.sofa.domain.linkcard.dto.LinkCardTagSimpleDto;
import com.sw19.sofa.domain.linkcard.dto.enums.LinkCardSortBy;
import com.sw19.sofa.domain.linkcard.dto.request.CreateLinkCardBasicInfoReq;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardInfoEditReq;
import com.sw19.sofa.domain.linkcard.dto.request.LinkCardReq;
import com.sw19.sofa.domain.linkcard.dto.response.*;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import com.sw19.sofa.domain.linkcard.enums.TagType;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.remind.service.RemindService;
import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.domain.tag.service.CustomTagService;
import com.sw19.sofa.domain.tag.service.TagService;
import com.sw19.sofa.global.common.constants.Constants;
import com.sw19.sofa.global.common.dto.*;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import com.sw19.sofa.global.error.code.CommonErrorCode;
import com.sw19.sofa.global.error.exception.BusinessException;
import com.sw19.sofa.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LinkCardMangeService {
    private final TagService tagService;
    private final CustomTagService customTagService;
    private final LinkCardService linkCardService;
    private final FolderService folderService;
    private final ArticleService articleService;
    private final LinkCardTagService linkCardTagService;
    private final ArticleTagService articleTagService;
    private final AiManageService aiManageService;
    private final RemindService remindService;

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY = 1000;


    @Transactional
    public CreateLinkCardBasicInfoRes createLinkCardBasicInfo(Member member, CreateLinkCardBasicInfoReq req) {
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                return tryCreateLinkCardBasicInfo(member, req);
            } catch (DataIntegrityViolationException | DataException e) {
                attempts++;
                if (attempts == MAX_RETRIES) {
                    log.error("Failed to create LinkCard after {} attempts", MAX_RETRIES, e);
                    throw e;
                }
                log.warn("Attempt {} failed, retrying after delay...", attempts);
                try {
                    Thread.sleep(RETRY_DELAY);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new BusinessException(CommonErrorCode.INTERNAL_SERVER_ERROR);
                }
            }
        }
        throw new BusinessException(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    private CreateLinkCardBasicInfoRes tryCreateLinkCardBasicInfo(Member member, CreateLinkCardBasicInfoReq req) {
        ArticleDto articleDto = articleService.getArticleDtoByUrlOrElseNull(req.url());
        List<TagDto> tagDtoList;
        TitleAndSummaryDto titleAndSummaryDto;

        if(articleDto != null) {
            List<ArticleTagDto> articleTagDtoList = articleTagService.getArticleTagDtoListByArticleId(articleDto.id());
            titleAndSummaryDto = new TitleAndSummaryDto(articleDto.title(), articleDto.summary());

            List<Long> tagIdList = articleTagDtoList.stream().map(ArticleTagDto::tagId).toList();
            tagDtoList = tagService.getTagDtoListByIdList(tagIdList);
        } else {
            titleAndSummaryDto = aiManageService.createTitleAndSummary(req.url());
            List<String> tagNameList = aiManageService.createTagList(req.url());
            List<Tag> tagList = tagService.createAiTags(tagNameList);
            tagDtoList = tagList.stream().map(TagDto::new).toList();

            Article article = articleService.addArticle(
                    req.url(),
                    titleAndSummaryDto.title(),
                    titleAndSummaryDto.summary()
            );
            articleTagService.addArticleTagListByArticleAndTagListIn(article, tagList);

            articleDto = new ArticleDto(article);
        }
        List<FolderRes> userFolders = folderService.getFolderList(member).folderList().stream()
                .filter(folder -> !folder.name().equals(Constants.recycleBinName))
                .toList();

        Folder selectedFolder = null;

        if (!userFolders.isEmpty()) {
            String recommendedFolderName = aiManageService.recommendFolder(
                    titleAndSummaryDto.summary(),
                    userFolders.stream().map(FolderRes::name).toList()
            );
            selectedFolder = folderService.getFolderByNameAndMemberOrNull(recommendedFolderName, member);
        }

        if (selectedFolder == null) {
            String defaultFolderName = aiManageService.recommendFolder(
                    titleAndSummaryDto.summary(),
                    Constants.DEFAULT_FOLDER_CATEGORIES
            );
            selectedFolder = folderService.getFolderByNameAndMemberOrNull(defaultFolderName, member);

            if (selectedFolder == null) {
                selectedFolder = folderService.addFolder(member, defaultFolderName);
                log.info("Created default category folder: {} for user: {}", defaultFolderName, member.getId());
            }
        }

        List<LinkCardTagDto> linkCardTagDtoList = tagDtoList.stream().map(LinkCardTagDto::new).toList();
        LinkCardFolderDto linkCardFolderDto = new LinkCardFolderDto(selectedFolder);

        return new CreateLinkCardBasicInfoRes(
                titleAndSummaryDto.title(),
                titleAndSummaryDto.summary(),
                linkCardTagDtoList,
                linkCardFolderDto,
                articleDto.imageUrl()
        );
    }

    @Transactional
    public LinkCardRes getLinkCard(String encryptId, Member member){
        Long linkCardId = EncryptionUtil.decrypt(encryptId);

        LinkCard linkCard = linkCardService.getLinkCardDto(linkCardId);
        remindService.removeFromRemind(linkCard, member);

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
        List<LinkCardTagSimpleDto> linkCardTagSimpleDtoList = req.tagList().stream().map(LinkCardTagSimpleDto::new).toList();
        linkCardTagService.addLinkCardTagList(linkCard, linkCardTagSimpleDtoList );

    }

    public ListRes<LinkCardSimpleRes> getLinkCardList(Member member, LinkCardSortBy linkCardSortBy, SortOrder sortOrder, String encryptLastId, int limit) {
        Long lastId = encryptLastId.equals("0") ? 0 : EncryptionUtil.decrypt(encryptLastId);
        List<Long> folderIdList = folderService.getFolderList(member).folderList().stream()
                .filter(folder -> !"휴지통".equals(folder.name()))
                .map(folderRes -> EncryptionUtil.decrypt(folderRes.id()))
                .toList();
        return linkCardListInfiniteScroll(folderIdList, linkCardSortBy, sortOrder, limit, lastId);
    }

    public MostTagLinkCardListRes getMostTagLinkCardList(Member member, LinkCardSortBy linkCardSortBy, SortOrder sortOrder, String encryptLastId, int limit) {
        Long lsatId = EncryptionUtil.decrypt(encryptLastId);

        LinkCardTag linkCardTag = linkCardTagService.getMostTagIdByMember(member);
        Long tagId = linkCardTag.getTagId();

        LinkCardTagDto tagDto;
        if(linkCardTag.getTagType().equals(TagType.AI)){
            tagDto = new LinkCardTagDto(tagService.getTagDto(tagId));
        }else{
            tagDto = new LinkCardTagDto(customTagService.getCustomTagDto(tagId));
        }

        ListRes<LinkCard> linkCardListRes = linkCardService.getLinkCardSimpleResListByLinkCardTagAndSortCondition(linkCardTag, linkCardSortBy, sortOrder, limit, lsatId);
        List<LinkCardSimpleRes> data = linkCardListRes.data().stream().map(LinkCardSimpleRes::new).toList();

        return new MostTagLinkCardListRes(tagDto, data, limit, linkCardListRes.size(), linkCardListRes.hasNext());
    }

    public ListRes<LinkCardSimpleRes> getLinkCardListByFolder(String encryptFolderId, LinkCardSortBy linkCardSortBy, SortOrder sortOrder, String encryptLastId, int limit) {
        Long folderId = EncryptionUtil.decrypt(encryptFolderId);
        Long lastId = encryptLastId.equals("0") ? 0 : EncryptionUtil.decrypt(encryptLastId);

        return linkCardListInfiniteScroll(List.of(folderId), linkCardSortBy, sortOrder, limit, lastId);
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
    public void enterLinkCard(String encryptId, Member member) {
        Long linkCardId = EncryptionUtil.decrypt(encryptId);
        LinkCard linkCard = linkCardService.getLinkCard(linkCardId);
        linkCardService.enterLinkCard(linkCard);
        remindService.removeFromRemind(linkCard, member);
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


    private ListRes<LinkCardSimpleRes> linkCardListInfiniteScroll(List<Long> folderIdList, LinkCardSortBy linkCardSortBy, SortOrder sortOrder, int limit, Long lastId) {
        ListRes<LinkCard> linkCardListRes = linkCardService.getLinkCardSimpleResListByFolderIdAndSortCondition(folderIdList, linkCardSortBy, sortOrder, limit, lastId);
        List<LinkCardSimpleRes> linkCardSimpleResList = linkCardListRes.data().stream().map(LinkCardSimpleRes::new).toList();

        return new ListRes<>(
                linkCardSimpleResList,
                linkCardListRes.limit(),
                linkCardListRes.size(),
                linkCardListRes.hasNext()
        );
    }
}
