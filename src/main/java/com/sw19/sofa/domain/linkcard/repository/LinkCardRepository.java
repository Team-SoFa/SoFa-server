package com.sw19.sofa.domain.linkcard.repository;

import com.sw19.sofa.domain.linkcard.entity.LinkCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkCardRepository extends JpaRepository<LinkCard, Long>, LinkCardCustomRepository {
}
