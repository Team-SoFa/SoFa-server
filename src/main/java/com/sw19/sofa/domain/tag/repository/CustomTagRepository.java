package com.sw19.sofa.domain.tag.repository;

import com.sw19.sofa.domain.tag.entity.CustomTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomTagRepository extends JpaRepository<CustomTag, Long> {
    List<CustomTag> findAllByIdIn(List<Long> id);
}
