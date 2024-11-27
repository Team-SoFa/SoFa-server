package com.sw19.sofa.domain.tag.repository;

import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.domain.linkcard.enums.TagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByNameIn(List<String> nameList);
    List<Tag> findAllByIdIn(List<Long> ids);
    Optional<Tag> findByName(String name);
    List<Tag> findAllByType(TagType type);
    boolean existsByName(String name);

    @Query("SELECT t FROM Tag t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Tag> findAllByNameContainingIgnoreCase(@Param("keyword") String keyword);

    @Query("SELECT t FROM Tag t " +
            "WHERE t.id IN (SELECT DISTINCT lt.tagId FROM LinkCardTag lt) " +
            "AND LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Tag> searchTagsWithLinks(@Param("keyword") String keyword);

    @Query("SELECT CASE WHEN COUNT(lct) > 0 THEN true ELSE false END FROM LinkCardTag lct WHERE lct.tagId = :tagId")
    boolean existsByIdAndLinkCardTagsIsNotEmpty(@Param("tagId") Long tagId);

    @Query("SELECT t FROM Tag t " +
            "LEFT JOIN LinkCardTag lct ON t.id = lct.tagId " +
            "GROUP BY t.id " +
            "ORDER BY COUNT(lct.id) DESC " +
            "LIMIT 10")
    List<Tag> findMostUsedTags();

    @Modifying
    @Query("DELETE FROM LinkCardTag lct WHERE lct.tagId = :tagId")
    void deleteAllLinkCardTagsByTagId(@Param("tagId") Long tagId);

    @Query("SELECT DISTINCT t FROM Tag t " +
            "LEFT JOIN LinkCardTag lct ON t.id = lct.tagId " +
            "WHERE lct.linkCard.id IN :linkCardIds")
    Set<Tag> findTagsByLinkCardIds(@Param("linkCardIds") List<Long> linkCardIds);


}