package com.sw19.sofa.domain.linkcard.repository;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import com.sw19.sofa.domain.linkcard.enums.TagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkCardTagRepository extends JpaRepository<LinkCardTag, Long>, LinkCardTagCustomRepository {
    List<LinkCardTag> findAllByLinkCardId(Long linkCardId);
    void deleteByLinkCard_IdAndTagIdAndTagType(Long linkCardId, Long tagId, TagType tagType);
    void deleteAllByLinkCard(LinkCard linkCard);
}
