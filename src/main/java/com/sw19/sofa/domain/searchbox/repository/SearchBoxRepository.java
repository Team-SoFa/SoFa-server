package com.sw19.sofa.domain.searchbox.repository;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchBoxRepository extends JpaRepository<LinkCard, Long> {
    @Query("""
        SELECT DISTINCT lc FROM LinkCard lc
        LEFT JOIN FETCH lc.article art
        WHERE 
        lc.id > :lastId
        AND (:folderId IS NULL OR lc.folder.id = :folderId)
        AND EXISTS (
            SELECT 1 FROM LinkCardTag lt 
            WHERE lt.linkCard = lc 
            AND (:tagIds IS NULL OR lt.tagId IN :tagIds)
        )
        AND (
            LOWER(art.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(art.url) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(art.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(lc.memo) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(lc.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        ORDER BY lc.id ASC
    """)
    List<LinkCard> search(
            @Param("keyword") String keyword,
            @Param("folderId") Long folderId,
            @Param("tagIds") List<Long> tagIds,
            @Param("lastId") Long lastId,
            @Param("limit") int limit
    );
}