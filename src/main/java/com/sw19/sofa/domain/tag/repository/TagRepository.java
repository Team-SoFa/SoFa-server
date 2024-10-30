package com.sw19.sofa.domain.tag.repository;

import com.sw19.sofa.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, TagCustomRepository {
    List<Tag> findAllByNameIn(List<String> nameList);
}
