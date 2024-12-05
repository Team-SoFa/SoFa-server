package com.sw19.sofa.domain.setting.repository;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.setting.entity.Setting;
import com.sw19.sofa.global.error.exception.BusinessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sw19.sofa.domain.setting.error.SettingErrorCode.SETTING_NOT_FOUND;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByMember(Member member);
    default Setting findByMemberOrThrow(Member member) {
        return findByMember(member).orElseThrow(() -> new BusinessException(SETTING_NOT_FOUND));
    }
    boolean existsByMember(Member member);
}