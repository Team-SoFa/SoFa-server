package com.sw19.sofa.domain.tag.repository;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.tag.entity.CustomTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomTagRepository extends JpaRepository<CustomTag, Long> {
    List<CustomTag> findAllByIdIn(List<Long> id);
    List<CustomTag> findByMember(Member member);
    List<CustomTag> findAllByNameIn(List<String> names);
    List<CustomTag> findAllByNameContainingIgnoreCase(String keyword);
}