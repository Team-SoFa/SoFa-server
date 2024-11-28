package com.sw19.sofa.domain.linkcard.repository;

import com.sw19.sofa.domain.folder.entity.Folder;
import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import com.sw19.sofa.domain.linkcard.error.LinkCardErrorCode;
import com.sw19.sofa.global.error.exception.BusinessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LinkCardRepository extends JpaRepository<LinkCard, Long>, LinkCardCustomRepository {
    default LinkCard findByIdOrElseThrowException(Long id) {
        return findById(id).orElseThrow(() -> new BusinessException(LinkCardErrorCode.NOT_FOUND_LINK_CARD));
    }
    List<LinkCard> findAllByFolder(Folder folder);

    @Query("SELECT lc FROM LinkCard lc WHERE lc.visitedAt < :thresholdDate")
    List<LinkCard> findUnusedLinkCardList(@Param("thresholdDate") LocalDateTime thresholdDate);
}
