package com.sw19.sofa.domain.setting.repository;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.setting.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByMember(Member member);
    boolean existsByMember(Member member);
}