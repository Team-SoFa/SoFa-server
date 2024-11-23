package com.sw19.sofa.domain.tag.repository;

import com.sw19.sofa.domain.tag.entity.Tag;
import com.sw19.sofa.domain.linkcard.enums.TagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByNameIn(List<String> nameList);
    List<Tag> findAllByIdIn(List<Long> id);
    Optional<Tag> findByName(String name);
    List<Tag> findAllByType(TagType type);
    boolean existsByName(String name);
    List<Tag> findAllByNameContainingIgnoreCase(String keyword);

    @Modifying
    @Query("DELETE FROM LinkCardTag lct WHERE lct.tagId = :tagId")
    void deleteAllLinkCardTagsByTagId(Long tagId);
}