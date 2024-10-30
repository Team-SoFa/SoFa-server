package com.sw19.sofa.domain.memberTag.repository;

import com.sw19.sofa.domain.member.entity.MemberTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTagRepository extends JpaRepository<MemberTag, Long>, MemberTagCustomRepository {

}
