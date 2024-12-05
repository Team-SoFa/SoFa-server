package com.sw19.sofa.domain.tag.repository;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.tag.entity.CustomTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomTagRepository extends JpaRepository<CustomTag, Long> {
    List<CustomTag> findAllByIdIn(List<Long> id);
    List<CustomTag> findByMember(Member member);
    //List<CustomTag> findAllByNameIn(List<String> names);
    List<CustomTag> findAllByNameContainingIgnoreCase(String keyword);
    boolean existsByMemberAndName(Member member, String name);

    @Query("SELECT DISTINCT ct FROM CustomTag ct " +
            "JOIN LinkCardTag lct ON ct.id = lct.tagId " +
            "WHERE lct.tagType = com.sw19.sofa.domain.linkcard.enums.TagType.CUSTOM " +
            "AND LOWER(ct.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<CustomTag> findCustomTagsInLinkCards(@Param("keyword") String keyword);
}
