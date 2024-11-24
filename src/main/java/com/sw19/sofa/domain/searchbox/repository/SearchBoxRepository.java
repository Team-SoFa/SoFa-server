package com.sw19.sofa.domain.searchbox.repository;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchBoxRepository extends JpaRepository<LinkCard, Long>, SearchBoxCustomRepository  {
    @Query(value = """
        SELECT DISTINCT lc FROM LinkCard lc
        LEFT JOIN FETCH lc.article art
        WHERE lc.id > :lastId
        AND lc.folder.id = :folderId
        ORDER BY lc.id ASC
        """, nativeQuery = false)
    List<LinkCard> searchByFolder(
            @Param("folderId") Long folderId,
            @Param("lastId") Long lastId);

    @Query(value = """
        SELECT DISTINCT lc FROM LinkCard lc
        LEFT JOIN FETCH lc.article art
        WHERE lc.id > :lastId
        AND EXISTS (
            SELECT 1 FROM LinkCardTag lt 
            WHERE lt.linkCard = lc 
            AND lt.tagId IN :tagIds
        )
        ORDER BY lc.id ASC
        """, nativeQuery = false)
    List<LinkCard> searchByTags(
            @Param("tagIds") List<Long> tagIds,
            @Param("lastId") Long lastId);

    @Query(value = """
        SELECT DISTINCT lc FROM LinkCard lc
        LEFT JOIN FETCH lc.article art
        WHERE lc.id > :lastId
        ORDER BY lc.id ASC
        """, nativeQuery = false)
    List<LinkCard> searchAll(
            @Param("lastId") Long lastId);
}