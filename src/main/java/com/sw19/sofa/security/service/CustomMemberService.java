package com.sw19.sofa.security.service;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.member.repository.MemberRepository;
import com.sw19.sofa.global.util.EncryptionUtil;
import com.sw19.sofa.security.entity.CustomMemberDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CustomMemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String encryptedUserId) throws UsernameNotFoundException {
        Member member = memberRepository.findByIdOrElseThrow(EncryptionUtil.decrypt(encryptedUserId));

        return new CustomMemberDetail(member, member.getAuthority());

    }
}
