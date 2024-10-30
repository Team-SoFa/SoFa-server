package com.sw19.sofa.domain.linkcard.repository;

import com.sw19.sofa.domain.linkcard.entity.LinkCardTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkCardTagRepository extends JpaRepository<LinkCardTag, Long> {
}
