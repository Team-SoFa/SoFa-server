package com.sw19.sofa.domain.member.repository;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.error.exception.BusinessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.sw19.sofa.domain.member.error.MemberErrorCode.NOT_FOUND_USER;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long memberId);

    default Member findByIdOrElseThrow(Long userId) {
        return findById(userId).orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
    }

    Optional<Member> findByEmail(String email);
}
